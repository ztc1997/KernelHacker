package com.ztc1997.kernelhacker;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ztc1997.kernelhacker.extra.PrefKeys;
import com.ztc1997.kernelhacker.extra.RootUtil;
import com.ztc1997.kernelhacker.ui.ExceptionActivity;

/**
 * Created by Alex on 2015/1/10.
 */
public class MyApplication extends Application {

    private static RootUtil mRootUtil = new RootUtil();
    private SharedPreferences preferences;
    private static MyApplication application;

    public static RootUtil getRootUtil() {
        if (!mRootUtil.isStarted() && !mRootUtil.startShell())
            return null;
        return mRootUtil;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean(PrefKeys.T2W_AUTO, false))
            startService(new Intent(this, AntiFalseWakeService.class));
    }

    public static MyApplication getInstance() {
        return application;
    }
    
    private Thread.UncaughtExceptionHandler exceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            Intent intent = new Intent(MyApplication.this, ExceptionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("exception", ex.toString());
            startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    };
}