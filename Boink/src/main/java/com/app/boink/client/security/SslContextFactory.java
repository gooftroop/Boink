package com.app.boink.client.security;

import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public final class SslContextFactory {

    private static final String PROTOCOL = "TLS";
    private static final SSLContext CONTEXT;
    private static SecureRandom sec;

    static {

        String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");

        if (algorithm == null)
            algorithm = "SunX509";

        KeyStore ks, ts;
        KeyManagerFactory kmf = null;
        TrustManagerFactory tmf = null;

        try {

            ks = KeyStore.getInstance("JKS");
            ts = KeyStore.getInstance("JKS");

            if (sec == null)
                throw new NullPointerException();

            sec.nextBytes(new byte[64]);
            byte[] seed = sec.generateSeed(64);

            // Set up key manager factory to use our key store
            kmf = KeyManagerFactory.getInstance(algorithm);
            kmf.init(ks, new String(seed, "ASCII").toCharArray());

            tmf = TrustManagerFactory.getInstance(algorithm);
            tmf.init(ts);

        } catch (KeyStoreException e) {

        } catch (NoSuchAlgorithmException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (UnrecoverableKeyException e) {

        } catch (NullPointerException e) {

        }


        try {

            // Initialize the SSLContext to work with our key managers.
            CONTEXT = SSLContext.getInstance(PROTOCOL);
            CONTEXT.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        } catch (Exception e) {
            throw new Error("Failed to initialize the server-side SSLContext", e);
        }
    }

    public static SSLContext getContext() {
        return CONTEXT;
    }

    public static void setSecureRandom(SecureRandom secure) {

        if (secure != null)
            sec = secure;
    }
}