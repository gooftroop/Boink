package com.app.boink.server.controller;

import com.app.boink.prototype.ConnectionEvent;
import com.app.boink.prototype.ConnectionListener;
import com.app.boink.util.EventListenerList;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by goof_troop on 9/12/13.
 */
public final class ConnectionManager {

    private static EventListenerList listenerList = new EventListenerList();
    private static ConnectionManager self = null;

    public ConnectionManager() {
        self = this;
    }


    public synchronized static void connect(ChannelHandlerContext ctx) {

        if (ctx != null)
            fireCommunicationEvent(ctx);
    }

    public synchronized static void addEventListener(ChannelHandlerContext ctx, ConnectionListener listener) {
        listenerList.add(ctx, listener);
    }

    public synchronized static void removeEventListener(ChannelHandlerContext ctx, ConnectionListener listener) {
        listenerList.remove(ctx, listener);
    }

    private static synchronized void fireCommunicationEvent(ChannelHandlerContext ctx) {

        Object[] listeners = listenerList.getListenerList();

        for (int i = 0; i < listeners.length; i = i + 2)
            if (listeners[i] == ChannelHandlerContext.class)
                ((ConnectionListener) listeners[i + 1]).connectionEstablished(new ConnectionEvent(self, ctx));

    }
}