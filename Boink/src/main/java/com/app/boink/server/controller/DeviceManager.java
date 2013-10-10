package com.app.boink.server.controller;

import com.app.boink.exception.BoinkException;
import com.app.boink.exception.ErrorCode;
import com.app.boink.exception.PickleException;
import com.app.boink.server.main.BoinkServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by goof_troop on 10/5/13.
 */
public class DeviceManager {

    // This is the registry of devices. This will be loaded from the DB or serialized file
    private static HashMap<byte[], Boolean> devices;

    public DeviceManager() throws PickleException {

        if (BoinkServer.IS_LOCAL)
            loadLocal();
        else
            loadDB();

    }

    public static boolean isActive(byte[] id) {

        if (devices == null) {
            // logger
            throw new NullPointerException();
        }


        if (devices.containsKey(id))
            return devices.get(id);
        else
            return false;

    }

    public static boolean contains(byte[] id) {

        if (devices == null) {
            // logger
            throw new NullPointerException();
        }

        return devices.containsKey(id);
    }

    public static void addDevice(byte[] id) throws BoinkException {

        if (devices == null) {
            // logger
            throw new NullPointerException();
        }

        if (!devices.containsKey(id) || !devices.get(id))
            devices.put(id, true);
        else
            throw new BoinkException("TODO");
    }

    private void loadLocal() throws PickleException {

        ObjectInputStream ois = null;

        try {

            ois = new ObjectInputStream(new FileInputStream("/boink/resource/.data/A1B$KI839BD00JA.ser"));

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

            devices = (HashMap<byte[], Boolean>) ois.readObject();

        } catch (IOException e) {
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
    }

    private void saveLocal() throws PickleException {

        ObjectOutputStream oos = null;

        try {

            oos = new ObjectOutputStream(new FileOutputStream("/boink/resource/.data/A1B$KI839BD00JA.ser"));

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
            oos.writeObject(devices);
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

    private void loadDB() {

    }

    private void saveDB() {

    }
}
