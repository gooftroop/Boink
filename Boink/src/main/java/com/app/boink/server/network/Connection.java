package com.app.boink.server.network;

import com.app.boink.model.data.BoinkObject;

/**
 * Created by goof_troop on 9/12/13.
 */
public abstract class Connection {

    public abstract boolean listen();

    public abstract boolean listen(int port);

    public abstract boolean write(BoinkObject data);

    public abstract Object read();

    public abstract int getPort();

    public abstract int getIntAddr();

    public abstract boolean isAlive();
}