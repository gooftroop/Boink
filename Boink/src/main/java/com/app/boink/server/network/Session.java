package com.app.boink.server.network;

import com.app.boink.prototype.Account;
import com.app.boink.prototype.BoinkObject;
import com.app.boink.prototype.Profile;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by goof_troop on 9/21/13.
 */
public class Session extends BoinkObject {

    private static final long serialVersionUID = 0;

    // Configurable?
    private static final long TIMEOUT = 900000;

    private boolean invalid;
    private long lastaccess, createdate;
    private Profile profile;
    private LinkedList<Account> accounts;

    private final String sessionId;
    private final byte[] deviceId;

    public Session(final byte[] deviceId) {

        this(UUID.randomUUID().toString(), deviceId);
    }

    public Session(final String sessionId, final byte[] deviceId) {

        createdate = lastaccess = System.currentTimeMillis();
        invalid = false;
        accounts = new LinkedList<Account>();
        this.sessionId = sessionId;
        this.deviceId = deviceId;
    }

    public void invalidate() {

        invalid = true;
    }

    public String getSessionId() {

        if (!isValid())
            throw new IllegalAccessError("Session is invalid");

        access();

        return sessionId;
    }

    public byte[] getDeviceId() {

        if (!isValid())
            throw new IllegalAccessError("Session is invalid");

        access();

        return deviceId;

    }

    public Profile getProfile() {

        if (!isValid())
            throw new IllegalAccessError("Session is invalid");

        access();

        return profile;
    }

    public void setProfile(Profile p) {

        if (!isValid())
            throw new IllegalAccessError("Session is invalid");

        access();

        if (p == null) {
            // log and except
        }

        this.profile = p;
    }

    public void addAccount(Account a) {

        if (!isValid())
            throw new IllegalAccessError("Session is invalid");

        access();

        if (a != null)
            accounts.add(a);
    }

    public void addAccounts(Collection<Account> c) {

        if (!isValid())
            throw new IllegalAccessError("Session is invalid");

        access();

        if (c != null && !c.isEmpty())
            accounts.addAll(c);
    }

    public Account getAccount(String name) {

        if (!isValid())
            throw new IllegalAccessError("Session is invalid");

        access();

        if (name == null)
            return null;

        for (Account a : accounts)
            if (a.getAccountName().equals(name))
                return a;

        return null;
    }

    public Iterator<Account> getAccounts() {

        if (!isValid())
            throw new IllegalAccessError("Session is invalid");

        access();

        return accounts.iterator();
    }

    public boolean isValid() {

        // Check to see if the session is stale
        invalid = (lastaccess - createdate > TIMEOUT);

        return !invalid;
    }

    private void access() {
        lastaccess = System.currentTimeMillis();
    }
}
