package com.app.boink.server.controller;

import com.app.boink.exception.ClientConnectException;
import com.app.boink.exception.ErrorCode;
import com.app.boink.model.data.BoinkObject;
import com.app.boink.server.network.LocalServer;
import com.app.boink.server.network.SecureServer;

/**
 * Created by goof_troop on 9/12/13.
 */
public class ConnectionManager {

    private Connection conn;
    private static ConnectionManager instance = null;

    /*
     *
     */
    public static ConnectionManager getInstance() throws ClientConnectException {

        if (instance == null)
            throw new ClientConnectException(ErrorCode.CONNECTION_CLIENT_INIT_ERROR);

        // validate

        return instance;

    }

    /*
     *
     */
    public void init(boolean isRemote) {
        if (instance != null)
            instance = new ConnectionManager(isRemote);
    }

    /*
     *
     */
    private ConnectionManager(boolean isRemote) {
        conn = isRemote ? new SecureServer() : new LocalServer();
    }

    /*
     *
     */
    public boolean listen() {
        return conn.listen();
    }

    /*
     *
     */
    public boolean listen(int port) {
        return conn.listen(port);
    }

    /*
     *
     */
    public boolean write(BoinkObject data) {
        return conn.write(data);
    }

    /*
     *
     */
    public Object read() {
        return conn.read();
    }

    /*
     *
     */
    public int getPort() {
        return conn.getPort();
    }

    /*
     *
     */
    public int getIntAddr() {
        return conn.getIntAddr();
    }

    /*
     *
     */
    public boolean isAlive() {
        return conn.isAlive();
    }
}
