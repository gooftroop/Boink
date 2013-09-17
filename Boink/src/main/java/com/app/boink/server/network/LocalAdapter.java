package com.app.boink.server.network;

import com.app.boink.model.data.AuthPacket;
import com.app.boink.model.data.BoinkObject;
import com.app.boink.model.data.RegisterPacket;
import com.app.boink.model.data.UpdatePacket;

/**
 * Created by goof_troop on 9/12/13.
 */
public class LocalAdapter extends Connection {

    BoinkObject object;

    public LocalAdapter() {
        object = null;
    }

    @Override
    public boolean listen() {
        return false;
    }

    public boolean listen(int port) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean write(BoinkObject data) {

        if (data instanceof AuthPacket) {

        } else if (data instanceof RegisterPacket) {

        } else if (data instanceof UpdatePacket) {

        }

        return false;
    }

    @Override
    public Object read() {
        return object;
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public int getIntAddr() {
        return 0;
    }

    @Override
    public boolean isAlive() {
        return false;
    }
}
