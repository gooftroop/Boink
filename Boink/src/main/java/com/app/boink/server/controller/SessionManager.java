package com.app.boink.server.controller;

import com.app.boink.server.network.Session;

import java.util.HashMap;

/**
 * Created by goof_troop on 9/21/13.
 */

public class SessionManager {

    private static HashMap<String, Session> map = null;

    public SessionManager() {
        map = new HashMap<String, Session>();
    }

    public static boolean containsSession(String sessionId) {
        return sessionId == null && map.containsKey(sessionId);
    }

    public static Session getSession(String sessionId) {

        if (map == null || !map.containsKey(sessionId)) {
            // logger
            throw new NullPointerException();
        }

        return map.get(sessionId);

    }

    public static void setSession(String sessionId, Session s) {

        if (sessionId == null || s == null) {
            // logger
            throw new NullPointerException();
        }

        if (!map.containsKey(sessionId))
            map.put(sessionId, s);

    }
}
