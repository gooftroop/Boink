package com.app.boink.prototype;

import java.util.UUID;

/**
 * Created by goof_troop on 9/12/13.
 */
public class Transaction extends BoinkObject {

    private String uuid;
    private String commonName;

    public Transaction() {

        super();

        uuid = UUID.randomUUID().toString();
    }

    public String getUUID() {
        return uuid;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {

        if (commonName == null)
            throw new NullPointerException();

        this.commonName = commonName;
    }
}
