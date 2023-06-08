package com.tphz.zh_base.tope_base.image.oss;

import androidx.annotation.NonNull;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.tphz.zh_base.common.LogUtil;


import java.io.IOException;
import java.io.InputStream;

class OSSStreamFetcher implements DataFetcher<InputStream>, OSSCompletedCallback<GetObjectRequest, GetObjectResult> {
    private OSS oss;
    private GlideUrl url;

    private OSSAsyncTask task;

    private InputStream stream;

    private DataCallback<? super InputStream> callback;

    @SuppressWarnings("WeakerAccess")
    public OSSStreamFetcher(OSS client, GlideUrl url) {
        this.oss = client;
        this.url = url;
    }

    @Override
    public void loadData(@NonNull Priority priority, @NonNull DataFetcher.DataCallback<? super InputStream> callback) {
        LogUtil.e("glide", "loadData");
        LogUtil.e("glide","urlL:"+url.toStringUrl());
//        String bucket = url.toStringUrl().substring(,)

        GetObjectRequest get = new GetObjectRequest("xfans-nas-oss", "311bd2c.bmp");
        task = oss.asyncGetObject(get, this);
        this.callback = callback;
    }

    @Override
    public void cleanup() {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
        }
    }

    @Override
    public void cancel() {
        if (task != null) {
            task.cancel();
        }
    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }

    @Override
    public void onSuccess(GetObjectRequest request, GetObjectResult result) {
        LogUtil.e("glide", "onSuccess");
        stream = result.getObjectContent();
        callback.onDataReady(stream);
    }

    @Override
    public void onFailure(GetObjectRequest request, ClientException clientException, ServiceException serviceException) {
        LogUtil.e("glide", "onFailure");
        if (clientException != null) {
            callback.onLoadFailed(clientException);
            LogUtil.e("glide", "clientException:"+clientException.getMessage());
        } else if (serviceException != null) {
            callback.onLoadFailed(serviceException);
            LogUtil.e("glide", "clientException:"+serviceException.getMessage());
        } else {
            callback.onLoadFailed(new Exception("unknown"));
        }
    }

}