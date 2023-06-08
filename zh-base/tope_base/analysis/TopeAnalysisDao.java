package com.tope.tope_base.analysis;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TopeAnalysisDao {
    @Insert
    void insert(TopeAnalysisMode mode);

    @Delete
    void delete(TopeAnalysisMode mode);

    @Query("SELECT * FROM topeanalysismode WHERE childId = :childId")
    List<TopeAnalysisMode> getAll(String childId);

    @Query("DELETE FROM topeanalysismode WHERE uuid IN (:list)")
    void deleteEventByUUID(List<String> list);
}
