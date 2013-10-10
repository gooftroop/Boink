package com.app.boink.client.connection;

import com.app.boink.exception.BoinkException;
import com.app.boink.prototype.BoinkObject;
import com.app.boink.prototype.ConnectionEvent;
import com.app.boink.prototype.ConnectionListener;

import java.util.Observable;

/**
 * Created by goof_troop on 9/12/13.
 */
public abstract class Connection extends Observable implements ConnectionListener {

    // we only need to read here and not write back to the server. that's a seperate task.
    @Override
    public void connectionEstablished(ConnectionEvent evt) {

        setChanged();
        notifyObservers(evt.getSource());
    }

    /*
     *
     */
    public abstract void connect(int port, String url, String user, String password) throws BoinkException;

    /*
     *
     */
    public abstract void write(BoinkObject data) throws BoinkException;

    /*
     *
     */
    public abstract void close() throws BoinkException;

}
