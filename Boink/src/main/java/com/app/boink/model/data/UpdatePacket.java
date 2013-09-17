package com.app.boink.model.data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by goof_troop on 9/12/13.
 */
public class UpdatePacket extends BoinkObject {

    private static final long serialVersionUID = 0;

    private HashMap<String, String> info;

    private boolean updated;

    public UpdatePacket(HashMap<String, String> info) {

        super();

        if (info == null) {
            // logger
            throw new NullPointerException();
        }

        this.info = info;

        updated = false;
    }

    public HashMap<String, String> getInfo() {
        return info;
    }

    public void success(boolean updated) {
        this.updated = updated;
    }

    public boolean isUpdated() {
        return updated;
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
