package com.ztc1997.kernelhacker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;

import com.ztc1997.kernelhacker.extra.PrefKeys;
import com.ztc1997.kernelhacker.extra.Paths;
import com.ztc1997.kernelhacker.extra.Utils;
import com.ztc1997.kernelhacker.ui.MainActivity;

public class AntiFalseWakeService extends Service {

    private SensorManager
            mManager;

    private Sensor
            mSensor = null;

    private SensorEventListener
            mListener = null;

    private SharedPreferences preferences;
    private PowerManager pm;


    @Override
    public void onCreate() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(changeListener);
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        new Thread(){
            @Override
            public void run() {
                super.run();
                if (MyApplication.getRootUtil() == null){
                    showNotifition();
                }
            }
        }.start();

//获取系统服务SENSOR_SERVICE，返回一个SensorManager对象
        mManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//获取距离感应器对象
        mSensor = mManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
//注册感应器事件
        mListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent
                                                event) {

                final float[]
                        its = event.values;

                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY && !pm.isScreenOn()) {

                    System.out.println("its[0]:" +
                            its[0]);

//经过测试，当手贴近距离感应器的时候its[0]返回值为0.0，当手离开时返回1.0
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            if (its[0]
                                    == 0.0) {//贴近手机
                                System.out.println("t2w disabled...");
                                if (MyApplication.getRootUtil() == null)
                                    showNotifition();
                                Utils.writeFileWithRoot(Paths.T2W_ENABLE, "0");
                            } else {//远离手机
                                System.out.println("t2w enabled...");
                                if (MyApplication.getRootUtil() == null)
                                    showNotifition();
                                Utils.writeFileWithRoot(Paths.T2W_ENABLE, "1");
                            }
                        }
                    }.start();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//注册监听
        mManager.registerListener(mListener,
                mSensor,
                SensorManager.SENSOR_DELAY_GAME);

        return super.onStartCommand(intent,
                flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        
        preferences.unregisterOnSharedPreferenceChangeListener(changeListener);
        //取消监听
        mManager.unregisterListener(mListener);
        
        if (preferences.getBoolean(PrefKeys.T2W_AUTO, false) && preferences.getBoolean(PrefKeys.T2W, false))
            startService(new Intent(this, AntiFalseWakeService.class));
    }

    @Override
    public IBinder
    onBind(Intent intent) {
        return null;
    }

    private SharedPreferences.OnSharedPreferenceChangeListener changeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences preferences, String s) {
            if (!(preferences.getBoolean(PrefKeys.T2W_AUTO, false) || preferences.getBoolean(PrefKeys.T2W, false)))
                stopSelf();
        }
    };

    private void showNotifition(){
        Resources res = this.getResources();
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(res.getString(R.string.toast_getting_root_failed))
                .setContentText(res.getString(R.string.app_name))
                .setContentIntent(pi)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false)
                .setTicker(res.getString(R.string.toast_getting_root_failed))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher));

        Notification n = builder.getNotification();
        NotificationManager nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, n);
    }
}