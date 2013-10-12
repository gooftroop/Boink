package com.app.boink.packet;

import com.app.boink.client.main.Boink;
import com.app.boink.client.security.InfoProvider;

import java.util.Arrays;

/**
 * Created by goof_troop on 9/26/13.
 */
public abstract class BoinkPacket {

    private static final byte[] deviceId = InfoProvider.getDeviceId();
    private static final String sessionId = InfoProvider.getSessionId();

    private boolean updated;

    public BoinkPacket() {

        updated = false;
    }

    protected String getSessionId() {
        return sessionId;
    }

    protected byte[] getDeviceId() {
        return Arrays.copyOf(deviceId, Boink.FIELD_LENGTH);
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
}
