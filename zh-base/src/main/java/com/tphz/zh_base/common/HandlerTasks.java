package com.tphz.zh_base.common;

import android.os.Handler;

public class HandlerTasks {

    private static HandlerTasks instance;
    private Handler mHandler = new Handler();

    public static HandlerTasks getInstance() {
        return instance;
    }

    public static void initInstance() {
        instance = new HandlerTasks();
    }

    public void runOnUiThread(Runnable runnable) {
        mHandler.post(runnable);
    }

    public boolean postDelayed(Runnable r, long delayMillis) {
        return mHandler.postDelayed(r, delayMillis);
    }

    public Handler getHandler() {
        return mHandler;
    }


}
