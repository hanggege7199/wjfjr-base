package com.tphz.zh_base.tope_base.image.oss;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.LibraryGlideModule;
import com.tphz.zh_base.common.LogUtil;
import com.tphz.zh_base.http_lib.OSSClientUtils;

import java.io.InputStream;

@GlideModule
public class OSSModule extends LibraryGlideModule {

    //超时时长
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        LogUtil.e("glide", "registerComponents");
        OSSUrlLoader.Factory factory = new OSSUrlLoader.Factory(OSSClientUtils.getOssClient(context));
        registry.prepend(GlideUrl.class, InputStream.class, factory);
    }

}
