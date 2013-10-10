package com.app.boink.packet;

/**
 * Created by goof_troop on 9/12/13.
 */
public class RegisterPacket extends BoinkPacket {

    private String userName;
    private String password;

    public RegisterPacket(String userName, String password, final String sessionId) {

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

    public void setRegistered(boolean registered) {
        success(registered);
    }

    public boolean isRegistered() {
        return isUpdated();
    }
}
