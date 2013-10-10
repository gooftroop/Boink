package com.app.boink.client.connection;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles a client-side channel.
 */
public class ClientHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {

        // fire off a new communication event.
        // the event should have the session id to work on and
        // the information to work with
        CommunicationManager.connect(o, ctx);
    }

    public void channelUnregistered(ChannelHandlerContext ctx) {
        com.app.boink.server.controller.CommunicationManager.removeEventListener(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Logger.getLogger(ClientHandler.class.getName()).log(Level.WARNING, "Unexpected exception from downstream.", cause);
        ctx.close();
    }
}
