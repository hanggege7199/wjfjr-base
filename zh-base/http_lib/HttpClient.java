package com.tope.http_lib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tope.tope_base.data.TopeDatas;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    protected Retrofit retrofit;
    private static List<Interceptor> interceptors = new ArrayList<>();
    protected OkHttpClient okHttpClient;
    protected OkHttpClient downloadOkHttpClient;

    private static String getUA(Context context) {
        String os = "Android";
        String str = "tphz/czmm/" + getVersionName(context) + " (" + os + ";" + Build.MODEL + ";" + Build.VERSION.INCREMENTAL + ";" + getVersionCode(context) + ";" + getPackageName(context) + ";channel)";
        HLog.log("RetrofitClient" + "UA:" + str);
        return str;
    }

    private static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取版本序号
     *
     * @return 当前应用的版本序号
     */
    private static String getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            int versionCode = info.versionCode;
            return String.valueOf(versionCode);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    private static String getVersionName(Context context) {
        String versionName = "";
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionName
            versionName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    public String getUserId() {
        return "";
    }

    public String getAccountId() {
        return "";
    }

    public CallAdapter.Factory callFactory() {
        return null;
    }

    public String getChildrenId() {
        return "";
    }

    public HttpClient(Context context, String baseUrl) {
        OkHttpClient.Builder downloadClientBuild = new OkHttpClient.Builder();
        HttpLoggingInterceptor downLog = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String s) {
                HLog.log(s);
            }
        });
        downLog.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        downloadClientBuild.addInterceptor(downLog);
        downloadOkHttpClient = downloadClientBuild.build();


        OkHttpClient.Builder clientBuild = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String s) {
                HLog.log(s);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        clientBuild.addInterceptor(new ConnectivityInterceptor(context));
        clientBuild.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                String accountId = getAccountId();
                if (TextUtils.isEmpty(accountId)) {
                    accountId = TopeDatas.getAccountId();
                }
                if (TextUtils.isEmpty(accountId)) {
                    accountId = "";
                }

                String userId = getUserId();
                if (TextUtils.isEmpty(userId)) {
                    userId = TopeDatas.getUserId();
                }
                if (TextUtils.isEmpty(userId)) {
                    userId = "";
                }
                String childrenId = getChildrenId();
                if (TextUtils.isEmpty(childrenId)) {
                    childrenId = TopeDatas.getChildrenId();
                }
                if (TextUtils.isEmpty(childrenId)) {
                    childrenId = "";
                }
                String deviceId = TopeDatas.getDeviceId();
                if (TextUtils.isEmpty(deviceId)) {
                    deviceId = "";
                }
                String token = TopeDatas.getToken();
                if (TextUtils.isEmpty(token)) {
                    token = "";
                }
                requestBuilder.addHeader("token", token);
                requestBuilder.addHeader("User-Agent", getUA(context));
                requestBuilder.addHeader("Account-Id", accountId);
                requestBuilder.addHeader("User-Id", userId);
                requestBuilder.addHeader("Children-Id", childrenId);
                requestBuilder.addHeader("Device-Id", deviceId);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        clientBuild.addInterceptor(new AccountInterceptor(context));
        for (Interceptor i : interceptors) {
            clientBuild.addInterceptor(i);
        }
        clientBuild.addInterceptor(loggingInterceptor);
        //忽略https校验
//        try {
//            final TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                        }
//
//                        @Override
//                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                            return new java.security.cert.X509Certificate[]{};
//                        }
//                    }
//            };
//
//            // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            // Create an ssl socket factory with our all-trusting manager
//            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            clientBuild.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
//            clientBuild.hostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            });
//        } catch (Exception e) {
//
//        }
        okHttpClient = clientBuild.build();
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Data.class, new DataSecurityAdapter())
//                .registerTypeAdapter(DataList.class, new DataListSecurityAdapter()).create();
//                .registerTypeAdapter(JsonObject.class, new ObjectSecurityAdapter()).create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());
        if (callFactory() != null) {
            builder.addCallAdapterFactory(callFactory());
        }
        retrofit = builder.build();
    }

    public static void addInterceptor(Interceptor interceptor) {
        if (interceptor != null) {
            interceptors.add(interceptor);
        }
    }

    public void cancelAllDownload() {
        downloadOkHttpClient.dispatcher().cancelAll();
    }

    /**
     * @param url      下载连接
     * @param savePath 储存路径
     * @param listener 下载监听
     */
    public void download(final String url, final String savePath, final OnDownloadListener listener) {

        Request request = new Request.Builder().url(url).tag(url).build();
        downloadOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                listener.onFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (200 == response.code()) {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    try {
                        is = response.body().byteStream();
                        long total = response.body().contentLength();
                        File fileTmp = new File(savePath + ".tmp");
                        if (fileTmp.exists()) {
                            fileTmp.delete();
                        }
                        File fileDest = new File(savePath);
                        fos = new FileOutputStream(fileTmp);
                        long sum = 0;
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            sum += len;
                            int progress = (int) (sum * 1.0f / total * 100);
                            // 下载中
                            listener.onProgress(progress);
                        }
                        fos.flush();
                        // 下载完成
                        fileTmp.renameTo(fileDest);
                        listener.onSuccess();
                    } catch (Exception e) {
                        listener.onFailed(e);
                    } finally {
                        try {
                            if (is != null)
                                is.close();
                        } catch (IOException e) {
                        }
                        try {
                            if (fos != null)
                                fos.close();
                        } catch (IOException e) {
                        }
                    }
                } else {
                    listener.onFailed(new Exception("http code " + response.code()));
                }

            }
        });
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onSuccess();

        /**
         * @param progress 下载进度
         */
        void onProgress(int progress);

        /**
         * 下载失败
         */
        void onFailed(Exception e);
    }
}
