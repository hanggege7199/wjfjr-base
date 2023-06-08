package com.tope.tope_base.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;

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

public abstract class BaseLoadImage implements ILoadInterFaceBase {


    @Override
    public RequestBuilder<Drawable> loadCircleImage(Context context, Object obj) {
        return Glide.with(context).load(obj).circleCrop();
    }


    @Override
    public RequestBuilder<Drawable> loadGrayscaleImage(Context context, Object obj, int radius) {
        return Glide.with(context).load(obj).apply(new RequestOptions().transform(new RoundedCorners(radius)));
    }

    @Override
    public RequestBuilder<Drawable> loadGrayscaleImage(Context context, Object obj, int radius, RoundedCornersTransformation.CornerType cornerType) {
        return Glide.with(context).load(obj).apply(initOptions(new RoundedCornersTransformation(radius,0,cornerType)));
    }

    @Override
    public RequestBuilder<Drawable> loadGrayscaleImage(Context context, Object obj, int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius) {
        return Glide.with(context).load(obj).apply(initOptions(topLeftRadius,topRightRadius,bottomLeftRadius,bottomRightRadius));
    }

    @Override
    public RequestBuilder<Drawable> loadCropTransformationImage(Context context, Object obj, int width, int height, CropTransformation.CropType cropType) {
        return Glide.with(context).load(obj).apply(initOptions(new CropTransformation(width,height, cropType)));
    }

    @Override
    public RequestBuilder<GifDrawable> loadGifImage(Context context, Object obj) {
        return Glide.with(context).asGif().load(obj) ;
    }

    @Override
    public RequestBuilder<Drawable> loadTransformImage(Context context, Object obj, int ambiguity) {
        return Glide.with(context).load(obj).apply(initOptions(new BlurTransformation(ambiguity)));
    }

    @Override
    public RequestBuilder<Drawable> loadThumbnailImage(Context context, Object obj, float sizeMultiplier) {
        float fp = sizeMultiplier;
        if (fp < 0.0f) {
            fp = 0f;
        }
        if (fp > 1.0f) {
            fp = 1f;
        }
        return Glide.with(context).load(obj).thumbnail(fp);
    }


    @Override
    public RequestBuilder<Drawable> loadBrightnessFilterTransformationImage(Context context, Object obj, float intensity) {
        float fp = intensity;
        if (fp < 0.0f) {
            fp = 0f;
        }
        if (fp > 1.0f) {
            fp = 1f;
        }
        return Glide.with(context).load(obj).apply(initOptions(new SepiaFilterTransformation(fp)));
    }

    @Override
    public RequestBuilder<Drawable> loadBrightnessFilterTransformationImage(Context context, String url, float brightness) {
        return Glide.with(context).load(url).apply(initOptions(new BrightnessFilterTransformation(brightness)));
    }

    @Override
    public RequestBuilder<Drawable> loadContrastFilterTransformationImage(Context context, Object obj, float contrast) {
        return  Glide.with(context).load(obj).apply(initOptions(new ContrastFilterTransformation(contrast)));
    }

    @Override
    public RequestBuilder<Drawable> loadPixelationFilterTransformationImage(Context context, Object obj, float pixel) {
        return  Glide.with(context).load(obj).apply(initOptions(new PixelationFilterTransformation(pixel)));
    }

    @Override
    public RequestBuilder<Drawable> loadSketchFilterTransformationImage(Context context, Object obj) {
        return Glide.with(context).load(obj).apply(initOptions(new SketchFilterTransformation()));
    }

    @Override
    public RequestBuilder<Drawable> loadTransformationSkipMemoryCacheImage(Context context, Object obj, RequestOptions options) {
        return Glide.with(context).load(obj).skipMemoryCache(true).apply(options);
    }

    @Override
    public RequestBuilder<Drawable> loadImageRequest(Context context, Object obj, RequestOptions options) {
        return Glide.with(context).load(obj).apply(options);
    }

    @Override
    public RequestBuilder<Drawable> loadImageNotAnimate(Context context, Object obj) {
        return Glide.with(context).load(obj).dontAnimate();
    }

    @Override
    public RequestBuilder<Bitmap> loadImageAsBitmap(Context context, Object obj) {
        return Glide.with(context).asBitmap().load(obj);
    }

    @Override
    public RequestBuilder<GifDrawable> loadGifImage(Context context, Object obj, float sizeMultiplier) {
        float fp = sizeMultiplier;
        if (fp < 0.0f) {
            fp = 0f;
        }
        if (fp > 1.0f) {
            fp = 1f;
        }
        return Glide.with(context).asGif().load(obj).thumbnail(fp);
    }


    @Override
    public RequestBuilder<Drawable> loadDiskCacheStrategyImage(Context context, Object obj, LoadImage.DiskCacheType diskCacheType) {
        if (diskCacheType == LoadImage.DiskCacheType.ALL) {
            return Glide.with(context).load(obj).diskCacheStrategy(DiskCacheStrategy.ALL);
        } else if (diskCacheType == LoadImage.DiskCacheType.RESOURCE) {
            return Glide.with(context).load(obj).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        } else if (diskCacheType == LoadImage.DiskCacheType.NONE) {
            return Glide.with(context).load(obj).diskCacheStrategy(DiskCacheStrategy.NONE);
        } else if (diskCacheType == LoadImage.DiskCacheType.AUTOMATIC) {
            return Glide.with(context).load(obj).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        } else if (diskCacheType == LoadImage.DiskCacheType.DATA) {
            return Glide.with(context).load(obj).diskCacheStrategy(DiskCacheStrategy.DATA);
        } else {
            return Glide.with(context).load(obj);
        }
    }


    //----------------------------------------
    private RequestOptions initOptions(BitmapTransformation transformation) {
        return new RequestOptions()
                .transform(transformation);
    }

    /**
     * 初始化添加圆角
     */
    @SuppressLint("CheckResult")
    private static RequestOptions initOptions(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius) {
        RoundedCornersTransformation rounded1;
        RoundedCornersTransformation rounded2;
        RoundedCornersTransformation rounded3;
        RoundedCornersTransformation rounded4;
        if (topLeftRadius > 0) {
            rounded1 = new RoundedCornersTransformation(topLeftRadius, 0, RoundedCornersTransformation.CornerType.TOP_LEFT);
        } else {
            rounded1 = new RoundedCornersTransformation(0, 0);
        }
        if (topRightRadius > 0) {
            rounded2 = new RoundedCornersTransformation(topRightRadius, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT);
        } else {
            rounded2 = new RoundedCornersTransformation(0, 0);
        }
        if (bottomLeftRadius > 0) {
            rounded3 = new RoundedCornersTransformation(bottomLeftRadius, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT);
        } else {
            rounded3 = new RoundedCornersTransformation(0, 0);
        }
        if (bottomRightRadius > 0) {
            rounded4 = new RoundedCornersTransformation(bottomRightRadius, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT);
        } else {
            rounded4 = new RoundedCornersTransformation(0, 0);
        }

        MultiTransformation<Bitmap> multiTransformation = new MultiTransformation<>(rounded1, rounded2, rounded3, rounded4);
        return new RequestOptions().transform(multiTransformation);
    }
}
