package com.app.boink.server.network;

import com.app.boink.packet.BoinkPacket;
import com.app.boink.server.controller.CommunicationManager;
import com.app.boink.server.controller.DeviceManager;
import com.app.boink.server.controller.SessionManager;

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

        // decode this ( make sure this is encoded when set ). Should be final also.
        String sessionId = ((BoinkPacket) o).getSessonId();
        byte[] deviceId = ((BoinkPacket) o).getDeviceId();
        Session s = null;

        if (!SessionManager.containsSession(sessionId)) {
            // log
            // redirect? User must refresh login
        }

        s = SessionManager.getSession(sessionId);

        if (s == null || !s.isValid()) {
            // log
            // redirect? User must refresh login
        }

        // check DeviceManager to ensure that the device ID are correct. The deviceId is unique to each client and
        // shoud be nearly impossible to a) decode and b) recreate
        if (!DeviceManager.isActive(deviceId) || deviceId != s.getDeviceId()) {
            // log
            // redirect? Unauthorized device
        }

        CommunicationManager.addEventListener(ctx, new ConnectionHandler());
    }
}
