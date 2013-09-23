package com.app.boink.server.network;

import com.app.boink.exception.PickleException;
import com.app.boink.model.data.Profile;
import com.app.boink.server.controller.Pickler;

import java.util.HashMap;

/**
 * Created by goof_troop on 9/12/13.
 */
// TODO this is local connection only. figure out how exactly you're going to handle socket connections, then implement dual functionality
public class AuthenticatorService {

    private Profile profile;

    public AuthenticatorService() {
        profile = null;
    }

    public boolean authenticate(String userName, String password) {

        // validate input

        try {
            profile = (Profile) Pickler.dePickle(userName);
        } catch (PickleException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }

        // decrypt into temp string, then securely erase that string

        return profile.getPassword().equals(password);
    }

    public boolean register(String userName, String password) {

        // validate input

        if (!Pickler.isRegistered(userName)) {

            profile = new Profile();

            profile.setCommonName(userName);
            profile.setUserName(userName);

            // password must be encrypted
            profile.setPassword(password);

            return true;
        } else
            return false;
    }

    public boolean updateUser(HashMap<String, String> info) {

        // validate input

        if (!info.containsKey("userName")) {
            // logger
            return false;
        }

        if (Pickler.isRegistered(info.get("userName"))) {

            try {
                profile = (Profile) Pickler.dePickle(info.get("userName"));
            } catch (PickleException e) {
                return false;
            } catch (NullPointerException e) {
                return false;
            }

            for (String key : info.keySet())
                profile.update(key, info.get(key));

            return true;

        } else
            return false;
    }

    public Profile getProfile() {
        // do secure auth here
        return profile;
    }

    public void destroy() {
        profile = null;
        System.gc();
    }
}
