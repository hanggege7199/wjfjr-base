package com.tope.tope_base.db;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tope.tope_base.analysis.TopeAnalysisDao;
import com.tope.tope_base.analysis.TopeAnalysisMode;
import com.tope.tope_base.data.TopeDataDao;
import com.tope.tope_base.data.TopeDataMode;

@Database(entities = {TopeAnalysisMode.class, TopeDataMode.class}, version = 5, exportSchema = false)
public abstract class TopeAppDatabase extends RoomDatabase {
    public abstract TopeAnalysisDao topeAnalysisDao();

    public abstract TopeDataDao topeSettingDao();
}
