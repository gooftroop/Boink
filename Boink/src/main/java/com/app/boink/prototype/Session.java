package com.app.boink.prototype;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by goof_troop on 9/21/13.
 */
// TODO session access times
// TODO session invalidation
public class Session extends BoinkObject {

    private static final long serialVersionUID = 0;

    private boolean invalid;

    private Profile profile;
    private LinkedList<Account> accounts;

    private String sessionId = "";

    public Session() {

        invalid = false;
        accounts = new LinkedList<Account>();
        sessionId = UUID.randomUUID().toString();
    }

    public void invalidate() {

    }

    public String getSessionId() {
        return sessionId;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile p) {

        if (p == null) {
            // log and except
        }

        this.profile = p;
    }

    public void addAccount(Account a) {

        if (a != null)
            accounts.add(a);
    }

    public void addAccounts(Collection<Account> c) {

        if (c != null && !c.isEmpty())
            accounts.addAll(c);
    }

    public Account getAccount(String name) {

        if (name == null)
            return null;

        for (Account a : accounts)
            if (a.getAccountName().equals(name))
                return a;

        return null;
    }

    public Iterator<Account> getAccounts() {

        return accounts.iterator();
    }

    public boolean isValid() {
        return !invalid;
    }
}
