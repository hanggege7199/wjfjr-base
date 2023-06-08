package com.tphz.zh_base.http_lib;

import android.os.Handler;
import android.os.Looper;

import com.tope.http_lib.FileCallback;
import com.tope.http_lib.FileUpload;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class ThirdHttpClient {
    private static ThirdHttpClient simpleHttpClient;
    private OkHttpClient client;
    private Handler handler;

    private ThirdHttpClient() {
        handler = new Handler(Looper.getMainLooper());
        OkHttpClient.Builder clientBuild = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuild.addInterceptor(loggingInterceptor);
        client = clientBuild.build();
    }

    public static synchronized ThirdHttpClient getInstance() {
        if (simpleHttpClient == null) {
            simpleHttpClient = new ThirdHttpClient();
        }
        return simpleHttpClient;
    }

    public void upload2OSS(FileUpload fileUpload, File file, final OSSCallback callback) {
        MediaType mediaType = MediaType.parse("application/octet-stream");//设置类型，类型为八位字节流
        RequestBody requestBody = RequestBody.create(mediaType, file);//把文件与类型放入请求体
        final FileCallback fileCallback = fileUpload.getCallback();
        final String path = fileCallback.getDir() + "/" + file.getName();
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", path)//添加表单数据
                .addFormDataPart("policy", fileCallback.getPolicy())
                .addFormDataPart("OSSAccessKeyId", fileCallback.getAccessid())
                .addFormDataPart("signature", fileCallback.getSignature())
                .addFormDataPart("success_action_status", "200")
                .addFormDataPart("file", file.getName(), requestBody)//文件名,请求体里的文件
                .build();

        Request request = new Request.Builder()
                .url(fileCallback.getHost())
                .post(multipartBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(fileCallback.getHost() + "/" + path);
                    }
                });
            }
        });

    }

    public interface OSSCallback {

        void onFailure(IOException e);

        void onResponse(String path);
    }
}
