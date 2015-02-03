package com.ztc1997.kernelhacker.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;

import com.ztc1997.kernelhacker.AntiFalseWakeService;
import com.ztc1997.kernelhacker.MyApplication;
import com.ztc1997.kernelhacker.extra.Commands;
import com.ztc1997.kernelhacker.extra.PrefKeys;
import com.ztc1997.kernelhacker.extra.Paths;
import com.ztc1997.kernelhacker.R;
import com.ztc1997.kernelhacker.extra.Utils;
import com.ztc1997.kernelhacker.ui.MainActivity;
import com.ztc1997.kernelhacker.ui.ReBootSetActivity;

/**
 * Created by Alex on 2015/1/10.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (preferences.getBoolean(PrefKeys.BOOTED, false)){
                showNotifition(context, R.string.notifition_boot_no_shutdown_title, R.string.notifition_boot_setting_failed_text);
                return;
            }
            preferences.edit().putBoolean(PrefKeys.BOOTED, true).apply();
            if (!preferences.getString(PrefKeys.KERNEL_VERSION, "").equals(Utils.readOneLine(Paths.INFO_KERNEL_VERSION))){
                showNotifition(context, R.string.notifition_boot_kernel_changed_title, R.string.notifition_boot_setting_failed_text);
                return;
            }
            bootSetup(context, preferences);
        }
    }
    
    private static void showNotifition(Context context, int titleRes, int contentRes){
        Resources res = context.getResources();
        Intent intent = new Intent(context, ReBootSetActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(res.getString(titleRes))
                .setContentText(res.getString(contentRes))
                .setContentIntent(pi)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setTicker(res.getString(titleRes))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher));

        Notification n = builder.getNotification();
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, n);
    }
    
    public static void bootSetup(final Context context, final SharedPreferences preferences){
        if (preferences.getBoolean(PrefKeys.T2W_AUTO, false))
            context.startService(new Intent(context, AntiFalseWakeService.class));
        new Thread(){
            @Override
            public void run() {
                super.run();
                String i = (preferences.getBoolean(PrefKeys.T2W, false) ? 1 : 0) + "";
                if (MyApplication.getRootUtil() == null) {
                    showNotifition(context, R.string.notifition_boot_setting_failed_title, R.string.notifition_boot_setting_failed_text);
                    return;
                }
                Utils.writeFileWithRoot(Paths.T2W_PREVENT_SLEEP, i);
                Utils.writeFileWithRoot(Paths.T2W_ENABLE, i);
                Utils.writeFileWithRoot(Paths.T2W_INTERVAL, preferences.getString(PrefKeys.T2W_INTERAL, "20"));
                Utils.writeFileWithRoot(Paths.T2W_X_FROM, preferences.getString(PrefKeys.T2W_RANGE_X_FROM, "0"));
                Utils.writeFileWithRoot(Paths.T2W_Y_FROM, preferences.getString(PrefKeys.T2W_RANGE_Y_FROM, "0"));
                Utils.writeFileWithRoot(Paths.T2W_X_TO, preferences.getString(PrefKeys.T2W_RANGE_X_TO, "719"));
                Utils.writeFileWithRoot(Paths.T2W_Y_TO, preferences.getString(PrefKeys.T2W_RANGE_Y_TO, "1327"));
                Utils.setFilePermission(Paths.SCALING_MAX_FREQ, "644");
                Utils.setFilePermission(Paths.SCALING_MIN_FREQ, "644");
                Utils.writeFileWithRoot(Paths.SCALING_MIN_FREQ, preferences.getString(PrefKeys.CPU_MIN_FREQ, "-1"));
                Utils.writeFileWithRoot(Paths.SCALING_MAX_FREQ, preferences.getString(PrefKeys.CPU_MAX_FREQ, "-1"));
                if (preferences.getBoolean(PrefKeys.CPU_LOCK_FREQ, false)){
                    Utils.setFilePermission(Paths.SCALING_MAX_FREQ, "444");
                    Utils.setFilePermission(Paths.SCALING_MIN_FREQ, "444");
                }
                Utils.writeFileWithRoot(Paths.SCALING_GOVERNOR, preferences.getString(PrefKeys.CPU_GOV, "-1"));
                Utils.writeFileWithRoot(Paths.ZRAM_DISKSIZE, (preferences.getInt(PrefKeys.ZRAM_DISKSIZE, 0) << 20) + "");
                MyApplication.getRootUtil().execute(preferences.getBoolean(PrefKeys.ZRAM, false) ?
                        Commands.ENABLE_ZRAM : Commands.DISABLE_ZRAM, null);
            }
        }.start();
    }
}