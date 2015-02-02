package com.ztc1997.kernelhacker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.ztc1997.kernelhacker.extra.PrefKeys;

/**
 * Created by Alex on 2015/2/2.
 */
public class ShutdownReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(PrefKeys.BOOTED, false).apply();
        }
    }
}
