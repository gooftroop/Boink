package com.app.boink.client.connection;

import com.app.boink.client.main.Boink;
import com.app.boink.exception.BoinkException;
import com.app.boink.exception.ClientConnectException;
import com.app.boink.prototype.BoinkObject;

/**
 * Created by goof_troop on 9/12/13.
 */
public class ConnectionManager {

    private Connection conn;
    private static final ConnectionManager instance = new ConnectionManager();

    /*
     *
     */
    public static ConnectionManager getInstance() throws ClientConnectException {
        return instance;
    }

    /*
     *
     */
    private ConnectionManager() {

        try {
            conn = Boink.IS_LOCAL ? new LocalAdapter() : new RemoteAdapter();
        } catch (Exception e) {
            // logger
        }
    }

    /*
     *
     */
    public void connect(int port, String url, String user, String password) {

        try {
            conn.connect(port, url, user, password);
        } catch (BoinkException e) {

        }
    }

    /*
     *
     */
    public void write(BoinkObject data) {

        try {
            conn.write(data);
        } catch (BoinkException e) {

        }
    }
}
