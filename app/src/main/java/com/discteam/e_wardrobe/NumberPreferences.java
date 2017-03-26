package com.discteam.e_wardrobe;

import android.content.Context;
import android.preference.PreferenceManager;

public class NumberPreferences {

    private static final String PREF_NUMBER = "prefNumber";
    private static final String PREF_LOGIN = "prefLogin";
    private static final String PREF_PASSWORD = "prefPassword";
    private static final String PREF_IS_ALARM_ON = "isAlarmOn";

    public static Integer getNumber(Context context) {
        int number = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_NUMBER, 0);
        return number == 0 ? null : number;
    }

    public static void setNumber(Context context, Integer number) {
        if (number == null) {
            number = 0;
        }
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

    public static String getLogin(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LOGIN, null);
    }

    public static void setLogin(Context context, String login) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(PREF_LOGIN, login).apply();
    }

    public static String getPassword(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_PASSWORD, null);
    }

    public static void setPassword(Context context, String password) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(PREF_PASSWORD, password).apply();
    }
}
