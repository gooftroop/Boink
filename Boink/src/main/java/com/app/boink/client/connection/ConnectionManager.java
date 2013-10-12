package com.app.boink.client.connection;

import com.app.boink.client.main.Boink;
import com.app.boink.exception.BoinkException;
import com.app.boink.prototype.BoinkObject;

import java.security.cert.X509Certificate;

/**
 * Created by goof_troop on 9/12/13.
 */
public class ConnectionManager {

    private Connection conn;
    private static final ConnectionManager instance = new ConnectionManager();

    /*
     *
     */
    public static ConnectionManager getInstance() {
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

    public void write(int port, String url, X509Certificate cert, Boink caller) throws BoinkException, UnsupportedOperationException {

        conn.addObserver(caller);

        try {
            conn.write(port, url, cert);
        } catch (UnsupportedOperationException e) {

        } catch (BoinkException e) {

        }
    }
}
