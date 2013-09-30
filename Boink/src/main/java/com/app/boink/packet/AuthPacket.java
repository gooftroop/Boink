package com.app.boink.packet;

/**
 * Created by goof_troop on 9/12/13.
 */
public class AuthPacket extends BoinkPacket {

    private final String userName;
    private final String password;

    private boolean authd;

    public AuthPacket(final String userName, final String password, final long sessionId) {

        super(sessionId);

        if (userName == null || password == null) {
            // logger
            throw new NullPointerException();
        }

        this.userName = userName;
        this.password = password;

        success(false);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void success(boolean authd) {
        success(authd);
    }

    public boolean isSuccessful() {
        return isUpdated();
    }
}
