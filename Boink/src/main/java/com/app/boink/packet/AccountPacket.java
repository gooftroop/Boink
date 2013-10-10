package com.app.boink.packet;

/**
 * Created by goof_troop on 9/22/13.
 */
public class AccountPacket extends BoinkPacket {

    // use this to transport account information and account updates, new auto transations

    public AccountPacket(final String sessionId) {
        super(sessionId);
    }
}
