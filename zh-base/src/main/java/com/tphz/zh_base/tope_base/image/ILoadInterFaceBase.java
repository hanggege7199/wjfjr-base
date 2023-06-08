package com.tphz.zh_base.tope_base.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public  interface ILoadInterFaceBase {
    //所有方法
    //--------------注意-------------------------
    //1.扩展接口，所有都有返回值，自己into加载
    //
    //
    //-------------------------------------------
    //-----------------------------------------------
    

    /**
     * 加载圆形图片
     */
     RequestBuilder<Drawable> loadCircleImage(Context context, Object obj);


    /**
     * 加载圆角图片
     */
     RequestBuilder<Drawable> loadGrayscaleImage(Context context,Object obj ,int radius);

    /**
     * 加载圆角图片
     */
     RequestBuilder<Drawable> loadGrayscaleImage(Context context, Object obj , int radius, RoundedCornersTransformation.CornerType cornerType);

    /**
     * 加载指定圆角图片
     */
     RequestBuilder<Drawable> loadGrayscaleImage(Context context, Object obj , int topLeftRadius,int topRightRadius,int bottomLeftRadius,int bottomRightRadius);

    /**
     * 自定义裁剪 默认居中裁剪
     */
     RequestBuilder<Drawable> loadCropTransformationImage(Context context, Object obj , int width, int height, CropTransformation.CropType cropType);

    /**
     * 加载动图gif
     */
     RequestBuilder<GifDrawable> loadGifImage(Context context, Object obj );

    /**
     * 加载高斯模糊大图
     */
     RequestBuilder<Drawable> loadTransformImage(Context context,Object obj,int ambiguity);

    /**
     * 加载缩略图
     * @param sizeMultiplier 如设置0.2f缩略
     */
     RequestBuilder<Drawable> loadThumbnailImage(Context context,Object obj ,float sizeMultiplier);

    /**
     * 设置 乌墨色滤 滤镜
     */
     RequestBuilder<Drawable> loadBrightnessFilterTransformationImage(Context context, Object obj,float intensity);

    /**
     * 设置滤镜 （亮度）
     */
     RequestBuilder<Drawable> loadBrightnessFilterTransformationImage(Context context, String url ,float brightness);

    /**
     * 设置 对比度 滤镜
     */
     RequestBuilder<Drawable> loadContrastFilterTransformationImage(Context context, Object obj  ,float contrast);

    /**
     * 设置滤镜 （马赛克）
     */
     RequestBuilder<Drawable> loadPixelationFilterTransformationImage(Context context, Object obj ,float pixel);

    /**
     * 设置滤镜 （素描画）
     */
     RequestBuilder<Drawable> loadSketchFilterTransformationImage(Context context, Object obj );

    /**
     * 当前跳过内存缓存，并自己设置RequestOptions Cache:true 不缓存 false 缓存
     */
     RequestBuilder<Drawable> loadTransformationSkipMemoryCacheImage(Context context, Object obj , RequestOptions options);

    /**
     * 自定义变换
     */
     RequestBuilder<Drawable> loadImageRequest(Context context, Object obj , RequestOptions options);

    /**
     * 去掉动画，闪烁
     */
     RequestBuilder<Drawable> loadImageNotAnimate(Context context, Object obj );

    /**
     * 类型转换为bitmap
     */
     RequestBuilder<Bitmap> loadImageAsBitmap(Context context, Object obj );

    /**
     * 类型转换为gif并且有缩略图
     */
     RequestBuilder<GifDrawable> loadGifImage(Context context, Object obj ,float sizeMultiplier);

    //---------------------------------------------------------------
    /**
     * 设置缓存
     */
     RequestBuilder<Drawable> loadDiskCacheStrategyImage(Context context, Object obj , LoadImage.DiskCacheType diskCacheType);

    
    
    


}
