package com.tope.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tope.adapter.QuestionAdapter;
import com.tope.bean.BrowseHistoryEntity;
import com.tope.listener.OnAnswerClickListener;
import com.tope.tope_base.R;

import java.util.List;

public class AnswerQuestionDialog extends Dialog implements QuestionAdapter.OnSubmitClickListener {

    Context mContext;
    List<BrowseHistoryEntity.TaskProblemListDTO> answersEntity;
    OnAnswerClickListener onAnswerClickListener;

    public AnswerQuestionDialog(@NonNull Context context, List<BrowseHistoryEntity.TaskProblemListDTO> answersEntity) {
        super(context);
        this.mContext = context;
        this.answersEntity = answersEntity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_answer_question);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        QuestionAdapter adapter = new QuestionAdapter(answersEntity, getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnSubmitClickListener(this);
        adapter.notifyDataSetChanged();
    }

    public void setOnAnswerClickListener(OnAnswerClickListener pOnItemClickListener) {
        this.onAnswerClickListener = pOnItemClickListener;
    }

    @Override
    public void onClickListener(BrowseHistoryEntity.TaskProblemListDTO listDto) {
        int numberAnswers = 0;
        String answersTaskId = "";
        answersTaskId = listDto.getTaskId();
        List<BrowseHistoryEntity.TaskProblemListDTO.ListDTO> data = listDto.getProblemList();
        for (int i = 0; i < data.size(); i++) {
            List<BrowseHistoryEntity.TaskProblemListDTO.ListDTO.OptionListDTO> data1 = data.get(i).getOptionList();
            boolean allAnswer = true;
            for (BrowseHistoryEntity.TaskProblemListDTO.ListDTO.OptionListDTO dataItem : data1) {
                boolean isSelectStatus = dataItem.isSelectStatus();
                int correctOption = dataItem.getCorrectOption();
                if (correctOption == 1 && !isSelectStatus) {
                    allAnswer = false;
                }
                if (correctOption == 2 && isSelectStatus) {
                    allAnswer = false;
                }
            }
            if (!allAnswer) {
                numberAnswers++;
            }
        }
        dismiss();
        if (onAnswerClickListener != null)
            onAnswerClickListener.click(answersTaskId, numberAnswers);
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
        }

    }
}
