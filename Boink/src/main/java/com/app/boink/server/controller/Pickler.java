package com.app.boink.server.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.app.boink.exception.ErrorCode;
import com.app.boink.exception.PickleException;
import com.app.boink.model.data.BoinkObject;

/**
 * Created by goof_troop on 9/12/13.
 */
public class Pickler {

    private static HashMap<String, String> reference = null;

    public Pickler() {
        reference = new HashMap<String, String>();
    }

    public static boolean isRegistered(String commonName) {

        if (reference == null) {
            // logger
            throw new NullPointerException();
        }

        return reference.containsKey(commonName);
    }

    // local implementation only. do DB
    public static void pickle(BoinkObject data) throws PickleException {

        if (reference == null) {
            // logger
            throw new NullPointerException();
        }

        ObjectOutputStream oos = null;

        try {

            oos = new ObjectOutputStream(new FileOutputStream("/boink/resource/.data/" + data.getUUID() + ".ser"));

            if (!reference.containsValue(data.getUUID()))
                reference.put(data.getCommonName(), data.getUUID());

        } catch (FileNotFoundException e) {
            // logger
            throw new PickleException(ErrorCode.FILE_NOT_FOUND);
        } catch (IOException e) {
            // logger
            throw new PickleException(ErrorCode.OBJECT_STREAM_ERROR);
        } finally {

            try {
                if (oos != null)
                    oos.close();
            } catch (IOException i) {
                //logger
            }
        }

        try {
            oos.writeObject(data);
        } catch (IOException e) {
            // logger
            throw new PickleException(ErrorCode.OBJECT_WRITE_ERROR);
        } finally {

            try {
                oos.flush();
                oos.close();
            } catch (IOException i) {
                //logger
            }
        }

    }

    // local implementation only. do DB
    public static Object dePickle(String commonName) throws PickleException {

        if (reference == null) {
            // logger
            throw new NullPointerException();
        }

        ObjectInputStream ois = null;
        BoinkObject o = null;

        try {

            String uuid;

            if (reference.containsValue(commonName))
                uuid = reference.get(commonName);
            else
                throw new PickleException(ErrorCode.PICKLE_REFERENCE_ERROR);

            ois = new ObjectInputStream(new FileInputStream("/boink/resource/.data/" + uuid + ".ser"));

        } catch (FileNotFoundException e) {
            // logger
            throw new PickleException(ErrorCode.FILE_NOT_FOUND);
        } catch (IOException e) {
            // logger
            throw new PickleException(ErrorCode.OBJECT_STREAM_ERROR);
        } finally {

            try {
                if (ois != null)
                    ois.close();
            } catch (IOException i) {
                //logger
            }
        }

        try {

            o = (BoinkObject)ois.readObject();

        } catch(IOException e) {
            // logger
            throw new PickleException(ErrorCode.OBJECT_READ_ERROR);
        } catch (ClassCastException e) {
            // logger
            throw new PickleException(ErrorCode.CLASS_CAST_ERROR);
        } catch (ClassNotFoundException e) {
            // logger
            throw new PickleException(ErrorCode.CLASS_NOT_FOUND_ERROR);
        } finally {

            try {
                ois.close();
            } catch (IOException i) {
                //logger
            }
        }

        return o;

    }
}
