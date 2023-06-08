package com.tope.tope_base.scheme;

import android.content.Context;

import java.util.Map;

public interface SchemeHandler {
    void handler(Context context, String path, Map<String, String> map,boolean fromOther);
}
