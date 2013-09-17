package com.app.boink.server.network;

import com.app.boink.model.data.BoinkObject;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by goof_troop on 9/12/13.
 */
public class InternetAdapter extends Connection {

    // Assume that the connecting client is malicious. Validate all input with extreme predjudice

    private static final int port = 8443;

    private EventLoopGroup bossGroup, workerGroup;
    private ServerBootstrap b;

    public InternetAdapter() {

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
            // lgger
        }
    }

    @Override
    public boolean listen() {
        return false;
    }

    public boolean listen(int port) {
        return false;
    }

    @Override
    public boolean write(BoinkObject data) {
        return false;
    }

    @Override
    public Object read() {
        return null;
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public int getIntAddr() {
        return 0;
    }

    @Override
    public boolean isAlive() {
        return false;
    }
}
