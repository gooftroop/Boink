package com.app.boink.client.connection;

import com.app.boink.model.data.BoinkObject;

/**
 * Created by goof_troop on 9/12/13.
 */
public abstract class Connection {

    /*
     *
     */
    public abstract boolean connect(String user, String password);

    /*
     *
     */
    public abstract boolean connect(int port, String url, String user, String password);

    // move to thread class
    /*
     *
     */
    public abstract boolean write(BoinkObject data);

    /*
     *
     */
    public abstract Object read();

    /*
     *
     */
    public abstract int getPort();

    /*
     *
     */
    public abstract int getIntAddr();

    /*
     *
     */
    public abstract boolean isAlive();
}
