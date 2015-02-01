package com.ztc1997.kernelhacker;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ztc1997.kernelhacker.extra.PrefKeys;
import com.ztc1997.kernelhacker.extra.RootUtil;

/**
 * Created by Alex on 2015/1/10.
 */
public class MyApplication extends Application {

    private static RootUtil mRootUtil = new RootUtil();
    private SharedPreferences preferences;

    public static RootUtil getRootUtil() {
        if (!mRootUtil.isStarted() && !mRootUtil.startShell())
            return null;
        return mRootUtil;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean(PrefKeys.T2W_AUTO, false))
            startService(new Intent(this, AntiFalseWakeService.class));
    }
}
