package com.tphz.zh_base.tope_base.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;


import com.tphz.zh_base.common.LogUtil;

import java.util.concurrent.Executors;

public class TopeDataManager {
    private static TopeDataManager topeSettingManager;
    private Uri settingUri = Uri.parse("content://com.lhkj.caique.appcontentprovider/setting");
    private Context context;

    public TopeDataManager(Context context) {
        this.context = context;
    }

    public static synchronized TopeDataManager getInstance(Context context) {
        if (topeSettingManager == null) {
            topeSettingManager = new TopeDataManager(context);
        }
        return topeSettingManager;
    }

    public void putKeyAsync(String key, String value) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                putKey(key, value);
            }
        });
    }

    public void putKey(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            LogUtil.e("TopeDataManager", "key mush NonNull");
            return;
        }
        if (value == null) {
            value = "";
        }
        ContentValues values = new ContentValues();
        values.put(key, value);
        // 获取ContentResolver
        ContentResolver resolver = context.getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver.insert(settingUri, values);
    }

    public String getKey(String key) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(settingUri, null, null, new String[]{key}, null);
        String value = null;
        if (cursor == null) return null;
        while (cursor.moveToNext()) {
            value = cursor.getString(1);
//            LogUtil.e("TopeSettingManager", "TopeSettingManager: value" + value);
        }
        cursor.close();
        return value;
    }
}
