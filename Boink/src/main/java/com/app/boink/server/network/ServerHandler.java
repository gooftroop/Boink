package com.app.boink.server.network;

import com.app.boink.packet.BoinkPacket;
import com.app.boink.server.controller.CommunicationManager;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles a server-side channel.
 */
public final class ServerHandler extends SimpleChannelInboundHandler<BoinkPacket> {

    public ServerHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BoinkPacket o) throws Exception {

        // fire off a new communication event.
        // the event should have the session id to work on and
        // the information to work with
        CommunicationManager.connect(o, ctx);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        CommunicationManager.removeEventListener(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Logger.getLogger(ServerHandler.class.getName()).log(Level.WARNING, "Unexpected exception from downstream.", cause);
        ctx.close();
    }
}
