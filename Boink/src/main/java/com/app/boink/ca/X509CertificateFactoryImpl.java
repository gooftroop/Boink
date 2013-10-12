package com.app.boink.ca;

import com.app.boink.exception.XCISException;
import com.eaio.uuid.UUID;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PasswordFinder;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

@Singleton
public class X509CertificateFactoryImpl implements X509CertificateFactory, PasswordFinder {

    private static final String
            PROPS_HOURS_BEFORE = "hours.before",
            PROPS_HOURS_AFTER = "hours.after",
            PROPS_CHECK = "check",
            SIGNATURE_ALGORITHM = "SHA1withRSA",
            CA_CERT_PATH = "ca.cert.path",
            CA_KEY_PATH = "ca.key.path";

    private final Properties props;
    private final SshPublicKeyFactory sshPublicKeyFactory;
    private final String hostname;
    private final boolean checkCert;
    final int hoursBefore;
    final int hoursAfter;
    private java.security.cert.X509Certificate caCert;
    private PrivateKey caPrivateKey;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Inject
    X509CertificateFactoryImpl(SshPublicKeyFactory sshPublicKeyFactory) throws XCISException {

        this.props = new Properties();

        this.hoursBefore = Integer.parseInt(props.getProperty(PROPS_HOURS_BEFORE));
        this.hoursAfter = Integer.parseInt(props.getProperty(PROPS_HOURS_AFTER));
        this.checkCert = Boolean.parseBoolean(props.getProperty(PROPS_CHECK));

        this.sshPublicKeyFactory = sshPublicKeyFactory;

        try {
            this.hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new XCISException();
        }

        try {
            this.caPrivateKey = readCAPrivateKey();
        } catch (IOException e) {
            throw new XCISException();
        }

        try {
            this.caCert = readCACert();
        } catch (IOException e) {
            throw new XCISException();
        }
    }

    private PrivateKey readCAPrivateKey() throws IOException {

        final File caPrivateKeyFile = new File(props.getProperty(CA_KEY_PATH));

        FileReader caPrivateKeyFileReader = null;
        PEMReader caPrivateKeyReader = null;

        try {
            caPrivateKeyFileReader = new FileReader(caPrivateKeyFile);
            caPrivateKeyReader = new PEMReader(caPrivateKeyFileReader, this);
            final KeyPair keypair = (KeyPair) caPrivateKeyReader.readObject();
            return keypair.getPrivate();
        } finally {
            if (caPrivateKeyReader != null)
                caPrivateKeyReader.close();
            if (caPrivateKeyFileReader != null)
                caPrivateKeyFileReader.close();
        }
    }

    private java.security.cert.X509Certificate readCACert() throws IOException {

        final File caCertFile = new File(props.getProperty(CA_CERT_PATH));
        FileReader caCertFileReader = null;
        PEMReader caCertReader = null;

        try {
            caCertFileReader = new FileReader(caCertFile);
            caCertReader = new PEMReader(caCertFileReader, this);
            return (java.security.cert.X509Certificate) caCertReader.readObject();
        } finally {
            if (caCertReader != null)
                caCertReader.close();
            if (caCertFileReader != null)
                caCertFileReader.close();
        }
    }

    @NotNull
    @Override
    public X509Certificate get(HashMap<String, String> infos) throws XCISException {


        final UUID uuid = new UUID();
        final X509V3CertificateGenerator generator = new X509V3CertificateGenerator();
        final SshPublicKey sshKey;

        try {
            sshKey = sshPublicKeyFactory.get(infos.get("uid"));
        } catch (SshPublicKey.SshPublicKeyLoadingException e) {
            throw new XCISException();
        } catch (ConfigProperties.ConfigLoadingException e) {
            throw new XCISException();
        }

        final Calendar calendar = Calendar.getInstance();

        final Vector<DERObjectIdentifier> attrsVector = new Vector<DERObjectIdentifier>();
        final Hashtable<DERObjectIdentifier, String> attrsHash = new Hashtable<DERObjectIdentifier, String>();

        attrsHash.put(X509Principal.CN, infos.get("cn"));
        attrsVector.add(X509Principal.CN);

        attrsHash.put(X509Principal.UID, infos.get("uid"));
        attrsVector.add(X509Principal.UID);

        attrsHash.put(X509Principal.EmailAddress, infos.get("mail"));
        attrsVector.add(X509Principal.EmailAddress);

        attrsHash.put(X509Principal.OU, infos.get("groups"));
        attrsVector.add(X509Principal.OU);

        attrsHash.put(X509Principal.SN, infos.get("deviceid"));
        attrsVector.add(X509Principal.SN);

        generator.setSubjectDN(new X509Principal(attrsVector, attrsHash));

        calendar.add(Calendar.HOUR, -hoursBefore);
        generator.setNotBefore(calendar.getTime());

        calendar.add(Calendar.HOUR, hoursBefore + hoursAfter);
        generator.setNotAfter(calendar.getTime());

        // Reuse the UUID time as a SN
        generator.setSerialNumber(BigInteger.valueOf(uuid.getTime()).abs());

        try {
            generator.addExtension(X509Extensions.AuthorityKeyIdentifier, false, new AuthorityKeyIdentifierStructure(caCert));
        } catch (CertificateParsingException e) {
            throw new XCISException();
        }

        try {
            generator.addExtension(X509Extensions.SubjectKeyIdentifier, false, new SubjectKeyIdentifierStructure(sshKey.getKey()));
        } catch (CertificateParsingException e) {
            throw new XCISException();
        }

        String hostnameAndUUIDBuilder = hostname + ':' + uuid.toString();
        generator.addExtension(X509Extensions.IssuingDistributionPoint, false, hostnameAndUUIDBuilder.toString().getBytes());

        // Not a CA
        generator.addExtension(X509Extensions.BasicConstraints, true, new BasicConstraints(false));

        generator.setIssuerDN(caCert.getSubjectX500Principal());
        generator.setPublicKey(sshKey.getKey());
        generator.setSignatureAlgorithm(SIGNATURE_ALGORITHM);

        final java.security.cert.X509Certificate cert;
        try {
            cert = generator.generate(caPrivateKey, BouncyCastleProvider.PROVIDER_NAME);
        }  catch (NoSuchProviderException e) {
            throw new XCISException();
        } catch (CertificateEncodingException e) {
            throw new XCISException();
        } catch (NoSuchAlgorithmException e) {
            throw new XCISException();
        } catch (SignatureException e) {
            throw new XCISException();
        } catch (InvalidKeyException e) {
            throw new XCISException();
        }

        try {
            if (this.checkCert) {
                cert.checkValidity();
                cert.verify(caCert.getPublicKey());
            }
        } catch (CertificateExpiredException e) {

        } catch (CertificateNotYetValidException e) {

        } catch (CertificateException e) {

        } catch (NoSuchAlgorithmException e) {

        } catch (InvalidKeyException e) {

        } catch (NoSuchProviderException e) {

        } catch (SignatureException e) {

        }

        return cert;
    }

    @Override
    public char[] getPassword() {
        return new char[0];
    }
}
