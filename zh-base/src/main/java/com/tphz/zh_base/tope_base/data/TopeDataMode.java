package com.tphz.zh_base.tope_base.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TopeDataMode {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "key")
    public String key;

    @ColumnInfo(name = "value")
    public String value;
}
