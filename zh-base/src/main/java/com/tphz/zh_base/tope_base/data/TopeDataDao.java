package com.tphz.zh_base.tope_base.data;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TopeDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TopeDataMode mode);

    @Query("SELECT * FROM TopeDataMode")
    Cursor getSetting();

    @Query("SELECT * FROM TopeDataMode WHERE `key` = :key")
    Cursor getSettingForKey(String key);
}
