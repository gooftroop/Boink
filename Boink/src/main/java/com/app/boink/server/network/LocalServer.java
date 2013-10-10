package com.app.boink.server.network;

import com.app.boink.prototype.BoinkObject;
import com.app.boink.server.controller.CommunicationManager;

/**
 * Created by goof_troop on 9/12/13.
 */

// this will be packaged with the stand-alone app.
// the client will connect through CommunicationManager,
// but the client connection adapter will be forced to
// connect locally. There will be no need for SSL or sessions
public final class LocalServer {


    public LocalServer() {

        CommunicationManager.addEventListener(this, new ConnectionHandler());

    }

    public void close() {

        CommunicationManager.removeEventListener(this);

    }

    public void write(final BoinkObject o) {


        // fire off a new communication event.
        // the event should have the session id to work on and
        // the information to work with
        CommunicationManager.connect(o, this);

    }
}
