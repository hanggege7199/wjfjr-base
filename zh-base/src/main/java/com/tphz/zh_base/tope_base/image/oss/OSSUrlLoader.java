package com.tphz.zh_base.tope_base.image.oss;

import androidx.annotation.NonNull;

import com.alibaba.sdk.android.oss.OSS;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

public class OSSUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    OSS oss;

    public OSSUrlLoader(OSS oss) {
        this.oss = oss;
    }

    @Override
    public LoadData<InputStream> buildLoadData(
            @NonNull GlideUrl model, int width, int height, @NonNull Options options) {
        return new LoadData<>(model, new OSSStreamFetcher(oss, model));
    }

    @Override
    public boolean handles(@NonNull GlideUrl glideUrl) {
        if (glideUrl.toStringUrl().startsWith("https://xfans-nas-oss")) {
            return true;
        } else {
            return false;
        }

    }

    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        OSS oss;

        /**
         * Constructor for a new Factory that runs requests using given client.
         *
         * @param oss this is typically an instance of {@code OkHttpClient}.
         */
        public Factory(@NonNull OSS oss) {
            this.oss = oss;
        }

        @NonNull
        @Override
        public ModelLoader<GlideUrl, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new OSSUrlLoader(oss);
        }

        @Override
        public void teardown() {
            // Do nothing, this instance doesn't own the client.
        }
    }

}
