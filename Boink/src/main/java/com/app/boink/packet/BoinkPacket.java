package com.app.boink.packet;

/**
 * Created by goof_troop on 9/26/13.
 */
public abstract class BoinkPacket {

    private final long sessonId;
    private boolean updated;

    public BoinkPacket(final long sessionId) {
        updated = false;
        this.sessonId = sessionId;
    }

    public long getSessonId() {
        return sessonId;
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
