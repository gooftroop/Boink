package com.app.boink.prototype;

import java.util.EventObject;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by goof_troop on 9/28/13.
 */
public class ConnectionEvent extends EventObject {

    private final ChannelHandlerContext ctx;

    public ConnectionEvent(Object source, ChannelHandlerContext ctx) {
        super(source);

        this.ctx = ctx;
    }

    public ChannelHandlerContext channel() {
        return ctx;
    }
}
