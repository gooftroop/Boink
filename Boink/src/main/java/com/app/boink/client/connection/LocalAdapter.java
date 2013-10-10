package com.app.boink.client.connection;

import com.app.boink.exception.BoinkException;
import com.app.boink.prototype.BoinkObject;
import com.app.boink.server.network.LocalServer;

/**
 * Created by goof_troop on 9/12/13.
 */
public final class LocalAdapter extends Connection {

    private LocalServer client;

    public LocalAdapter() {

        // since we're connecting on the same JDI, ignore authentication info
        client = new LocalServer();
    }

    /*
     *
     */
    public void connect(int port, String url, String user, String password) throws BoinkException {
        throw new BoinkException("");
    }

    @Override
    public void close() {

        client.close();
        client = null;
    }

    /*
     *
     */
    @Override
    public void write(final BoinkObject data) throws BoinkException {

        // event handlers
        client.write(data);
    }
}
