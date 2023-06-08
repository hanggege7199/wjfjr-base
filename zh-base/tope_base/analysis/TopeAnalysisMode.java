package com.tope.tope_base.analysis;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TopeAnalysisMode {
    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ColumnInfo(name = "event")
    public String event;

    @ColumnInfo(name = "info")
    public String info;

    @ColumnInfo(name = "eventTwainId")
    public String eventTwainId;

    @ColumnInfo(name = "create_time")
    public long createTime;

    @ColumnInfo(name = "uuid")
    public String uuid;

    @ColumnInfo(name = "childId")
    public String childId;

    @Override
    public String toString() {
        return "TopeAnalysisMode{" +
                "_id=" + _id +
                ", event='" + event + '\'' +
                ", info='" + info + '\'' +
                ", eventTwainId='" + eventTwainId + '\'' +
                ", createTime=" + createTime +
                ", childId='" + childId + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
