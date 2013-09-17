package com.app.boink.client.connection;

import com.app.boink.exception.ClientConnectException;
import com.app.boink.model.data.BoinkObject;

/**
 * Created by goof_troop on 9/12/13.
 */
public class LocalAdapter extends Connection {

    private com.app.boink.server.controller.ConnectionManager client;

    /*
     *
     */
    public LocalAdapter() {
        client = null;
    }

    /*
     *
     */
    @Override
    public boolean connect(String user, String password) {

        // since we're connecting on the same JDI, ignore authentication info

        try {
            client = com.app.boink.server.controller.ConnectionManager.getInstance();
        } catch (ClientConnectException e) {
            return false;
        } catch (Exception e) {
            // logger
            return false;
        }

        if (client == null)
            return false;
        else
            return true;
    }

    /*
     *
     */
    public boolean connect(int port, String url, String user, String password) {
        throw new UnsupportedOperationException();
    }

    /*
     *
     */
    @Override
    public boolean write(BoinkObject data) {
        return client.write(data);
    }

    /*
     *
     */
    @Override
    public Object read() {
        return client.read();
    }

    /*
     *
     */
    @Override
    public int getPort() {
        return 0;
    }

    /*
     *
     */
    @Override
    public int getIntAddr() {
        return 0;
    }

    /*
     *
     */
    @Override
    public boolean isAlive() {
        return false;
    }
}
