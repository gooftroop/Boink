package com.app.boink.util;

import java.util.logging.Logger;

/**
 * Created by goof_troop on 9/22/13.
 */
// Exception handler. Log and throw the exception
// Any exception that occurs thats not immediately wrapped into a custom exception will get passed
// into here to get logged then thrown as a Home Exception
public class Except {

    private static final Logger logger = Logger.getLogger(Except.class.getName());

    public static void except(String msg, Exception e) throws Exception {

        logger.severe("Exception " + e.toString() + "occured. " + msg);

        if (e instanceof NullPointerException) {
            //TODO as coding continues, create cases for specific ErrorCode messages to wrap into BoinkException
        } else {

        }
    }

    public static void except(String msg, Throwable e) throws Exception {

        logger.severe("Exception " + e.toString() + "occured. " + msg);

        if (e instanceof NullPointerException) {
            //TODO as coding continues, create cases for specific ErrorCode messages to wrap into BoinkException
        } else {

        }
    }
}
