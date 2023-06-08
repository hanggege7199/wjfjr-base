package com.tphz.zh_base.tope_base.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.tphz.zh_base.common.LogUtil;
import com.tphz.zh_base.tope_base.db.TopeAppDatabase;
import com.tphz.zh_base.tope_base.db.TopeDatabaseManager;

import java.util.Map;

/**
 * 桌面使用，保存共享数据给其他app使用
 */
public class TopeDataContentProvider extends ContentProvider {
    public static final String AUTOHORITY = "com.tope.launcher.appcontentprovider";
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final int APP_SETTING = 1;
    private TopeAppDatabase topeAppDatabase;

    static {
        matcher.addURI(AUTOHORITY, "setting", APP_SETTING);
    }

    @Override
    public boolean onCreate() {
        topeAppDatabase = TopeDatabaseManager.getInstance(getContext()).getTopeDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        LogUtil.d("TopeSettingManager", "TopeSettingManager:query uri" + uri + " selectionArgs[0]:" + selectionArgs[0]);
        if (matcher.match(uri) == APP_SETTING) {
            return topeAppDatabase.topeSettingDao().getSettingForKey(selectionArgs[0]);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
//        LogUtil.e("TopeSettingManager", "TopeSettingManager:insert" + values + values.valueSet().toString());

        if (values != null) {
            for (Map.Entry<String, Object> entry : values.valueSet()) {
                TopeDataMode mode = new TopeDataMode();
                mode.key = entry.getKey();
                mode.value = (String) entry.getValue();
                topeAppDatabase.topeSettingDao().insert(mode);
            }
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
