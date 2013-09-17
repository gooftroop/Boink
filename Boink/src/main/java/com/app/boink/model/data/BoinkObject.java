package com.app.boink.model.data;

import java.io.Serializable;

/**
 * Created by goof_troop on 9/12/13.
 */
public abstract class BoinkObject implements Serializable {

    protected String uuid, commonName;

    public BoinkObject() {
        uuid = null;
        commonName = null;
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
