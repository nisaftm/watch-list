package com.nemesis.watchlist.data.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class DataPrefs {

    public static String DAILY_CHECK = "dailyCheck";
    public static String RELEASE_CHECK = "releaseCheck";
    private final Context context;
    private final String PREFS_CHECKED = "PrefsChecked";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public DataPrefs(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_CHECKED, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }


    public boolean getboolean(String key, boolean defValue) {
        return prefs.getBoolean(key, defValue);
    }

    public void editboolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }
}
