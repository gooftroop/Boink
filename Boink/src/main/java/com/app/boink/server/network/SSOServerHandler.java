package com.app.boink.server.network;

import com.app.boink.exception.BoinkException;
import com.app.boink.packet.SSOPacket;
import com.app.boink.server.controller.DeviceManager;
import com.app.boink.server.controller.SessionManager;
import com.app.boink.server.security.Encryptor;
import com.app.boink.server.security.SslContextFactory;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles a server-side channel.
 */
public class SSOServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        // check the object coming in
        // constraint: the msg has to be the client app id and is permanent. this needs to be a "signature". encrypted??
        // return the session id and unique encryption key

        if (!(msg instanceof String)) {
            // logger
            ctx.write("Connection refused. Invalid communication attempt");
            return;
        }

        byte[] devid = new Encryptor().encrypt((String) msg);


        if (DeviceManager.isActive(devid)) {
            // logger
            ctx.write("Connection refused. Device already in use");
        } else
            try {
                DeviceManager.addDevice(devid);
            } catch (BoinkException e) {
                // logger
                ctx.write("Connection refused. Device already in use");
            }

        String sessionid = UUID.randomUUID().toString();
        SessionManager.setSession(sessionid, new Session(sessionid, devid));

        SSOPacket response = new SSOPacket(sessionid);
        response.setSecureRandom(SslContextFactory.getSecureRandom());
        ctx.write(response);

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
