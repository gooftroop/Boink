package com.app.boink.server.controller;

import com.app.boink.model.data.Session;

import java.util.HashMap;

/**
 * Created by goof_troop on 9/21/13.
 */

public class SessionManager {

    private static HashMap<String, Session> map = null;

    public SessionManager() {
        map = new HashMap<String, Session>();
    }

    public static Session getSession(String uuid) {

        if (map == null || !map.containsKey(uuid)) {
            // logger
            throw new NullPointerException();
        }

        return map.get(uuid);

    }

    public static void setSession(String uuid, Session s) {

        if (uuid == null || s == null) {
            // logger
            throw new NullPointerException();
        }

        if (!map.containsKey(uuid))
            map.put(uuid, s);

    }

    public static void invalidate(String uuid) {

        if (uuid == null)
            return;

        map.put(uuid, null);
        map.remove(uuid);
    }
}
