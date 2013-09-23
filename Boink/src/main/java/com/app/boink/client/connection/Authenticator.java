package com.app.boink.client.connection;

import com.app.boink.exception.ClientConnectException;
import com.app.boink.model.data.BoinkObject;
import com.app.boink.model.packet.AuthPacket;
import com.app.boink.model.packet.ProfileUpdatePacket;
import com.app.boink.model.packet.RegisterPacket;

import java.util.HashMap;

/**
 * Created by goof_troop on 9/12/13.
 */
// somehow, I need to have a main() run on app start up. this should set which authenticator to use. local or wan
public class Authenticator {

    // Server URL - get this from configuration!
    private volatile String url = "gooftroop.synology.me";
    // Server user - get this from configuration!
    private volatile String user = "boinkadmin";
    // encrypt this!!
    private volatile String pass = "A0L42z@6e6!P1";

    private volatile int port = 8080; // TODO: refactor

    private ConnectionManager client;

    /*
     *
     */
    public Authenticator() {

        try {
            client = ConnectionManager.getInstance();
        } catch (ClientConnectException e) {
            // logger
            client = null;
        }
    }

    /*
     *
     */
    public boolean connect() {

        if (client == null)
            return false;

        return client.connect(port, url, user, pass);
    }

    /*
     *
     */
    public boolean authenticate(String userName, String password) {

        if (client == null)
            return false;

        // encrypt password into secure packet
        if (!client.write((BoinkObject) new AuthPacket(userName, password)))
            return false;

        try {
            return ((AuthPacket) client.read()).isSuccessful();
        } catch (NullPointerException e) {
            return false;
        }
    }

    /*
     *
     */
    public boolean registerUser(String userName, String password) {

        if (client == null)
            return false;

        // encrypt password into secure packet
        if (!client.write((BoinkObject) new RegisterPacket(userName, password)))
            return false;

        try {
            return ((RegisterPacket) client.read()).isRegistered();
        } catch (NullPointerException e) {
            return false;
        }
    }

    /*
     *
     */
    public boolean updateUser(HashMap<String, String> info) {

        if (client == null)
            return false;

        if (!client.write((BoinkObject) new ProfileUpdatePacket(info)))
            return false;

        try {
            return ((ProfileUpdatePacket) client.read()).isUpdated();
        } catch (NullPointerException e) {
            return false;
        }
    }
}
