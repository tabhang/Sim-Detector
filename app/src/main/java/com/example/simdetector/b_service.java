package com.example.simdetector;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.support.annotation.Nullable;


import java.util.Timer;
import java.util.TimerTask;

import static com.example.simdetector.notif.CHANNEL_ID;

public class b_service extends Service {

    private Uri player;
    private Ringtone r;
    private TelephonyManager telMgr;
    private Handler mTimerHandler = new Handler();
    private Timer mTimer1;
    private TimerTask mTt1;

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
                .setContentTitle("App running successfully")
                .setSmallIcon(R.drawable.ic_sim)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);


        player = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        r = RingtoneManager.getRingtone(getApplicationContext(), player);
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run(){
                        telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                        if(telMgr.getSimState() == TelephonyManager.SIM_STATE_ABSENT){
                            r.play();
                        }
                        else
                        {
                            r.stop();
                        }
                        }
                    });
                }
            };

            mTimer1.schedule(mTt1, 1, 5000);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        r.stop();
        mTimer1.cancel();
        mTimer1.purge();
    }
}
