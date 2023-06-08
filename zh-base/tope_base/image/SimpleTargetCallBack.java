package com.tope.tope_base.image;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.NotNull;

/////////////////////////接口回调处/////////////////////////////
//SimpleTarget回调 （已过时）
public interface SimpleTargetCallBack {
    void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition);
}

//CustomTarget回调 （替换SimpleTarget回调）
interface CustomTargetDrawableCallBack{
    void onResourceReady(@NonNull @NotNull Drawable resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Drawable> transition);
    void onLoadCleared(@Nullable Drawable placeholder);
}

