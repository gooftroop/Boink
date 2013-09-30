package com.app.boink.prototype;

import java.util.EventListener;

/**
 * Created by goof_troop on 9/28/13.
 */
public interface ConnectionListener extends EventListener {

    public void connectionEstablished(ConnectionEvent evt);

}
