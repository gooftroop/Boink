package com.app.boink.client.main;

import com.app.boink.client.connection.ConnectionManager;
import com.app.boink.client.security.InfoProvider;
import com.app.boink.exception.BoinkException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by goof_troop on 10/11/13.
 */
public class Boink implements Observer {

    private String CERT_PATH = "/";

    // Keep all contstants private to prevent any possible alteration
    public static final boolean IS_LOCAL;
    public static final int PORT;
    public static final String URL;
    public static final int FIELD_LENGTH = 64;

    static {

        // obtain from property reader
        IS_LOCAL = false;
        PORT = 0;
        URL = "";

    }

    // Internally hardcoded port. The server will have the same property and cannot be configurable.
    private static final int SSO_PORT = 22385;

    // Internal static variables. deviceId and the x509 Certificate server as authorization keys
    // to the server. If the connection is internal, these will be null.
    private byte[] deviceId;
    private X509Certificate deviceCertificate;

    // Do these need to exist beyond the server check?
    private final String username;
    private final String password;

    private ConnectionManager mgr;

    public Boink() {

        mgr = ConnectionManager.getInstance();
        username = "";
        password = "";

        FileInputStream fis = null;
        ByteArrayInputStream bais = null;

        try {

            // use FileInputStream to read the file
            fis = new FileInputStream(CERT_PATH);

            // read the bytes
            byte value[] = new byte[fis.available()];
            fis.read(value);
            bais = new ByteArrayInputStream(value);

            // get X509 certificate factory
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

            // certificate factory can now create the certificate
            deviceCertificate = (X509Certificate)certFactory.generateCertificate(bais);

        } catch (IOException e) {
            // logger
            return;
        } catch (CertificateException e) {
            // logger
            return;
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(bais);
        }

        // Pull the device ID out from the SubjectAltName field of the device Certificate
        try {
            deviceId = (byte[])deviceCertificate.getSubjectAlternativeNames().toArray()[0];
        } catch (CertificateParsingException e) {
            // logger
            return;
        }

        // find or create deviceID
        // do the SSO and obtain session Id
        if (IS_LOCAL)
            configureLocal();
        else
            configureRemote();

    }

    private void configureLocal() {

        byte[] s = new byte[64];

        try {
            SecureRandom.getInstance("SHA1PRNG", "SUN").nextBytes(s);
        } catch (NoSuchProviderException e) {
            // logger
            // fail miserably
        } catch (NoSuchAlgorithmException e) {
            // logger
            // fail miserably
        }

        new InfoProvider(deviceId, Base64.encodeBase64String(s));
    }

    private void configureRemote() {

        try {
            mgr.write(SSO_PORT, URL, deviceCertificate, this);
        } catch (BoinkException e) {
            // logger
            // something went wrong with SSO. Fail miserably.
        }

        mgr.connect(PORT, URL, username, password);
    }

    @Override
    public void update(Observable observable, Object o) {

        if (o instanceof String)
            new InfoProvider(deviceId, (String) o);
        else {
            // do something? Is it possible this will ever be called for anything other than sessionId?
        }
    }
}
