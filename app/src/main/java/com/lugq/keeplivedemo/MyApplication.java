package com.lugq.keeplivedemo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * @Description
 * @Author Lu Guoqiang
 * @Time 2019/4/22 17:51
 */
public class MyApplication extends Application {

    public static int stateCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycle();
    }

    public static boolean isBackGround() {
        if (stateCount == 0) {
            return true;
        } else {
            return false;
        }
    }

    private void registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                stateCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                stateCount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
