package com.tphz.zh_base.tope_base.analysis;

import com.google.gson.Gson;

import com.tope.http_lib.DataList;
import com.tphz.zh_base.common.Constants;
import com.tphz.zh_base.common.LogUtil;
import com.tphz.zh_base.http_lib.ApiCallback;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TopeAnalysis {
    private static final AtomicLong lastEventTime = new AtomicLong(0);
    private static Gson gson = new Gson();

    public static synchronized void saveEvent(String eventId, Map map) {
        MobclickAgent.onEventObject(Constants.getContext(), eventId, map);
    }

    public static synchronized void saveEvent(String eventId, String info, String eventTwainId) {
        TopeAnalysisManager.getInstance(Constants.getContext()).saveEvent(eventId, info, eventTwainId);
        if (System.currentTimeMillis() - lastEventTime.get() >= 1000 * 60) {
//            LogUtil.e("TopeAnalysis", "--" + lastEventTime.get());
            lastEventTime.set(System.currentTimeMillis());
            sendAnalysis();
        }
//        lastEventTime.set(System.currentTimeMillis());
    }

    public static synchronized void sendAnalysis() {
        List<TopeAnalysisMode> list = TopeAnalysisManager.getInstance(Constants.getContext()).getAllEvent();
        if (list == null) return;
        if (list.size() == 0) return;
        MediaType mediaType = MediaType.parse("application/json");
        String json = gson.toJson(list);
//        LogUtil.e("sendAnalysis11", json);
        RequestBody body = RequestBody.create(mediaType, json);
        TopeAnalysisClient.getInstance().getApi().saveBuried(body).enqueue(new ApiCallback<DataList<String>>() {
            @Override
            public void onSuccess(DataList<String> data) {
                try {
                    LogUtil.d("sendAnalysis onSuccess", data.getData().toString());
                    TopeAnalysisManager.getInstance(Constants.getContext()).deleteEventByUUID(data.getData());
                } catch (Exception e) {
                }

            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }
}
