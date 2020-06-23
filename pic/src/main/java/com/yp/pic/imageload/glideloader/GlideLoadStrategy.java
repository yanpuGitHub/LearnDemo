package com.yp.pic.imageload.glideloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.yp.pic.imageload.IImageLoadCallback;
import com.yp.pic.imageload.IImageLoadStrategy;
import com.yp.pic.imageload.ImageLoadOptions;
import com.yp.pic.imageload.Utils;
import com.yp.pic.imageload.blur.BlurTransformation;

import java.io.File;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


/**
 * Created by Administrator on 2017/3/21 0021.
 */

public class GlideLoadStrategy implements IImageLoadStrategy {

    private static final String TAG = GlideLoadStrategy.class.getSimpleName();
    
    public static String CACHE_DISK_PATH;
    private Context mContext;
    
    @Override
    public void download(@NonNull ImageLoadOptions options) {
        final String url = (String) options.mLoad;
        final ImageLoadOptions.ImageSize size = options.mImageSize;
        final IImageLoadCallback callback = options.mCallback;
        getRequestManager(options.mContext).downloadOnly().load(url).into(new DownloadTarget(url, size, callback));
    }
    
    @Override
    public void loadImage(@NonNull ImageLoadOptions options) {
        if(options.mAsGif) {
            loadGif(options);
            return;
        }
    
        showImageLast(createRequest(options), options);
    }
    
    private void loadGif(final ImageLoadOptions options) {
        GlideRequest<GifDrawable> request = getRequestManager(options.mContext).asGif()
                .placeholder(options.mHolderDrawableRes)
                .error(options.mErrorDrawableRes)
                .skipMemoryCache(options.mIsSkipMemoryCache)
                .load(options.mLoad);
                
        if(options.mViewContainer instanceof ImageView) {
            if(null != options.mCallback) {
                request = request.listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                        options.mCallback.onLoadFailed();
                        return false;
                    }
        
                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                        options.mCallback.onLoadCompleted(resource);
                        return false;
                    }
                });
            }
            request.into((ImageView) options.mViewContainer);
        } else {
            request.into(new SimpleTarget<GifDrawable>() {
                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    if(null != options.mViewContainer && null != errorDrawable) {
                        options.mViewContainer.setBackgroundDrawable(errorDrawable);
                    }
                    if(null != options.mCallback) {
                        options.mCallback.onLoadFailed();
                    }
                }
    
                @Override
                public void onResourceReady(GifDrawable resource, Transition<? super GifDrawable> transition) {
                    if(null != options.mViewContainer && null != resource) {
                        options.mViewContainer.setBackgroundDrawable(resource);
                        resource.start();
                    }
                    if(null != options.mCallback) {
                        options.mCallback.onLoadCompleted(resource);
                    }
                }
            });
        }
    }
    
    @Override
    public long getDiskCacheSize() {
        long size = 0L;
        if(TextUtils.isEmpty(CACHE_DISK_PATH)) {
            return size;
        } else {
            File cacheDir = new File(CACHE_DISK_PATH);
            if(cacheDir.exists()) {
                File[] files = cacheDir.listFiles();
                if(files != null) {
                    for(File file : files) {
                        size += file.length();
                    }
                }
            }
        
            return size;
        }
    }
    
    @Override
    public void deleteImgFromDiskCache(String url) {
        if(DefaultGlideModule.cache != null && url != null) {
            DefaultGlideModule.cache.delete(new ObjectKey(url));
        }
    }
    
    @Override
    public File getImgFileFromDiskCache(String url) {
        return DefaultGlideModule.cache != null && url != null ? DefaultGlideModule.cache.get(new ObjectKey(url)) : null;
    }
    
    @Override
    public boolean clearDiskCache() {
        // 理磁盘缓存 需要在子线程中执行
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlideApp.get(mContext).clearDiskCache();
                    }
                }).start();
            } else {
                GlideApp.get(mContext).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public void clearMemory() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            GlideApp.get(mContext).clearMemory();
        }
    }
    
    @Override
    public void pause(Object context) {
        getRequestManager(context).pauseRequests();
    }
    
    @Override
    public void resume(Object context) {
        getRequestManager(context).resumeRequests();
    }
    
    @Override
    public void init(Context context, Config config) {
        mContext = context.getApplicationContext();
        if(null != config) {
            CACHE_DISK_PATH = config.cacheDiskPath;
            DefaultGlideModule.headers = config.headers;
        }
        
        if(TextUtils.isEmpty(CACHE_DISK_PATH)) {
            // /data/data/你的应用包名/cache/
            // SDCard/Android/data/你的应用包名/cache/
            CACHE_DISK_PATH = Utils.getDiskCachePath(mContext) + "/image_manager_disk_cache";
        }
    }
    
    public GlideRequest<Bitmap> createRequest(ImageLoadOptions options) {
        final GlideRequests manager = getRequestManager(options.mContext);
        //装载参数
        return loadParams(manager.asBitmap(), options);
    }
    
    private GlideRequests getRequestManager(Object context) {
        if(null != context) {
            if(context instanceof FragmentActivity) {
                return GlideApp.with((FragmentActivity) context);
            }
            if(context instanceof Activity) {
                return GlideApp.with((Activity) context);
            }
            if(context instanceof Fragment) {
                return GlideApp.with((Fragment) context);
            }
            if(context instanceof android.app.Fragment) {
                return GlideApp.with((android.app.Fragment) context);
            }
            if(context instanceof View) {
                return GlideApp.with((View)context);
            }
        }
        return GlideApp.with(mContext);
    }
    
    //本地缓存策略
    private GlideRequest cacheStrategy(GlideRequest<Bitmap> builder, ImageLoadOptions.DiskCacheStrategy strategy) {
        switch (strategy) {
            case NONE:
                return builder.diskCacheStrategy(DiskCacheStrategy.NONE);
            case SOURCE:
                return builder.diskCacheStrategy(DiskCacheStrategy.DATA);
            case RESULT:
                return builder.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            case All:
            default:
                return builder.diskCacheStrategy(DiskCacheStrategy.ALL);
        }
    }
    
    private GlideRequest<Bitmap> loadParams(GlideRequest<Bitmap> request, final ImageLoadOptions options) {
        //取消动画效果
        request = request.dontAnimate();
        
        //裁剪圆
        if(options.mIsCropCircle) {
            request = request.circleCrop();
        }
    
        //高斯模糊和圆角处理
        BlurTransformation blurTrans = null;
        RoundedCorners roundedCornersTrans = null;
        if(options.mBlurImage) {
            blurTrans = new BlurTransformation((Context) options.mContext, options.mBlurRadius, options.mBlurSampling);
        }
        if(options.mIsRoundedCorners) {
            roundedCornersTrans = new RoundedCorners(options.mRoundedCornersRadius);
        }
        Transformation<Bitmap> applyTrans = null;
        if(null != blurTrans && null != roundedCornersTrans) {
            applyTrans = new MultiTransformation<>(blurTrans, roundedCornersTrans);
        } else if(null != blurTrans) {
            applyTrans = blurTrans;
        } else if(null != roundedCornersTrans) {
            applyTrans = roundedCornersTrans;
        }
        if(null != applyTrans) {
            request = request.apply(bitmapTransform(applyTrans));
        }
        
        //是否跳过内存缓存
        request = request.skipMemoryCache(options.mIsSkipMemoryCache);
    
        //图片大小
        final ImageLoadOptions.ImageSize size = options.mImageSize;
        if (size != null && size.width > 0 && size.height > 0) {
            request = request.override(size.width, size.height);
        }
    
        //占位图片m
        if (options.mHolderDrawableRes > 0) {
            request = request.placeholder(options.mHolderDrawableRes);
        } else if(null != options.mHolderDrawable) {
            request = request.placeholder(options.mHolderDrawable);
        }
        //错误图片
        if (options.mErrorDrawableRes > 0) {
            request = request.error(options.mErrorDrawableRes);
        } else if(null != options.mErrorDrawable) {
            request = request.error(options.mErrorDrawable);
        }
    
        //缓存策略
        request = cacheStrategy(request, options.mDiskCacheStrategy);
        
        if(options.mThumbnailScale > 0f && options.mThumbnailScale < 1f) {
            request = request.thumbnail(options.mThumbnailScale);
        } else if(!TextUtils.isEmpty(options.mThumbnailUrl)) {
            request = request.thumbnail(getRequestManager(options.mContext).asBitmap().load(options.mThumbnailUrl));
        }
        
        return request.load(options.mLoad);
    }
    
    private void showImageLast(GlideRequest<Bitmap> requestBuilder, ImageLoadOptions options) {
        final View imgView = options.mViewContainer;
        final IImageLoadCallback callback = options.mCallback;
        
        if(null != imgView) {
            if(imgView instanceof ImageView) {
                if(null != callback) {
                    requestBuilder = requestBuilder.listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            callback.onLoadFailed();
                            return false;
                        }
        
                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            callback.onLoadCompleted(resource);
                            return false;
                        }
                    });
                }
                requestBuilder.into((ImageView) imgView);
            } else {
                requestBuilder.into(new ViewRequestTarget<Bitmap>(imgView, callback));
            }
        } else {
            requestBuilder.into(new CallbackRequestTarget<Bitmap>(callback));
        }
    }
    
}
