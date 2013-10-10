package com.app.boink.client.connection;

import com.app.boink.exception.BoinkException;
import com.app.boink.prototype.BoinkObject;

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

    private static boolean SSOenabled;

    public RemoteAdapter() {

        SSOenabled = true;
        ch = null;

        group = new NioEventLoopGroup();

        b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer());
    }

    // we only need to read here and not write back to the server. that's a seperate task.

    /*
     *
     */
    public void connect(int port, String url, String user, String password) throws BoinkException {

        // We should not be connecting unless a SSO has occurred.
        if (SSOenabled) {
            // logger
            throw new BoinkException("");
        }

        try {
            ch = b.connect(url, port).sync().channel();
        } catch (InterruptedException e) {
            // logger
            ch = null;
            throw new BoinkException(e.getMessage());
        }
    }

    public void close() throws BoinkException {

        try {
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            // logger
            throw new BoinkException(e.getMessage());
        }

        group.shutdownGracefully();
    }

    /*
     *
     */
    @Override
    public void write(BoinkObject data) throws BoinkException {

        if (data == null) {
            // logger
            throw new BoinkException("");
        }

        if (ch == null) {
            // logger
            throw new BoinkException("");
        }

        ch.write(data);
    }

    public void write(int port, String url, String msg) throws BoinkException {

        if (!SSOenabled) {
            // logger
            throw new BoinkException("");
        }


        // write for SSO. This is obfuscated for security
        // encrypt? right now server and client have different encryption slat and password...we can't
        // encrypt using the existing AES since we're not garaunteed that its readable. So how do we deal with this?

        try {
            ch = b.connect(url, port).sync().channel();
        } catch (InterruptedException e) {
            // logger
            throw new BoinkException(e.getMessage());
        }

        ch.write(msg);
        SSOenabled = false;
        ch = null;

    }
}
