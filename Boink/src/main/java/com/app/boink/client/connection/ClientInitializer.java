package com.app.boink.client.connection;

import com.app.boink.server.security.SslContextFactory;

import javax.net.ssl.SSLEngine;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslHandler;

/**
 * Creates a newly configured {@link ChannelPipeline} for a new channel.
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        // Add SSL handler first to encrypt and decrypt everything
        SSLEngine engine = SslContextFactory.getContext().createSSLEngine();
        engine.setUseClientMode(true);

        pipeline.addLast("ssl", new SslHandler(engine));

        // On top of the SSL handler, add the object codec.
        pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
        pipeline.addLast("encoder", new ObjectEncoder());

        // and then business logic.
        pipeline.addLast("handler", new ClientHandler());
    }
}
