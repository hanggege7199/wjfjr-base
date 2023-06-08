package com.tphz.zh_base.tope_base.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public  interface ILoadInterFace {
    //所有方法
    //--------------注意-------------------------
    //1.其中   DiskCacheType NuLL 自己加的，不做任何操作
    //2.占位符 不符合调用 loadImageMore 方法自己调
    //3.SimpleTarget 匿名创建回调（不能this,乱）
    //-------------------------------------------
    //----------------------所有方法，into一步到位，还有一个接口(加一个方法，另一个也加方便扩展)-------------------------

    /**
     * 普通方法
     */
    void loadImage(Object obj, ImageView imageView);

    /**
     * 加载圆形图片
     */
    void loadCircleImage(Context context, Object obj, ImageView imageView);

    /**
     * 扩展性强
     */
    RequestBuilder<Drawable> loadImageMore(Context context, Object obj);

    /**
     * 加载圆角图片
     */
    void loadGrayscaleImage(Context context,Object obj, ImageView imageView,int radius);

    /**
     * 加载圆角图片
     */
    void loadGrayscaleImage(Context context, Object obj, ImageView imageView, int radius, RoundedCornersTransformation.CornerType cornerType);

    /**
     * 加载指定圆角图片
     */
    void loadGrayscaleImage(Context context, Object obj, ImageView imageView, int topLeftRadius,int topRightRadius,int bottomLeftRadius,int bottomRightRadius);

    /**
     * 自定义裁剪 默认居中裁剪
     */
    void loadCropTransformationImage(Context context, Object obj, ImageView imageView, int width, int height, CropTransformation.CropType cropType);

    /**
     * 加载动图gif
     */
    void loadGifImage(Context context,Object obj,ImageView imageView);

    /**
     * 加载高斯模糊大图
     */
    void loadTransformImage(Object obj,ImageView imageView,int ambiguity);

    /**
     * 加载缩略图
     * @param sizeMultiplier 如设置0.2f缩略
     */
    void loadThumbnailImage(Object obj,ImageView imageView,float sizeMultiplier);

    /**
     * 设置 乌墨色滤 滤镜
     */
    void loadBrightnessFilterTransformationImage(Context context, Object obj, ImageView imageView,float intensity);

    /**
     * 设置滤镜 （亮度）
     */
    void loadBrightnessFilterTransformationImage(Context context, String url, ImageView imageView,float brightness);

    /**
     * 设置 对比度 滤镜
     */
    void loadContrastFilterTransformationImage(Context context, Object obj, ImageView imageView ,float contrast);

    /**
     * 设置滤镜 （马赛克）
     */
    void loadPixelationFilterTransformationImage(Context context, Object obj, ImageView imageView,float pixel);

    /**
     * 设置滤镜 （素描画）
     */
    void loadSketchFilterTransformationImage(Context context, Object obj, ImageView imageView);

    /**
     * 当前跳过内存缓存，并自己设置RequestOptions Cache:true 不缓存 false 缓存
     */
    void loadTransformationSkipMemoryCacheImage(Context context, Object obj, ImageView imageView, RequestOptions options);

    /**
     * 自定义变换
     */
    void loadImageRequest(Context context, Object obj, ImageView imageView, RequestOptions options);

    /**
     * 去掉动画，闪烁
     */
    void loadImageNotAnimate(Context context, Object obj, ImageView imageView);

    /**
     * 类型转换为bitmap
     */
    void loadImageAsBitmap(Context context, Object obj, ImageView imageView);

    /**
     * 类型转换为gif并且有缩略图
     */
    void loadGifImage(Context context, Object obj, ImageView imageView,float sizeMultiplier);
    //-------------------------占位符------------------------------
    /**
     * 设置占位符加载中
     */
    void loadPlaceholderImage(Context context, Object obj, ImageView imageView,@DrawableRes int placeholder);


    /**
     * 设置占位符加载中（动画）
     */
    void loadPlaceholderImageCrossFade(Context context, Object obj, ImageView imageView,@DrawableRes int placeholder);

    /**
     * 设置占位符加载中Drawable
     */
    void loadPlaceholderImage(Context context, Object obj, ImageView imageView,@NotNull Drawable placeholder);

    /**
     * 设置占位符加载错误
     */
    void loadErrorImage(Context context, Object obj, ImageView imageView,@DrawableRes int error);

    /**
     * 设置占位符加载错误Drawable
     */
    void loadErrorImage(Context context, Object obj, ImageView imageView,@NotNull Drawable error);

    /**
     * 设置占位符后备回调
     */
    void loadFallbackImage(Context context, Object obj, ImageView imageView,@DrawableRes int fallback);

    /**
     * 设置占位符后备回调 Drawable
     */
    void loadFallbackImage(Context context, Object obj, ImageView imageView,@NotNull Drawable fallback);

    /**
     * 设置占位符,全部配，其余自己写一个
     */
    void loadPlaceImage(Context context, Object obj, ImageView imageView,@DrawableRes int placeholder,@DrawableRes int error,@DrawableRes int fallback);

    /**
     * 设置占位符(常用)两个
     */
    void loadPlaceholderImage(Context context, Object obj, ImageView imageView,@DrawableRes int placeholder,@DrawableRes int error);

    /**
     * 设置占位符(常用)两个 Drawable
     */
    void loadPlaceholderImage(Context context, Object obj, ImageView imageView,@NotNull Drawable placeholder,@NotNull Drawable error);



    //---------------------------------------------------------------
    /**
     * 设置缓存
     */
    void loadDiskCacheStrategyImage(Context context, Object obj, ImageView imageView, LoadImage.DiskCacheType diskCacheType);

    /**
     * 设置并返回 RequestBuilder<Bitmap>
     */
    RequestBuilder<Bitmap> loadStrategyBitmap(Context context, LoadImage.DiskCacheType diskCacheType, DecodeFormat decodeFormat);

    /**
     * 重新设置 Glide图片大小 override 加载并返回callBack(过时)
     */
    void loadOverrideImage(Context context, Object obj,int width,int height,SimpleTargetCallBack simpleTargetCallBack);

    /**
     * 重新设置 Glide图片大小 override 加载并返回callBack(替换SimpleTarget)
     */
    void loadOverrideImage(Context context, Object obj,int width,int height,CustomTargetDrawableCallBack customTargetDrawableCallBack);


}
