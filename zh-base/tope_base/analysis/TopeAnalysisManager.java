package com.tope.tope_base.analysis;

import android.content.Context;

import com.tope.common.LogUtil;
import com.tope.tope_base.data.TopeDatas;
import com.tope.tope_base.db.TopeAppDatabase;
import com.tope.tope_base.db.TopeDatabaseManager;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

public class TopeAnalysisManager {
    private TopeAppDatabase topeDatabase;
    private static TopeAnalysisManager topeAnalysisManager;
    private Context context;

    private TopeAnalysisManager(Context context) {
        this.context = context;
        topeDatabase = TopeDatabaseManager.getInstance(context).getTopeDatabase();
    }

    public static synchronized TopeAnalysisManager getInstance(Context context) {
        if (topeAnalysisManager == null) {
            topeAnalysisManager = new TopeAnalysisManager(context);
        }
        return topeAnalysisManager;
    }

    public synchronized void saveEvent(String eventId, String info, String eventTwainId) {
        TopeAnalysisMode topeAnalysisMode = new TopeAnalysisMode();
        topeAnalysisMode.event = eventId;
        topeAnalysisMode.info = info;
        topeAnalysisMode.eventTwainId = eventTwainId;
        topeAnalysisMode.createTime = System.currentTimeMillis();
        topeAnalysisMode.childId = TopeDatas.getChildrenId();
        topeAnalysisMode.uuid = UUID.randomUUID().toString();
        topeDatabase.topeAnalysisDao().insert(topeAnalysisMode);
    }

    public synchronized List<TopeAnalysisMode> getAllEvent() {
        List<TopeAnalysisMode> list = topeDatabase.topeAnalysisDao().getAll(TopeDatas.getChildrenId());
        return list;
    }

    public synchronized void deleteEventByUUID(List<String> list) {
        topeDatabase.topeAnalysisDao().deleteEventByUUID(list);
    }
}
