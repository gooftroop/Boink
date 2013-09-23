package com.app.boink.server.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by goof_troop on 9/12/13.
 */

// Create a NIO Flex SSL Server for the client to connect to
// This will be a stand-alone, installable server running as an app

public class SecureServer {

    // Assume that the connecting client is malicious. Validate all input with extreme predjudice

    private static final int port = 8443;

    private EventLoopGroup bossGroup, workerGroup;
    private ServerBootstrap b;

    public SecureServer() {

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        try {

            b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .localAddress(port)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            b.bind().sync().channel().closeFuture().sync();

        } catch (InterruptedException e) {
            // logger
        }
    }

    public void close() {
        // It returns a Future that notifies you when the EventLoopGroup has been terminated completely and all Channels that belong to the group have been closed.
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();

        try {

            bossGroup.terminationFuture().sync();
            workerGroup.terminationFuture().sync();

        } catch (InterruptedException e) {
            // logger
        }
    }
}
