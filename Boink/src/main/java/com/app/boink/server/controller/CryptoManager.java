package com.app.boink.server.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Observable;
import java.util.Observer;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by goof_troop on 9/13/13.
 */
// TODO: auto task to re generate password and salt
public class CryptoManager implements Observer {

    private volatile SecretKey secret;
    private volatile char[] password;
    private volatile byte[] salt;
    private SecretKeyFactory factory;

    private static CryptoManager instance = null;

    public static CryptoManager getInstance() {

        if (instance == null)
            instance = new CryptoManager();

        return instance;
    }

    private CryptoManager() {

        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            // logger
        }

        generate();
    }

    private void generate() {


        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);

        try {
            secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        } catch (InvalidKeySpecException e) {
            // logger
        }
    }

    private void reKey() {
        // on update, reKey
    }

    public SecretKey getSecret() {
        return secret;
    }

    @Override
    public void update(Observable observable, Object o) {

        if (o instanceof char[])
            password = (char[]) o;
        else if (o instanceof byte[])
            salt = (byte[]) o;
        else
            return;

        generate();
        reKey();
    }
}
