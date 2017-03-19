package com.discteam.e_wardrobe;

import android.content.Context;
import android.preference.PreferenceManager;

public class NumberPreferences {

    private static final String PREF_NUMBER = "prefNumber";
    private static final String PREF_IS_ALARM_ON = "isAlarmOn";

    public static int getNumber(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_NUMBER, 0);
    }

    public static void setNumber(Context context, int number) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putInt(PREF_NUMBER, number).apply();
    }

    public static boolean isAlarmOn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_IS_ALARM_ON, false);
    }

    public static void setAlarmOn(Context context, boolean isOn) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(PREF_IS_ALARM_ON, isOn).apply();
    }
}
