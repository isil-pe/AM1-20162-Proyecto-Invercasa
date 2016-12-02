package com.vrm.invercasa.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    private static final String FIRST_TIME = "FirstTime";
    private static final String LOGGED_USER = "LoggedUser";

    public PrefManager(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences("Preferences", 0);
        editor = pref.edit();
    }

    public void setLoggedUser(int logId) {
        editor.putInt(LOGGED_USER, logId);
        editor.commit();
    }

    public int getLoggedUser() {
        return pref.getInt(LOGGED_USER, -1);
    }

    public void setFirstTime(boolean isFirstTime) {
        editor.putBoolean(FIRST_TIME, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTime() {
        return pref.getBoolean(FIRST_TIME, true);
    }
}
