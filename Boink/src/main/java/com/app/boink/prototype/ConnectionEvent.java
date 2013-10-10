package com.app.boink.prototype;

import com.app.boink.server.network.LocalServer;

import java.util.EventObject;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by goof_troop on 9/28/13.
 */
public class ConnectionEvent extends EventObject {

    private final Object ctx;

    public ConnectionEvent(Object source, Object ctx) {
        super(source);

        this.ctx = ctx;
    }

    public ChannelHandlerContext channel() {
        return (ChannelHandlerContext) ctx;
    }

    public LocalServer server() {
        return (LocalServer) ctx;
    }
}
