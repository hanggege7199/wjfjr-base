package com.tope.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tope.tope_base.R;

import org.w3c.dom.Text;

/**
 * 答题失败
 */
public class AnswerFailedDialog extends Dialog implements View.OnClickListener {

    Context context;
    private TextView mFailedText, mFailedZdl;
    private String errorNumber;
    private boolean cancelBtnIsVisible = true;

    public AnswerFailedDialog(@NonNull Context context, String errorNumber) {
        super(context);
        this.context = context;
        this.errorNumber = errorNumber;
    }

    public AnswerFailedDialog setCancelBtnInVisible() {
        cancelBtnIsVisible = false;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_answer_failed);
        init();
    }

    private void init() {
        mFailedText = findViewById(R.id.failed_text);
        mFailedZdl = findViewById(R.id.failed_zdl);
        if (!TextUtils.isEmpty(errorNumber)) {
            mFailedText.setText("您答错了" + errorNumber + "道题");
        }
        if (!cancelBtnIsVisible) {
            mFailedZdl.setVisibility(View.GONE);
        }
        mFailedZdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

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