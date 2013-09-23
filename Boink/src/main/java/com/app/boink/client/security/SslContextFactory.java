package com.app.boink.client.security;

import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import io.netty.handler.ssl.SslHandler;

/**
 * Creates a bogus {@link SSLContext}.  A client-side context created by this
 * factory accepts any certificate even if it is invalid.  A server-side context
 * created by this factory sends a bogus certificate defined in {@link KeyStore}.
 * <p/>
 * You will have to create your context differently in a real world application.
 * <p/>
 * <h3>Client Certificate Authentication</h3>
 * <p/>
 * To enable client certificate authentication:
 * <ul>
 * <li>Enable client authentication on the server side by calling
 * {@link SSLEngine#setNeedClientAuth(boolean)} before creating
 * {@link SslHandler}.</li>
 * <li>When initializing an {@link SSLContext} on the client side,
 * specify the {@link KeyManager} that contains the client certificate as
 * the first argument of {@link SSLContext#init(KeyManager[], TrustManager[], SecureRandom)}.</li>
 * <li>When initializing an {@link SSLContext} on the server side,
 * specify the proper {@link TrustManager} as the second argument of
 * {@link SSLContext#init(KeyManager[], TrustManager[], SecureRandom)}
 * to validate the client certificate.</li>
 * </ul>
 */
public final class SslContextFactory {

    private static final String PROTOCOL = "TLS";
    private static final SSLContext CONTEXT;

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

            SecureRandom sec = SecureRandom.getInstance("SHA1PRNG", "SUN");
            sec.nextBytes(new byte[64]);
            // TODO seed needs to be pulled from some sort of secure store
            byte[] seed = null;

            // Set up key manager factory to use our key store
            kmf = KeyManagerFactory.getInstance(algorithm);
            kmf.init(ks, new String(seed, "ASCII").toCharArray());

            tmf = TrustManagerFactory.getInstance(algorithm);
            tmf.init(ts);

        } catch (KeyStoreException e) {

        } catch (NoSuchProviderException e) {

        } catch (NoSuchAlgorithmException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (UnrecoverableKeyException e) {

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
}