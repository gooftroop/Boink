package com.app.boink.server.network;

import com.app.boink.server.controller.ConnectionManager;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by goof_troop on 9/20/13.
 */
public class SessionContext extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {

        /*
        This will need to grab the session id from the connection/object (tbd on implementation)
        and check if a) the session id is valid or the session is valid and b) if the session needs
        to be loaded.

        Once the ctx is allowed through, the serverhandler will pull in the object, and trigger a
        async session event with the session id and the object containing the action to be performed.
        The channel will then wait for write notification once the object has been processed
         */

        ConnectionManager.addEventListener(ctx, new ConnectionHandler());
    }
}
