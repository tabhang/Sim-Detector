package com.example.simdetector;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.support.annotation.Nullable;


import java.util.Timer;
import java.util.TimerTask;

import static com.example.simdetector.notif.CHANNEL_ID;

public class b_service extends Service {

    private MediaPlayer player;
    private TelephonyManager telMgr;
    private Handler mTimerHandler = new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText("tejas")
                .setSmallIcon(R.drawable.ic_sim)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true);
        player.start();
        Timer mTimer1 = new Timer();
        TimerTask mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run(){
                        telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                        if(telMgr.getSimState() == TelephonyManager.SIM_STATE_ABSENT){
                            player.start();
                        }
                        else
                        {
                            player.pause();
                        }
                        }
                    });
                }
            };

            mTimer1.schedule(mTt1, 1, 5000);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}
