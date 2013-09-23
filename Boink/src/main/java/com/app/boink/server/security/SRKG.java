package com.app.boink.server.security;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Observable;

/**
 * Created by goof_troop on 9/13/13.
 */
public class SRKG extends Observable {

    private final long PERIOD = 86400000;

    private static char[] password;
    private static byte[] salt;
    private Context mContext;

    public SRKG(Context mContext) {

        this.mContext = mContext;

        password = new char[64];
        salt = new byte[64];

        clean();

        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, Rotate.class);
        PendingIntent pending = PendingIntent.getBroadcast(mContext, 3333, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), PERIOD, pending);

    }

    public static char[] getPassword() {
        return password;
    }

    public static byte[] getSalt() {
        return salt;
    }

    public void clean() {

        for (int i = 0; i < password.length; i++)
            password[i] = 'F';

        for (int i = 0; i < salt.length; i++)
            salt[i] = 0;
    }

    protected void update() {

        this.setChanged();
        this.notifyObservers();
    }

    private class Rotate extends BroadcastReceiver {

        public Rotate() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            try {

                SecureRandom sec = SecureRandom.getInstance("SHA1PRNG", "SUN");
                sec.nextBytes(new byte[64]);
                salt = sec.generateSeed(64);

            } catch (NoSuchAlgorithmException e) {

            } catch (NoSuchProviderException e) {

            }

            try {

                SecureRandom sec = SecureRandom.getInstance("SHA1PRNG", "SUN");
                sec.nextBytes(new byte[64]);
                byte[] tmp = sec.generateSeed(64);
                password = new String(tmp, "ASCII").toCharArray();

            } catch (NoSuchAlgorithmException e) {

            } catch (NoSuchProviderException e) {

            } catch (UnsupportedEncodingException e) {

            }

            update();
        }
    }
}
