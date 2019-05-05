package com.lugq.keeplivedemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

/**
 * @Description
 * @Author Lu Guoqiang
 * @Time 2019/4/22 17:32
 */
public class LongRunningService extends Service {

    public static final String SHARED_IS_EXIT_BY_PEOPLE = "shared_is_exit_by_people";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (MyApplication.isBackGround() /*&& !BuildConfig.BUILD_DEBUG*/) {
            //　用户点击退出　或者按home键时　不启动
            // 非人为退出，进行自启动
            if (!(boolean)SPUtils.get(this, SHARED_IS_EXIT_BY_PEOPLE, false)) {
                restartApp();
            }
        }
        timedBroadcast();
        return START_NOT_STICKY;
    }

    // 1s后重启APP
    private void restartApp() {
        Context context = getApplicationContext();
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        int flags = PendingIntent.FLAG_ONE_SHOT;
        PendingIntent operation = PendingIntent.getActivity(context, 0, intent, flags);
        long triggerAtMillis = System.currentTimeMillis() + 1000;
        mgr.set(AlarmManager.RTC, triggerAtMillis, operation);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
        System.gc();
    }

    // 观察日志 每 5S 轮训
    private void timedBroadcast() {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 50;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
    }
}
