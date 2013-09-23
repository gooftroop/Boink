package com.app.boink.server.network;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles a server-side channel.
 */
public class SSOServerHandler extends SimpleChannelInboundHandler<String> {

    public SSOServerHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        // check the object coming in
        // it should be encrypted - I need to figure this out
        // and it needs to be returned encrypted

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Logger.getLogger(SSOServerHandler.class.getName()).log(Level.WARNING, "Unexpected exception from downstream.", cause);
        ctx.close();
    }
}
