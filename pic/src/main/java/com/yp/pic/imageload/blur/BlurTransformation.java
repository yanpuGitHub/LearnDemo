package com.yp.pic.imageload.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.RSRuntimeException;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * @author : yanpu
 * @date : 2019-11-08
 * @description:
 */
public class BlurTransformation extends BitmapTransformation {

    private static int MAX_RADIUS = 25; // 设置模糊度(在0.0到25.0之间)，默认”25"
    private static int DEFAULT_DOWN_SAMPLING = 1; // 图片缩放比例

    private Context mContext;
    private BitmapPool mBitmapPool;
    private int mRadius;
    private int mSampling;
    

    public BlurTransformation(Context context) {
        this(context, Glide.get(context).getBitmapPool(), MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(Context context, BitmapPool pool) {
        this(context, pool, MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(Context context, BitmapPool pool, int mRadius) {
        this(context, pool, mRadius, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(Context context, int mRadius) {
        this(context, Glide.get(context).getBitmapPool(), mRadius, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(Context context, int mRadius, int mSampling) {
        this(context, Glide.get(context).getBitmapPool(), mRadius, mSampling);
    }

    public BlurTransformation(Context context, BitmapPool pool, int mRadius, int mSampling) {
        mContext = context.getApplicationContext();
        mBitmapPool = pool;
        this.mRadius = mRadius;
        this.mSampling = mSampling;
    }

//    public BlurTransformation() {
//        this(MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
//    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int scaledWidth = width / mSampling;
        int scaledHeight = height / mSampling;

        Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1 / (float) mSampling, 1 / (float) mSampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(toTransform, 0, 0, paint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                bitmap = RSBlur.blur(mContext, bitmap, mRadius);
            } catch (RSRuntimeException e) {
                bitmap = FastBlur.blur(bitmap, mRadius, true);
            }
        } else {
            bitmap = FastBlur.blur(bitmap, mRadius, true);
        }

        return bitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
//
//    @Override public String key() {
//        return "BlurTransformation(mRadius=" + mRadius + ", mSampling=" + mSampling + ")";
//    }

}
