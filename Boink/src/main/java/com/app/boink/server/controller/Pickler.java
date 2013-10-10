package com.app.boink.server.controller;

import com.app.boink.exception.ErrorCode;
import com.app.boink.exception.PickleException;
import com.app.boink.server.network.Session;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by goof_troop on 9/12/13.
 */
public class Pickler {

    public static void pickle(Session session) throws PickleException {

        if (session == null) {
            // logger
            throw new NullPointerException();
        }

        ObjectOutputStream oos = null;

        try {

            oos = new ObjectOutputStream(new FileOutputStream("/boink/resource/.data/" + session.getSessionId() + ".ser"));

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
            oos.writeObject(session);
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
    public static Session dePickle(String sessionId) throws PickleException {

        ObjectInputStream ois = null;
        Session o = null;

        try {

            ois = new ObjectInputStream(new FileInputStream("/boink/resource/.data/" + sessionId + ".ser"));

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

            o = (Session) ois.readObject();

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

        return o;

    }
}
