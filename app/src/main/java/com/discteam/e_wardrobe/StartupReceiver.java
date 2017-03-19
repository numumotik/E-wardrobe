package com.discteam.e_wardrobe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartupReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isOn = NumberPreferences.isAlarmOn(context);
        NotificationService.setServiceAlarm(context, isOn);
    }
}