package com.tope.tope_base.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tope.common.ScreenUtil;

import org.jetbrains.annotations.NotNull;

import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;

//
public class LoadImage extends BaseLoadImage implements ILoadInterFace {
    public static LoadImage loadImage=new LoadImage();
    //修改 obj 加一个方法判断后每一个加上 返回boolean 判断是否可以加载
    //目前有一个扩展性的方法loadImageMore
    //动态扩展OkHttp 方式 继承AppGlideModule,registry添加append 自己的
    //工具类，采用Glide,取消采用GlideApp

    public static LoadImage getInstance(){
        if (loadImage==null) {
            loadImage=new LoadImage();
        }
        return loadImage;
    }
    /**
     * DiskCacheStrategy.ALL 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
     * DiskCacheStrategy.NONE 不使用磁盘缓存
     * DiskCacheStrategy.DATA 在资源解码前就将原始数据写入磁盘缓存
     * DiskCacheStrategy.RESOURCE 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
     * DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。
     * 其中   DiskCacheType NuLL 自己加的，不做任何操作
     */
    public enum DiskCacheType {
        ALL,
        NONE,
        DATA,
        RESOURCE,
        AUTOMATIC,
        NULL
    }


    @Override
    public void loadImage(Object obj, ImageView imageView) {
        if (obj instanceof String) {
            Glide.with(imageView.getContext()).load(obj).into(imageView);
        }
        if (obj instanceof Bitmap) {
            Glide.with(imageView.getContext()).load(obj).into(imageView);
        }
        if (obj instanceof Drawable) {
            Glide.with(imageView.getContext()).load(obj).into(imageView);
        }
    }

    /**
     * 加载圆形图片
     *
     */
    @Override
    public void loadCircleImage(Context context, Object obj, ImageView imageView) {
        Glide.with(context).load(obj).circleCrop().into(imageView);
    }

    /**
     * 扩展性强
     */
    @Override
    public RequestBuilder<Drawable> loadImageMore(Context context, Object obj) {
        return Glide.with(context).load(obj);
    }

    /**
     * 加载圆角图片
     * @param radius 圆角
     *
     */
    @Override
    public void loadGrayscaleImage(Context context,Object obj, ImageView imageView,int radius) {
        Glide.with(context).load(obj).apply(new RequestOptions().transform(new RoundedCorners(radius))).into(imageView);
    }

    /**
     * 加载圆角图片
     * @param radius 圆角
     * @param cornerType 圆角类型
     */
    @Override
    public void loadGrayscaleImage(Context context, Object obj, ImageView imageView, int radius, RoundedCornersTransformation.CornerType cornerType) {
        Glide.with(context).load(obj).apply(initOptions(new RoundedCornersTransformation(radius,0,cornerType))).into(imageView);
    }

    /**
     * 加载指定圆角图片
     * @param topLeftRadius 左上角
     * @param topRightRadius 右上角
     * @param bottomLeftRadius 左下角
     * @param bottomRightRadius 右下角
     */
    @Override
    public void loadGrayscaleImage(Context context, Object obj, ImageView imageView, int topLeftRadius,int topRightRadius,int bottomLeftRadius,int bottomRightRadius) {
        Glide.with(context).load(obj).apply(initOptions(topLeftRadius,topRightRadius,bottomLeftRadius,bottomRightRadius)).into(imageView);
    }


    /**
     * @describe 自定义裁剪 默认居中裁剪
     * @param width,height 圆角宽高
     * @param cropType 裁剪方向
     */
    @Override
    public void loadCropTransformationImage(Context context, Object obj, ImageView imageView, int width, int height, CropTransformation.CropType cropType) {
        Glide.with(context).load(obj).apply(initOptions(new CropTransformation(width,height, cropType))).into(imageView);
    }


    /**
     * @describe 加载动图gif
     * @param context
     * @param obj
     * @param imageView
     */
    @Override
    public void loadGifImage(Context context,Object obj,ImageView imageView){
        Glide.with(context).asGif().load(obj).into(imageView);
    }

    /**
     * @describe 加载高斯模糊大图
     * @param ambiguity 模糊度  eg ：80
     */
    @Override
    public void loadTransformImage(Object obj,ImageView imageView,int ambiguity){
        Glide.with(imageView.getContext()).load(obj).apply(initOptions(new BlurTransformation(ambiguity))).into(imageView);
    }

    /**
     * @describe 加载缩略图
     * @param sizeMultiplier 如设置0.2f缩略
     */
    @Override
    public void loadThumbnailImage(Object obj,ImageView imageView,float sizeMultiplier){
        float fp=sizeMultiplier;
        if (fp<0.0f){
            fp=0f;
        }
        if (fp>1.0f){
            fp=1f;
        }
        Glide.with(imageView.getContext()).load(obj).thumbnail(fp).into(imageView);//缩略的参数
    }


    /**
     * @describe 设置 乌墨色滤 滤镜
     * @param context   当前Activity的上下文对象
     */
    @Override
    public void loadBrightnessFilterTransformationImage(Context context, Object obj, ImageView imageView,float intensity) {
        float fp=intensity;
        if (fp<0.0f){
            fp=0f;
        }
        if (fp>1.0f){
            fp=1f;
        }
        Glide.with(context).load(obj).apply(initOptions(new SepiaFilterTransformation(fp))).into(imageView);
    }
    /**
     * @describe 设置滤镜 （亮度）
     * @param context   当前Activity的上下文对象
     * @param brightness 亮度
     */
    @Override
    public void loadBrightnessFilterTransformationImage(Context context, String url, ImageView imageView,float brightness) {
        Glide.with(context).load(url).apply(initOptions(new BrightnessFilterTransformation(brightness))).into(imageView);
    }
    /**
     * @describe 设置 对比度 滤镜
     * @param context   当前Activity的上下文对象
     * @param contrast   对比度
     */
    @Override
    public void loadContrastFilterTransformationImage(Context context, Object obj, ImageView imageView ,float contrast) {
        Glide.with(context).load(obj).apply(initOptions(new ContrastFilterTransformation(contrast))).into(imageView);
    }

    /**
     * @describe 设置滤镜 （马赛克）
     * @param context   当前Activity的上下文对象
     * @param pixel 不超过100f
     */
    public void loadPixelationFilterTransformationImage(Context context, Object obj, ImageView imageView,float pixel) {
        Glide.with(context).load(obj).apply(initOptions(new PixelationFilterTransformation(pixel))).into(imageView);
    }


    /**
     * @describe 设置滤镜 （素描画）
     * @param context   当前Activity的上下文对象
     * @param imageView
     */
    @Override
    public void loadSketchFilterTransformationImage(Context context, Object obj, ImageView imageView) {
        Glide.with(context).load(obj).apply(initOptions(new SketchFilterTransformation())).into(imageView);
    }


    /**
     * @describe 当前跳过内存缓存，并自己设置RequestOptions Cache:true 不缓存 false 缓存
     * @param context   当前Activity的上下文对象
     * @param imageView
     */
    @Override
    public void loadTransformationSkipMemoryCacheImage(Context context, Object obj, ImageView imageView, RequestOptions options) {
        Glide.with(context).load(obj).skipMemoryCache(true).apply(options).into(imageView);
    }

    /**
     * 自定义变换
     * @param options 变换
     */
    @Override
    public void loadImageRequest(Context context, Object obj, ImageView imageView,@NotNull RequestOptions options) {
        Glide.with(context).load(obj).apply(options).into(imageView);
    }

    /**
     * 去掉动画，闪烁
     *
     */
    @Override
    public void loadImageNotAnimate(Context context, Object obj, ImageView imageView) {
        Glide.with(context).load(obj).dontAnimate().into(imageView);
    }

    /**
     * 类型bitmap
     *
     */
    @Override
    public void loadImageAsBitmap(Context context, Object obj, ImageView imageView) {
        Glide.with(context).asBitmap().load(obj).into(imageView);
    }

    /**
     * gif 缩略图
     * @param context
     * @param obj
     * @param imageView
     * @param sizeMultiplier 数值0-1
     */
    @Override
    public void loadGifImage(Context context, Object obj, ImageView imageView, float sizeMultiplier) {
        float fp=sizeMultiplier;
        if (fp<0.0f){
            fp=0f;
        }
        if (fp>1.0f){
            fp=1f;
        }
        Glide.with(context).asGif().load(obj).thumbnail(fp).into(imageView);
    }

    /**
     * @describe 设置占位符加载中
     * @param context   当前Activity的上下文对象
     * @param placeholder 设置图片
     */
    @Override
    public void loadPlaceholderImage(Context context, Object obj, ImageView imageView,@DrawableRes int placeholder) {
        Glide.with(context).load(obj).placeholder(placeholder).into(imageView);
    }

    /**
     * @describe 设置占位符加载中 动画
     * @param context   当前Activity的上下文对象
     * @param placeholder 设置图片
     */
    @Override
    public void loadPlaceholderImageCrossFade(Context context, Object obj, ImageView imageView,@DrawableRes int placeholder) {
        Glide.with(context).load(obj).placeholder(placeholder).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
    }

    /**
     * @describe 设置占位符加载中Drawable
     * @param context   当前Activity的上下文对象
     * @param placeholder 设置图片
     */
    @Override
    public void loadPlaceholderImage(Context context, Object obj, ImageView imageView,@NotNull Drawable placeholder) {
        Glide.with(context).load(obj).placeholder(placeholder).into(imageView);
    }

    /**
     * @describe 设置占位符加载错误
     * @param context   当前Activity的上下文对象
     * @param error 设置图片
     */
    @Override
    public void loadErrorImage(Context context, Object obj, ImageView imageView,@DrawableRes int error) {
        Glide.with(context).load(obj).error(error).into(imageView);
    }



    /**
     * @describe 设置占位符加载错误Drawable
     * @param context   当前Activity的上下文对象
     * @param error 设置图片
     */
    @Override
    public void loadErrorImage(Context context, Object obj, ImageView imageView,@NotNull Drawable error) {
        Glide.with(context).load(obj).error(error).into(imageView);
    }

    /**
     * @describe 设置占位符后备回调
     * @param context   当前Activity的上下文对象
     * @param fallback 设置图片
     */
    @Override
    public void loadFallbackImage(Context context, Object obj, ImageView imageView,@DrawableRes int fallback) {
        Glide.with(context).load(obj).fallback(fallback).into(imageView);
    }

    /**
     * @describe 设置占位符后备回调 Drawable
     * @param context   当前Activity的上下文对象
     * @param fallback 设置图片
     */
    @Override
    public void loadFallbackImage(Context context, Object obj, ImageView imageView,@NotNull Drawable fallback) {
        Glide.with(context).load(obj).fallback(fallback).into(imageView);
    }

    /**
     * @describe 设置占位符,全部配，其余自己写一个
     * @param context   当前Activity的上下文对象
     * @param placeholder 加载
     * @param error 错误
     * @param fallback 后备
     *
     */
    @Override
    public void loadPlaceImage(Context context, Object obj, ImageView imageView,@DrawableRes int placeholder,@DrawableRes int error,@DrawableRes int fallback) {
        try {
            Glide.with(context).load(obj).placeholder(placeholder).error(error).fallback(fallback).into(imageView);
        } catch (Throwable throwable){
            Log.e("GlideError", "加载错误" );
            throwable.printStackTrace();
        }
    }

    /**
     * 设置占位符(常用)两个
     * @param placeholder 加载
     * @param error 错误
     */
    @Override
    public void loadPlaceholderImage(Context context, Object obj, ImageView imageView, int placeholder, int error) {
        Glide.with(context).load(obj).placeholder(placeholder).error(error).into(imageView);
    }

    /**
     * 设置占位符(常用)两个 Drawable
     * @param placeholder 加载
     * @param error 错误
     */
    @Override
    public void loadPlaceholderImage(Context context, Object obj, ImageView imageView, @NotNull Drawable placeholder, @NotNull Drawable error) {
        Glide.with(context).load(obj).placeholder(placeholder).error(error).into(imageView);
    }


    /**
     * @describe 设置缓存
     * @param diskCacheType 设置缓存类型
     */
    @Override
    public void loadDiskCacheStrategyImage(Context context, Object obj, ImageView imageView,DiskCacheType diskCacheType){
        if (diskCacheType== DiskCacheType.ALL){
            Glide.with(context).load(obj).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        } else if (diskCacheType== DiskCacheType.RESOURCE){
            Glide.with(context).load(obj).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imageView);
        } else if (diskCacheType== DiskCacheType.NONE){
            Glide.with(context).load(obj).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
        } else if (diskCacheType== DiskCacheType.AUTOMATIC){
            Glide.with(context).load(obj).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView);
        } else if (diskCacheType== DiskCacheType.DATA){
            Glide.with(context).load(obj).diskCacheStrategy(DiskCacheStrategy.DATA).into(imageView);
        }else {
            Glide.with(context).load(obj).into(imageView);
        }
    }

    /**
     * @describe 设置并返回 RequestBuilder<Bitmap>
     * @param diskCacheType 设置缓存类型
     */
    @Override
    public RequestBuilder<Bitmap> loadStrategyBitmap(Context context, DiskCacheType diskCacheType, DecodeFormat decodeFormat){
        if (diskCacheType== DiskCacheType.ALL){
           return Glide.with(context).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).format(decodeFormat);
        } else if (diskCacheType== DiskCacheType.RESOURCE){
           return Glide.with(context).asBitmap().diskCacheStrategy(DiskCacheStrategy.RESOURCE).format(decodeFormat);
        } else if (diskCacheType== DiskCacheType.NONE){
           return Glide.with(context).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).format(decodeFormat);
        } else if (diskCacheType== DiskCacheType.AUTOMATIC){
           return Glide.with(context).asBitmap().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).format(decodeFormat);
        } else if (diskCacheType== DiskCacheType.DATA){
           return Glide.with(context).asBitmap().diskCacheStrategy(DiskCacheStrategy.DATA).format(decodeFormat);
        }else {
            return Glide.with(context).asBitmap().format(decodeFormat);
        }
    }

    /**
     * 重新设置 Glide图片大小 override 加载并返回callBack(过时)
     * @param width 宽（已按照dp换算）
     * @param height 高（已按照dp换算）
     * @param simpleTargetCallBack 回调SimpleTarget
     */
    @Override
    public void loadOverrideImage(Context context, Object obj,int width,int height,SimpleTargetCallBack simpleTargetCallBack) {
        Glide.with(context).load(obj).override(ScreenUtil.getPxByDp(width), ScreenUtil.getPxByDp(height)).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (simpleTargetCallBack!=null){
                    simpleTargetCallBack.onResourceReady(resource, transition);
                }
            }
        });
    }

    /**
     * 重新设置 Glide图片大小 override 加载并返回callBack(Drawable)(替换SimpleTarget)
     * @param width 宽（已按照dp换算）
     * @param height 高（已按照dp换算）
     * @param customTargetDrawableCallBack 回调SimpleTarget
     */

    @Override
    public void loadOverrideImage(Context context, Object obj, int width, int height, CustomTargetDrawableCallBack customTargetDrawableCallBack) {
        Glide.with(context).load(obj).override(ScreenUtil.getPxByDp(width), ScreenUtil.getPxByDp(height)).into(new CustomTarget<Drawable>() {

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                customTargetDrawableCallBack.onResourceReady(resource, transition);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                customTargetDrawableCallBack.onLoadCleared(placeholder);
            }
        });
    }


    /**
     * 需要asFile asDrawable 接着加
     */


    /**
     * @describe 设置缓存
     *      Glide有两种缓存机制，一个是内存缓存，一个是硬盘缓存。
     *      内存缓存的主要作用是防止应用重复将图片数据读取到内存当中，
     *      而硬盘缓存的主要作用是防止应用重复从网络或其他地方重复下载和读取数据
     *
     * @return 这里默认设置全部为禁止缓存
     * @param transformation 这里用的是依赖的bitmap
     */
    private static RequestOptions initOptions(BitmapTransformation transformation){
        return new RequestOptions()
                .transform(transformation);
    }

    /**
     * 初始化添加圆角
     */
    @SuppressLint("CheckResult")
    private static RequestOptions initOptions(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius){
        RoundedCornersTransformation rounded1;
        RoundedCornersTransformation rounded2;
        RoundedCornersTransformation rounded3;
        RoundedCornersTransformation rounded4;
        if (topLeftRadius>0){
            rounded1=new RoundedCornersTransformation(topLeftRadius,0, RoundedCornersTransformation.CornerType.TOP_LEFT);
        }else {
            rounded1=new RoundedCornersTransformation(0,0);
        }
        if (topRightRadius>0){
            rounded2=new RoundedCornersTransformation(topRightRadius,0, RoundedCornersTransformation.CornerType.TOP_RIGHT);
        }else {
            rounded2=new RoundedCornersTransformation(0,0);
        }
        if (bottomLeftRadius>0){
            rounded3=new RoundedCornersTransformation(bottomLeftRadius,0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT);
        }else {
            rounded3=new RoundedCornersTransformation(0,0);
        }
        if (bottomRightRadius>0){
            rounded4=new RoundedCornersTransformation(bottomRightRadius,0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT);
        }else {
            rounded4=new RoundedCornersTransformation(0,0);
        }

        MultiTransformation<Bitmap> multiTransformation=new MultiTransformation<>(rounded1,rounded2,rounded3,rounded4);
        return new RequestOptions().transform(multiTransformation);
    }

    //判断是否符合加载
    public boolean isObjLoadImage(Object object){
        if (object instanceof String) {
            return true;
        }
        if (object instanceof Bitmap) {
            return true;
        }
        if (object instanceof Drawable) {
            return true;
        }
        return false;
    }
}
