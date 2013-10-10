package com.app.boink.packet;

import java.security.SecureRandom;

/**
 * Created by goof_troop on 10/4/13.
 */
public class SSOPacket extends BoinkPacket {

    private SecureRandom sec;

    public SSOPacket(final String sessionId) {
        super(sessionId);
    }

    public void setSecureRandom(SecureRandom sec) {

        if (sec != null)
            this.sec = sec;
    }

    public void destroy() {
        sec = null;
    }
}
