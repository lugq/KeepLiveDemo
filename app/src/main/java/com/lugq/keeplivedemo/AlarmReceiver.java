package com.lugq.keeplivedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @Description
 * @Author Lu Guoqiang
 * @Time 2019/4/22 17:29
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        /*
        Intent i = new Intent(context, LongRunningService.class);
        context.startService(i);
        */
    }

}
