package com.app.boink.server.network;

/**
 * Created by goof_troop on 9/12/13.
 */
// Server application ONLY. Used to connect to the Database
public class DBC {

    private static java.sql.Connection conn = null;

    public DBC() {
        // initialize DB connection here
    }

    public static String query(String content) {

        if (conn == null) {
            // logger
            throw new NullPointerException();
        }

        return "";
    }

    public static boolean update(String content) {

        if (conn == null) {
            // logger
            throw new NullPointerException();
        }

        return false;
    }
}