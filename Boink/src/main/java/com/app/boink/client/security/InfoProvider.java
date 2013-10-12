package com.app.boink.client.security;

/**
 * Created by goof_troop on 10/12/13.
 */
public final class InfoProvider {

    private static byte[] deviceId = null;
    private static String sessionId = "";

    public InfoProvider(final byte[] d, final String s) {
        deviceId = d;
        sessionId = s;
    }

    public static byte[] getDeviceId() {

        byte[] temp = deviceId;
        deviceId = null;
        return temp;
    }

    public static String getSessionId() {

        String temp = sessionId;
        sessionId = null;
        return temp;
    }
}
