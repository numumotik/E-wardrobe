package com.discteam.e_wardrobe;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.SystemClock;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class NotificationService extends IntentService {

    private static final String TAG = "NotificationService";
    private static final int NOTIFICATION_INTERVAL = 1000 * 60;
    private static final long NOTIFICATION_TIME = SystemClock.elapsedRealtime() + 5000;

    public NotificationService() {
        super(TAG);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, NotificationService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "notification initialization");
        if (NumberPreferences.getNumber(getApplicationContext()) != 0) {
            Resources resources = getResources();
            Intent i = WardrobeActivity.newIntent(this);
            PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
            Notification notification = new NotificationCompat.Builder(this)
                    .setTicker(resources.getString(R.string.closet_close_title))
                    .setSmallIcon(R.drawable.ic_closet_close)
                    .setContentTitle(resources.getString(R.string.closet_close_title))
                    .setContentText(resources.getString(R.string.closet_close_text))
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, notification);
        }
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = NotificationService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setRepeating(AlarmManager.RTC, NOTIFICATION_TIME, NOTIFICATION_INTERVAL, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
        NumberPreferences.setAlarmOn(context, isOn);
    }

    public static boolean isServiceAlarmOn(Context context) {
        Intent i = NotificationService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }
}
