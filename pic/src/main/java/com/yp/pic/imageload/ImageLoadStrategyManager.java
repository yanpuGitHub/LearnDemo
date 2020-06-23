package com.yp.pic.imageload;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.WindowManager;


import com.yp.pic.imageload.glideloader.GlideLoadStrategy;

import java.io.File;


/**
 * Created by Administrator on 2017/3/22 0022.
 */
public class ImageLoadStrategyManager implements IImageLoadStrategy {
    private static final ImageLoadStrategyManager INSTANCE = new ImageLoadStrategyManager();
    public static int sScreenWidth = 0;
    private IImageLoadStrategy mImageLoadStrategy;
    private IImageLoadInterceptor mInterceptor;
    
    private ImageLoadStrategyManager() {
        //默认注册个图片加载策略，也可手动配置
        mImageLoadStrategy = new GlideLoadStrategy();
    }
    
    public static ImageLoadStrategyManager getInstance() {
        return INSTANCE;
    }
    
    public void setImageLoaderStrategy(IImageLoadStrategy strategy) {
        mImageLoadStrategy = strategy;
    }
    
    public IImageLoadStrategy getImageLoaderStrategy() {
        return mImageLoadStrategy;
    }
    
    public void registerOptionsInterceptor(IImageLoadInterceptor interceptor) {
        mInterceptor = interceptor;
    }
    
    public void unregisterOptionsInterceptor() {
        mInterceptor = null;
    }
    
    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
    
    @Override
    public void download(@NonNull ImageLoadOptions options) {
        if(null != mImageLoadStrategy) {
            if(null != mInterceptor) {
                options = mInterceptor.intercept(options);
            }
            mImageLoadStrategy.download(options);
        }
    }
    
    @Override
    public void loadImage(@NonNull ImageLoadOptions options) {
        if (mImageLoadStrategy != null) {
            if(null != mInterceptor) {
                options = mInterceptor.intercept(options);
            }
            mImageLoadStrategy.loadImage(options);
        }
    }
    
    @Override
    public boolean clearDiskCache() {
        if(null != mImageLoadStrategy) {
            return mImageLoadStrategy.clearDiskCache();
        }
        return false;
    }
    
    @Override
    public void clearMemory() {
        if(null != mImageLoadStrategy) {
            mImageLoadStrategy.clearMemory();
        }
    }
    
    @Override
    public long getDiskCacheSize() {
        if(null != mImageLoadStrategy) {
            return mImageLoadStrategy.getDiskCacheSize();
        }
        return 0;
    }
    
    @Override
    public void deleteImgFromDiskCache(String url) {
        if(null != mImageLoadStrategy) {
            mImageLoadStrategy.deleteImgFromDiskCache(url);
        }
    }
    
    @Override
    public File getImgFileFromDiskCache(String url) {
        if(null != mImageLoadStrategy) {
            mImageLoadStrategy.getImgFileFromDiskCache(url);
        }
        return null;
    }
    
    @Override
    public void pause(Object context) {
        if (mImageLoadStrategy != null) {
            mImageLoadStrategy.pause(context);
        }
    }
    
    @Override
    public void resume(Object context) {
        if (mImageLoadStrategy != null) {
            mImageLoadStrategy.resume(context);
        }
    }
    
    // 在application的oncreate中初始化
    @Override
    public void init(Context context, Config config) {
        sScreenWidth = getScreenWidth(context);
        if(null == mImageLoadStrategy) {
            mImageLoadStrategy = new GlideLoadStrategy();
        }
        mImageLoadStrategy.init(context, config);
    }
    
}
