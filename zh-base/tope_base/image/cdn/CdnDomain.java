package com.tope.tope_base.image.cdn;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tope.common.LogUtil;
import com.tope.tope_base.data.TopeDatas;

import java.util.HashMap;
import java.util.Map;

public class CdnDomain {
    private static final Gson gson = new Gson();
    public static long updateTime = 0;
    private static final Map<String, String> keys = new HashMap<>();

    public static Map<String, String> getKeys() {
        if (keys.size() == 0) {
            String keyStr = TopeDatas.getCdnKey();
            if (!TextUtils.isEmpty(keyStr)) {
                Map<String, String> map = gson.fromJson(keyStr, new TypeToken<Map<String, Object>>() {
                }.getType());
                if (map != null) {
                    keys.clear();
                    keys.putAll(map);
                }
            }
        }
        return keys;
    }

    public static void setKeys(Map<String, String> maps) {
        updateTime = System.currentTimeMillis();
        keys.clear();
        keys.putAll(maps);
        String str = gson.toJson(maps);
        TopeDatas.setCdnKey(str);
    }
}
