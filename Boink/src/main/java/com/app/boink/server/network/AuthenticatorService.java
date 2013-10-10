package com.app.boink.server.network;

import com.app.boink.prototype.Profile;

import java.util.HashMap;

/**
 * Created by goof_troop on 9/12/13.
 */

public class AuthenticatorService {

    public static boolean authenticate(String userName, String password, Session session) {

        // validate input
        if (userName == null || userName.isEmpty()) {
            // exception
        }

        if (password == null || password.isEmpty()) {
            // exception
        }

        if (session == null || !session.isValid()) {
            // exception
        }


        // decrypt into temp string, then securely erase that string
        try {
            return session.getProfile().getUserName().equals(userName) && session.getProfile().getPassword().equals(password);
        } catch (NullPointerException e) {
            // log
            return false;
        }
    }

    public static boolean register(String userName, String password, Session session) {

        // validate input
        if (userName == null || userName.isEmpty()) {
            // exception
        }

        if (password == null || password.isEmpty()) {
            // exception
        }

        if (session == null || !session.isValid()) {
            // exception
        }

        if (session.getProfile() != null)
            return false;
        else {
            session.setProfile(new Profile(userName, password));
            return true;
        }
    }

    // Can't call updateUser unless authentication has already occurred
    public static boolean updateUser(HashMap<String, String> info, Session session) {

        // validate input
        if (info == null || info.isEmpty()) {
            // exception
        }

        if (session == null || !session.isValid()) {
            // exception
        }

        Profile profile;

        if ((profile = session.getProfile()) == null) {
            // error
        }

        try {

            for (String key : info.keySet())
                profile.update(key, info.get(key));

        } catch (NullPointerException e) {

        }

        return true;

    }
}
