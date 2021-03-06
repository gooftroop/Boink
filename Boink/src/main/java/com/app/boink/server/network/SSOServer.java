package com.app.boink.server.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by goof_troop on 9/22/13.
 */
public class SSOServer {

    /*
    This server accepts connections from authentication or registration

    1. The client can only call this from the Logon activity. This is first called to obtain
        a password for the SSLEngine
    2. Is the fact that its only callable from the activity secure enough? Or do we need more authentication
        - We'll check for session for sure
     */

    private static final int port = 8445;

    private EventLoopGroup bossGroup, workerGroup;
    private ServerBootstrap b;

    public SSOServer() {

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        try {

            b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SSOServerInitializer())
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
