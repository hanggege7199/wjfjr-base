package com.tphz.zh_base.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.tphz.zh_base.R;


public class DialogUtil {


    private static void dismiss(AlertDialog dialog, Context context) {
        if (dialog != null && dialog.isShowing()) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                    dialog.dismiss();
                }
            }
        }
    }

    public static AlertDialog showToast(Context context, String title) {
        return showToast(context, title, null);
    }

    /**
     * 屏幕中间的toast
     *
     * @param context
     * @param title
     * @return
     */
    public static AlertDialog showToast(Context context, String title, ToastListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.dialog_toast, null);
        TextView textView = view.findViewById(R.id.text);
        textView.setText(title);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        dialog.getWindow().setContentView(view);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss(dialog, context);
                if (listener != null) {
                    listener.dismiss();
                }
            }
        }, 1000);
        return dialog;
    }

    public interface ToastListener {
        void dismiss();
    }


}
