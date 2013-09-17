package com.app.boink.model.data;

import java.io.Serializable;

/**
 * Created by goof_troop on 9/12/13.
 */
public class RegisterPacket extends BoinkObject {

    private static final long serialVersionUID = 0;

    private String userName;
    private String password;

    private boolean registered;

    public RegisterPacket(String userName, String password) {

        super();

        if (userName == null || password == null) {
            // logger
            throw new NullPointerException();
        }

        this.userName = userName;
        this.password = password;

        registered = false;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void success(boolean registered) {
        this.registered = registered;
    }

    public boolean isRegistered() {
        return registered;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public String toString() {
        return "";
    }
}
