package com.app.boink.packet;

/**
 * Created by goof_troop on 9/26/13.
 */
public abstract class BoinkPacket {

    // really want this to be final and every new packet uses this. This way the sessionId is set once
    // and not visible or passed around in upper levels.

    private final byte[] deviceId;
    private final String sessonId;
    private boolean updated;

    public BoinkPacket(final String sessionId, final byte[] deviceId) {
        updated = false;
        this.sessonId = sessionId;
        this.deviceId = deviceId;
    }

    public String getSessonId() {
        return sessonId;
    }

    public byte[] getDeviceId() {
        return deviceId;
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
