package com.app.boink.client.security;

import com.app.boink.model.data.BoinkObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by goof_troop on 9/12/13.
 */
public class ClientEncryptor {

    private volatile SecretKey secret;
    private volatile char[] password;
    private volatile byte[] salt;
    private volatile SecretKeyFactory factory;
    private volatile Cipher cipher;
    private volatile AlgorithmParameters params;
    private volatile byte[] iv;

    public ClientEncryptor() {

        // get the password and the salt from the server (this is pulled from the crypto manager). this will require ssl auth

        init();
    }

    private String toString(byte[] content) {
        return "";
    }

    private byte[] toArray(String content) {
        return null;
    }

    private void init() {

        try {

            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        } catch (NoSuchAlgorithmException e) {
            // logger
        }

        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);

        try {

            secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

        } catch (InvalidKeySpecException e) {
            // logger
        }

        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchPaddingException e) {
            // logger
        } catch (NoSuchAlgorithmException e) {
            // logger
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, secret);
        } catch (InvalidKeyException e) {
            // logger
        }

        try {
            iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
        } catch (InvalidParameterSpecException e) {
            // logger
        }
    }

    public byte[] encrypt(String expression) {

        try {

            return cipher.doFinal(expression.getBytes("UTF-8"));

        } catch (UnsupportedEncodingException e) {
            // logger
        } catch (IllegalBlockSizeException e) {
            // logger
        } catch (BadPaddingException e) {
            // logger
        }

        return null;
    }

    public SealedObject encrypt(BoinkObject object) {

        if (object == null) {
            // logger
            throw new NullPointerException();
        }

        try {

            return new SealedObject(object, cipher);

        } catch (UnsupportedEncodingException e) {
            // logger
        } catch (IllegalBlockSizeException e) {
            // logger
        } catch (IOException e) {
            // logger
        }

        return null;
    }

    public String decrypt(byte[] content) {

        if (content == null || content.length == 0) {
            // logger
            // exception
        }

        try {

            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));

            return new String(cipher.doFinal(content), "UTF-8");

        } catch (NoSuchPaddingException e) {
            // logger
        } catch (NoSuchAlgorithmException e) {
            // logger
        } catch (InvalidAlgorithmParameterException e) {
            // logger
        } catch (InvalidKeyException e) {
            // logger
        } catch (IllegalBlockSizeException e) {
            // logger
        } catch (BadPaddingException e) {
            // logger
        } catch (UnsupportedEncodingException e) {
            // logger
        } catch (IOException e) {
            // logger
        }

        return null;
    }

    public Object decrypt(SealedObject object) {

        if (object == null) {
            // logger
            // exception
        }

        try {

            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));

            return object.getObject(cipher);

        } catch (NoSuchPaddingException e) {
            // logger
        } catch (NoSuchAlgorithmException e) {
            // logger
        } catch (InvalidAlgorithmParameterException e) {
            // logger
        } catch (InvalidKeyException e) {
            // logger
        } catch (IllegalBlockSizeException e) {
            // logger
        } catch (BadPaddingException e) {
            // logger
        } catch (UnsupportedEncodingException e) {
            // logger
        } catch (IOException e) {
            // logger
        } catch (ClassNotFoundException e) {
            // logger
        }

        return null;
    }
}
