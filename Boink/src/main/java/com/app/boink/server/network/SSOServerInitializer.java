package com.app.boink.server.network;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Creates a newly configured {@link io.netty.channel.ChannelPipeline} for a new channel.
 */
public class SSOServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        // On top of the SSL handler, add the object codec.
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        // add the session context filter
        pipeline.addLast("session", new SessionContext());

        // and then business logic.
        pipeline.addLast("handler", new SSOServerHandler());
    }
}
