package com.tope.tope_base.scheme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.tope.common.Constants;
import com.tope.common.LogUtil;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SchemeManager {
    public static SchemeManager schemeManager;
    private String schemeName;
    private SchemeHandler schemeHandler;
    private Context context;

    private SchemeManager() {
    }


    public static SchemeManager getInstance() {
        if (schemeManager == null) {
            schemeManager = new SchemeManager();
        }
        return schemeManager;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public void setSchemeHandler(SchemeHandler schemeHandler) {
        this.schemeHandler = schemeHandler;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 内部打开
     *
     * @param context
     * @param scheme
     */
    public void go(Context context, String scheme) throws Exception {
        goScheme(context, scheme, false);
    }

    /**
     * 外部打开
     *
     * @param context
     * @param scheme
     */
    public void goFormOther(Context context, String scheme) throws Exception {
        goScheme(context, scheme, true);
    }

    /**
     * @param context
     * @param scheme
     * @param fromOther 是否外边打开
     */
    private void goScheme(Context context, String scheme, boolean fromOther) throws Exception {
        if (context == null) {
            LogUtil.e("SchemeManager", "context == null");
            return;
        }
        if (TextUtils.isEmpty(schemeName)) {
            LogUtil.e("SchemeManager", "schemeName == null");
            return;
        }
        if (TextUtils.isEmpty(scheme)) {
            LogUtil.e("SchemeManager", "scheme == null");
            return;
        }
        //host:series path: query:courseId=3&coursename=营造馆
        if (scheme.startsWith(schemeName)) {
            Uri uri = Uri.parse(scheme);
            String host = uri.getHost();
            String path = uri.getPath();
            String query = uri.getQuery();
            LogUtil.e("SchemeManager", "host:" + host + " path:" + path + " query:" + query);
            if (schemeHandler != null) {
                schemeHandler.handler(context, host, decode(query), fromOther);
            }
        } else {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(scheme));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e) {
                LogUtil.e("SchemeManager", "e:" + e.getMessage());
                throw new Exception("startActivity error");
            }
        }
    }


    public Map<String, String> decode(String query) {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            try {
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            } catch (Exception e) {
            }
        }
        return query_pairs;


    }
}
