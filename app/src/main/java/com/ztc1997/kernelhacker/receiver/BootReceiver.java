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

/**
 * Created by Alex on 2015/1/10.
 */
public class BootReceiver extends BroadcastReceiver {
    private Context context;
    @Override
    public void onReceive(final Context context, final Intent intent) {
        this.context = context;
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (preferences.getBoolean(PrefKeys.T2W_AUTO, false))
                context.startService(new Intent(context, AntiFalseWakeService.class));
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    String i = (preferences.getBoolean(PrefKeys.T2W, false) ? 1 : 0) + "";
                    if (MyApplication.getRootUtil() == null) {
                        showNotifition(R.string.notifition_boot_setting_failed_title, R.string.notifition_boot_setting_failed_text);
                        return;
                    }
                    Utils.writeFileWithRoot(Paths.T2W_PREVENT_SLEEP, i);
                    Utils.writeFileWithRoot(Paths.T2W_ENABLE, i);
                    Utils.writeFileWithRoot(Paths.T2W_INTERVAL, preferences.getString(PrefKeys.T2W_INTERAL, "20"));
                    Utils.writeFileWithRoot(Paths.T2W_X_FROM, preferences.getString(PrefKeys.T2W_RANGE_X_FROM, "0"));
                    Utils.writeFileWithRoot(Paths.T2W_Y_FROM, preferences.getString(PrefKeys.T2W_RANGE_Y_FROM, "0"));
                    Utils.writeFileWithRoot(Paths.T2W_X_TO, preferences.getString(PrefKeys.T2W_RANGE_X_TO, "719"));
                    Utils.writeFileWithRoot(Paths.T2W_Y_TO, preferences.getString(PrefKeys.T2W_RANGE_Y_TO, "1327"));
                    MyApplication.getRootUtil().execute(preferences.getBoolean(PrefKeys.ZRAM, false) ?
                            Commands.ENABLE_ZRAM : Commands.DISABLE_ZRAM, null);
                }
            }.start();
        }
    }
    
    private void showNotifition(int titleRes, int contentRes){
        Resources res = context.getResources();
        PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(res.getString(titleRes))
                .setContentText(res.getString(R.string.app_name))
                .setContentIntent(pi)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false)
                .setTicker(res.getString(titleRes))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher));

        Notification n = builder.getNotification();
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, n);
    }
}