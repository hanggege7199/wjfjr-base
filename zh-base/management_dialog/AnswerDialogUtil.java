package com.tope.management_dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.tope.bean.BrowseHistoryEntity;
import com.tope.common.ToastUtil;
import com.tope.http_lib.NetWorkUtils;
import com.tope.widget.AnswerFailedDialog;
import com.tope.widget.AnswerQuestionDialog;
import com.tope.widget.DailyRewardsDialog;

import java.util.List;

public class AnswerDialogUtil {

    //DailyRewardsDialog dialog = DialogUtil.dailyRewardsDialog(DailyTasksActivity.this, "科技馆",  "23");

    public static DailyRewardsDialog dailyRewardsDialog(Context context, String contentStr, String num) {
        if (!NetWorkUtils.isWifiAvailable(context)) {
            ToastUtil.toastShort("网络异常，请检查网络");
        } else {
            DailyRewardsDialog downloadDialog = new DailyRewardsDialog(context, contentStr, num);
            downloadDialog.dismiss();
            downloadDialog.show();
            return downloadDialog;

        }
        return null;
    }

    public static AnswerFailedDialog answerFailedDialog(Context context, String content) {
        if (!NetWorkUtils.isWifiAvailable(context)) {
            ToastUtil.toastShort("网络异常，请检查网络");
        } else {
            AnswerFailedDialog downloadDialog = new AnswerFailedDialog(context, content);
            downloadDialog.dismiss();
            downloadDialog.show();
            return downloadDialog;

        }
        return null;
    }

    public static AnswerQuestionDialog answerQuestionDialog(Context context, List<BrowseHistoryEntity.TaskProblemListDTO> problemList) {
        if (!NetWorkUtils.isWifiAvailable(context)) {
            ToastUtil.toastShort("网络异常，请检查网络");
        } else {
            AnswerQuestionDialog downloadDialog = new AnswerQuestionDialog(context, problemList);
            downloadDialog.dismiss();
            downloadDialog.show();
            return downloadDialog;

        }
        return null;
    }


    public static void dismiss(AlertDialog dialog, Context context) {
        if (dialog != null && dialog.isShowing()) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                    dialog.dismiss();
                }
            }
        }
    }


}
