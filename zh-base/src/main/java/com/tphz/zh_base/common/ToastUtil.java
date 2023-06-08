package com.tphz.zh_base.common;

import android.widget.Toast;

public class ToastUtil {

    private static Toast mToast;

    public static final void toastLong(final String message) {
        HandlerTasks.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                mToast = Toast.makeText(Constants.getContext(), message,
                        Toast.LENGTH_LONG);
                mToast.show();
            }
        });
    }


    public static final void toastShort(final String message) {
        HandlerTasks.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                mToast = Toast.makeText(Constants.getContext(), message,
                        Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }


}