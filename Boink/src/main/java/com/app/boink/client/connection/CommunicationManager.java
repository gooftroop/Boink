package com.app.boink.client.connection;

import com.app.boink.prototype.ConnectionEvent;
import com.app.boink.prototype.ConnectionListener;
import com.app.boink.util.EventListenerList;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by goof_troop on 9/12/13.
 */
public final class CommunicationManager {

    private static EventListenerList listenerList;
    private static final CommunicationManager self = new CommunicationManager();

    private CommunicationManager() {
        listenerList = new EventListenerList();
    }


    public synchronized static void connect(Object source, ChannelHandlerContext ctx) {

        if (ctx != null)
            fireCommunicationEvent(source, ctx);
    }

    public synchronized static void addEventListener(ChannelHandlerContext ctx, ConnectionListener listener) {
        listenerList.add(ctx, listener);
    }

    public synchronized static void removeEventListener(ChannelHandlerContext ctx, ConnectionListener listener) {
        listenerList.remove(ctx, listener);
    }

    private static synchronized void fireCommunicationEvent(Object source, ChannelHandlerContext ctx) {

        Object[] listeners = listenerList.getListenerList();

        for (int i = 0; i < listeners.length; i = i + 2)
            if (listeners[i] == ChannelHandlerContext.class)
                ((ConnectionListener) listeners[i + 1]).connectionEstablished(new ConnectionEvent(self, ctx));

    }
}