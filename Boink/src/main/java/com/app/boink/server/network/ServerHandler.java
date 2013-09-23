package com.app.boink.server.network;

import com.app.boink.model.packet.AccountPacket;
import com.app.boink.model.packet.ProfileUpdatePacket;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles a server-side channel.
 */
public class ServerHandler extends SimpleChannelInboundHandler<Object> {

    public ServerHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {

        // check the object coming in

        if (o instanceof ProfileUpdatePacket) {

        } else if (o instanceof AccountPacket) {

        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Logger.getLogger(ServerHandler.class.getName()).log(Level.WARNING, "Unexpected exception from downstream.", cause);
        ctx.close();
    }
}
