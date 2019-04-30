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

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean flag=false;
        if (SFApplication.isBackGround() == 0 && !BuildConfig.BUILD_DEBUG) {
            //　用户点击退出　或者按home键时　不启动
            if((Boolean) SharedPreferenceUtils.get(AppConstants.SHARED_IS_EXIT_BY_PEOPLE,false)){
                MyLog.i("click　exit or home","app over");
                flag=true;
            }else {
                MyLog.i("service restart", "begin");
                AlarmManager mgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent it = new Intent(getApplicationContext(), ForegroundActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                it.putExtra("crash", true);
                PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, it, PendingIntent.FLAG_ONE_SHOT);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                System.gc();
            }
        }

        if(!flag) {
            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            int anHour = 50; // 这是一小时的毫秒数
            long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
            Intent i = new Intent(this, AlarmReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
            return super.onStartCommand(intent, flags, startId);
        }else{
            return START_NOT_STICKY;
        }


        return super.onStartCommand(intent, flags, startId);

    }
}
