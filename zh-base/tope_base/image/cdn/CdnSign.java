package com.tope.tope_base.image.cdn;

import android.text.TextUtils;

import com.tope.common.LogUtil;
import com.tope.common.MD5Utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CdnSign {
    private static final Pattern URL_PATTERN = Pattern.compile("^(http://|https://)?([^/?]+)(/[^?]*)?(\\?.*)?$");

    public static String sign(String old) {
        Matcher matcher = URL_PATTERN.matcher(old);
        if (matcher.matches()) {
            String scheme = matcher.group(1);
            String host = matcher.group(2);
            String path = matcher.group(3);
            String args = matcher.group(4);

            LogUtil.e("CdnSign", "scheme:" + scheme + " host：" + host + " path：" + path + " args:" + args);
            Map<String, String> cdns = CdnDomain.getKeys();

            String key = cdns.get(host);
            if (!TextUtils.isEmpty(key)) {
                old = privateKeyA(scheme, host, path, args, key);
            }
            LogUtil.e("CdnSign", "url:" + old);
        }

        return old;

    }

    private static String privateKeyA(String scheme, String host, String path, String args, String key) {
        LogUtil.e("CdnSign", "host:" + host + " filename:" + path + " key:" + key.substring(0,5));
        String timeStr = String.valueOf(System.currentTimeMillis() / 1000);

        String str = path + "-" + timeStr + "-0-0-" + key;
//        LogUtil.e("CdnSign", "str:" + str);
        String md5 = MD5Utils.getMD5String(str);
        String authKey = "auth_key=" + timeStr + "-0-0-" + md5;
        if (TextUtils.isEmpty(args)) {
            return scheme + host + path + "?" + authKey;
        } else {
            return scheme + host + path + args + "&" + authKey;
        }
    }
}
