package com.app.boink.client.connection;

import com.app.boink.exception.ClientConnectException;
import com.app.boink.exception.ErrorCode;
import com.app.boink.model.data.BoinkObject;

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
        try {
            conn = isRemote ? new RemoteAdapter() : new LocalAdapter();
        } catch (Exception e) {
            // logger
        }
    }

    /*
     *
     */
    public boolean connect(String user, String password) {
        return conn.connect(user, password);
    }

    /*
     *
     */
    public boolean connect(int port, String url, String user, String password) {

        try {
            return conn.connect(port, url, user, password);
        } catch (UnsupportedOperationException e) {
            return connect(user, password);
        }
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
