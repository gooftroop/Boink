package com.app.boink.model.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by goof_troop on 9/12/13.
 */
public class Task extends BoinkObject {

    private static final long serialVersionUID = 0;

    private String uuid;
    private String commonName;

    public Task() {

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
