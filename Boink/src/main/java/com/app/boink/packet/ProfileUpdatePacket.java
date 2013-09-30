package com.app.boink.packet;

import java.util.HashMap;

/**
 * Created by goof_troop on 9/12/13.
 */
public class ProfileUpdatePacket extends BoinkPacket {

    private HashMap<String, String> info;

    public ProfileUpdatePacket(HashMap<String, String> info, final long sessionId) {

        super(sessionId);

        if (info == null) {
            // logger
            throw new NullPointerException();
        }

        this.info = info;

        success(false);
    }

    public HashMap<String, String> getInfo() {
        return info;
    }
}
