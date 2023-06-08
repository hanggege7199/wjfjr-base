package com.tope.http_lib;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.tope.tope_base.data.TopeDatas;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AccountInterceptor implements Interceptor {
    private Context mContext;
    public static final String ACCOUNT_EXPIRED = "ACCOUNT_EXPIRED";
    public static final String CODE = "ACCOUNT_EXPIRED_CODE";

    public AccountInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        Headers allHeaders = response.headers();

        int code = clone.code();
        String nextToken = allHeaders.get("next-token");
        if (!TextUtils.isEmpty(nextToken)) {
            TopeDatas.setToken(nextToken);
            HLog.log("AccountInterceptor: response next-token");
        }
        HLog.log("AccountInterceptor: responseCode:" + code);
        Intent intent = new Intent(ACCOUNT_EXPIRED);
        switch (code) {
            case 401://踢出登录
                intent.putExtra(CODE, 401);
                mContext.sendBroadcast(intent);
                break;
            case 455://试用帐号到期
                intent.putExtra(CODE, 455);
                mContext.sendBroadcast(intent);
                break;
            case 456://帐号停用
                intent.putExtra(CODE, 456);
                mContext.sendBroadcast(intent);
                break;
            case 403://多个设备下线（挤掉）
                intent.putExtra(CODE, 403);
                mContext.sendBroadcast(intent);
                break;
        }
        return response;
    }
}