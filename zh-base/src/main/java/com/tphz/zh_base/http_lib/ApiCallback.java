package com.tphz.zh_base.http_lib;

import android.text.TextUtils;

import com.tope.http_lib.Data;
import com.tope.http_lib.DataList;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (200 == response.code()) {
            if (response.body() != null) {
                if (response.body() instanceof Data<?>) {
                    if (((Data<?>) response.body()).getCode() == 0) {
                        onSuccess((T) response.body());
                    } else {
                        HLog.error(new ApiCallbackException("message: " + ((Data<?>) response.body()).getMsg()));
                        onFailure(((Data<?>) response.body()).getMsg() + "");
                    }
                } else if (response.body() instanceof DataList<?>) {
                    if (((DataList<?>) response.body()).getCode() == 0) {
                        onSuccess((T) response.body());
                    } else {
                        HLog.error(new ApiCallbackException("message: " + ((DataList<?>) response.body()).getMsg()));
                        onFailure(((DataList<?>) response.body()).getMsg());
                    }
                } else {
                    HLog.error(new ApiCallbackException("(code:8023) " + response.body()));
                    onFailure("遇到点小问题，请稍后重试(code:8023)");
                }
            } else {
                HLog.error(new ApiCallbackException("(code:8021) response.body() == null"));
                onFailure("遇到点小问题，请稍后重试(code:8022)");
            }
        } else {
            try {
                String body = response.errorBody().string();
                String msg = "遇到点小问题，请稍后重试(code:8021)";
                if (!TextUtils.isEmpty(body)) {
                    JSONObject jsonObject = new JSONObject(body);
                    msg = jsonObject.getString("message");
                }
                HLog.error(new ApiCallbackException("(code:8021)response.code():" + response.code() + " " + body));
                onFailure(msg);

            } catch (Exception e) {
                onFailure("遇到点小问题，请稍后重试(code:8020)" + response.code());
                HLog.error(new ApiCallbackException("(code:8020)response.code():" + response.code()));
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        HLog.error(t);
        if (t instanceof NoConnectivityException) {
            onFailure("网络无连接，请检查网络");
        } else if (t instanceof IOException) {
            onFailure("网络异常，请检查网络");
        } else {
            onFailure("遇到点小问题，请稍后重试(code:8026)");
        }
    }

    public abstract void onSuccess(T data);

    public abstract void onFailure(String msg);
}
