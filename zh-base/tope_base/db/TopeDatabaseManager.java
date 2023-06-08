package com.tope.tope_base.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tope.tope_base.analysis.TopeAnalysisConstant;

public class TopeDatabaseManager {
    public static final String DB_NAME = "Tope_Analysis4.db";
    private TopeAppDatabase topeDatabase;
    private static TopeDatabaseManager topeDatabaseManager;
    private Context context;

    private TopeDatabaseManager(Context context) {
        this.context = context;
        topeDatabase = Room.databaseBuilder(context, TopeAppDatabase.class, DB_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

    public TopeAppDatabase getTopeDatabase() {
        return topeDatabase;
    }

    public static synchronized TopeDatabaseManager getInstance(Context context) {
        if (topeDatabaseManager == null) {
            topeDatabaseManager = new TopeDatabaseManager(context);
        }
        return topeDatabaseManager;
    }
}
