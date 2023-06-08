package com.tope.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tope.tope_base.R;

import java.util.ArrayList;
import java.util.List;

public class DailyRewardsDialog extends Dialog implements View.OnClickListener {

    Context mContext;
    private LinearLayout mTasksLl;
    private TextView mPassedFinish, mTasksText, mTasksNum;
    private String title, douNumber;
    /* 取消按钮是否显示 */
    private boolean cancelBtnIsVisible = true;

    public DailyRewardsDialog(@NonNull Context context, String title, String douNumber) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.douNumber = douNumber;
    }

    public DailyRewardsDialog setCancelBtnInVisible() {
        cancelBtnIsVisible = false;
        return this;
    }

    //更新完成 网络传值
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_daily_tasks);
        init();
    }

    private void init() {
        mPassedFinish = findViewById(R.id.dialog_finish);
        mTasksLl = findViewById(R.id.tasks_ll);
        mTasksText = findViewById(R.id.tasks_text);
        mTasksNum = findViewById(R.id.tasks_num);
        mPassedFinish.setOnClickListener(this);
        if (!TextUtils.isEmpty(title)) mTasksText.setText("完成了 “" + title + "”");
        if (!TextUtils.isEmpty(douNumber)) {
            mTasksNum.setText(douNumber);
            mTasksLl.setVisibility(View.VISIBLE);
        }
        if (!cancelBtnIsVisible) {
            mTasksLl.setVisibility(View.GONE);
        }
    }

    @Override
    public void show() {
        super.show();

    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_finish) {
            dismiss();
        }
    }
}