package com.tphz.zh_base.http_lib;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {
    private Context context;

    public ConnectivityInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!NetWorkUtils.IsNetWorkEnable(context)) {
            throw new NoConnectivityException();
        } else {
            Response response = chain.proceed(chain.request());
            return response;
        }
    }
}

