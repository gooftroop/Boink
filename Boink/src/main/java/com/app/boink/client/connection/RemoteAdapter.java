package com.app.boink.client.connection;

import com.app.boink.model.data.BoinkObject;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by goof_troop on 9/12/13.
 */
public class RemoteAdapter extends Connection {

    private Bootstrap b;
    private Channel ch;
    private EventLoopGroup group;

    public RemoteAdapter() {

        group = new NioEventLoopGroup();

        b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer());
    }

    /*
     *
     */
    @Override
    public boolean connect(String user, String password) {

        // use user/password in socket connection, default url and port
        return false;
    }

    /*
     *
     */
    public boolean connect(int port, String url, String user, String password) {

        try {
            ch = b.connect(url, port).sync().channel();
        } catch (InterruptedException e) {
            // logger
            return false;
        }

        if (b == null)
            return false;

        return true;
    }

    public void close() {

        try {
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            // logger
        }

        group.shutdownGracefully();
    }

    /*
     *
     */
    @Override
    public boolean write(BoinkObject data) {

        return false;
    }

    public boolean write(String msg) {

        // write for SSO. This is obfuscated for security
        // encrypt? right now server and client have different encryption slat and password...we can't
        // encrypt using the existing AES since we're not garaunteed that its readable. So how do we deal with this?

        return false;
    }

    /*
     *
     */
    @Override
    public Object read() {

        // read. If its a String, we've got SSO going. it should be encrypted
        // If its a BoinkObject, its normal operations

        return null;
    }

    /*
     *
     */
    @Override
    public int getPort() {
        return 0;
    }

    /*
     *
     */
    @Override
    public int getIntAddr() {
        return 0;
    }

    /*
     *
     */
    @Override
    public boolean isAlive() {
        return false;
    }
}
