package com.app.boink.server.network;

import com.app.boink.exception.PickleException;
import com.app.boink.packet.AccountPacket;
import com.app.boink.packet.AuthPacket;
import com.app.boink.packet.BoinkPacket;
import com.app.boink.packet.ProfileUpdatePacket;
import com.app.boink.packet.RegisterPacket;
import com.app.boink.prototype.ConnectionEvent;
import com.app.boink.prototype.ConnectionListener;
import com.app.boink.prototype.Session;
import com.app.boink.server.controller.Pickler;

/**
 * Created by goof_troop on 9/29/13.
 */
public class ConnectionHandler implements ConnectionListener {

    @Override
    public void connectionEstablished(ConnectionEvent evt) {

        BoinkPacket packet;
        Session session = null;

        if ((packet = (BoinkPacket) evt.getSource()) == null) {
            //logger

        }

        try {

            // try hash first, then depickle
            session = Pickler.dePickle(packet.getSessonId());

        } catch (PickleException e) {

        } catch (NullPointerException e) {

        } catch (Exception e) {

        }

        if (session == null) {
            // logger
            evt.channel().write(packet);
        }

        if (packet instanceof ProfileUpdatePacket) {

            ((ProfileUpdatePacket) packet).success(AuthenticatorService.updateUser(((ProfileUpdatePacket) packet).getInfo(), session));

        } else if (packet instanceof AccountPacket) {

            // TODO implement

        } else if (packet instanceof RegisterPacket) {

            ((RegisterPacket) packet).success(AuthenticatorService.authenticate(((RegisterPacket) packet).getUserName(), ((RegisterPacket) packet).getPassword(), session));


        } else if (packet instanceof AuthPacket) {

            ((AuthPacket) packet).success(AuthenticatorService.authenticate(((AuthPacket) packet).getUserName(), ((AuthPacket) packet).getPassword(), session));

        } else {
            // error out
            return;
        }

        evt.channel().write(packet);
    }
}
