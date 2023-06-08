package com.tope.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.tope.bean.BrowseHistoryEntity;
import com.tope.tope_base.R;


public class CheckVerticalView extends LinearLayout {
    Context mContext;
    BrowseHistoryEntity.TaskProblemListDTO.ListDTO.OptionListDTO dataVo;

    public CheckVerticalView(Context context, BrowseHistoryEntity.TaskProblemListDTO.ListDTO.OptionListDTO optionListDTO) {
        super(context);
        this.mContext = context;
        this.dataVo = optionListDTO;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.check_vertical_item, this);
        setWillNotDraw(false);
        CheckBox checkBox = findViewById(R.id.textCheck);
        checkBox.setText(dataVo.getOptionContent());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataVo.setSelectStatus(true);
                    checkBox.setTextColor(Color.parseColor("#F3B442"));
                } else {
                    dataVo.setSelectStatus(false);
                    checkBox.setTextColor(Color.parseColor("#63742F"));
                }

            }
        });

    }
}
