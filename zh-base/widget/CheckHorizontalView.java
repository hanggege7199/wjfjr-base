package com.tope.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tope.bean.BrowseHistoryEntity;
import com.tope.tope_base.R;

public class CheckHorizontalView extends LinearLayout {
    Context mContext;
    BrowseHistoryEntity.TaskProblemListDTO.ListDTO.OptionListDTO dataVo;

    public CheckHorizontalView(Context context, BrowseHistoryEntity.TaskProblemListDTO.ListDTO.OptionListDTO optionListDTO) {
        super(context);
        this.mContext = context;
        this.dataVo = optionListDTO;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.check_horizontal_item, this);
        setWillNotDraw(false);
        CheckBox checkBox = findViewById(R.id.checkBox);
        ImageView img = findViewById(R.id.checkBoImg);
        TextView text = findViewById(R.id.checkBoxText);
        if (!TextUtils.isEmpty(dataVo.getOptionPicture())) {
            img.setVisibility(View.VISIBLE);
            Glide.with(this).load(dataVo.getOptionPicture()).into(img);
        }
        if (!TextUtils.isEmpty(dataVo.getOptionContent())) {
            text.setVisibility(View.VISIBLE);
            text.setText(dataVo.getOptionContent());
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataVo.setSelectStatus(true);
                } else {
                    dataVo.setSelectStatus(false);
                }

            }
        });

    }
}
