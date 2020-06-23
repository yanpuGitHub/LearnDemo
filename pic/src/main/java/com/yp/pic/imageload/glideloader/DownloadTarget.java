package com.yp.pic.imageload.glideloader;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.yp.pic.imageload.IImageLoadCallback;
import com.yp.pic.imageload.ImageLoadOptions;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by ZhouSuQiang on 2017/11/2.
 */

public class DownloadTarget extends SimpleTarget<File> {
    String mUrl;
    private WeakReference<IImageLoadCallback> mCallback;
    
    public DownloadTarget(String url, ImageLoadOptions.ImageSize size, IImageLoadCallback callback) {
        super((null == size || size.width <= 0 || size.height <= 0) ? Target.SIZE_ORIGINAL : size.width,
                (null == size || size.width <= 0 || size.height <= 0) ? Target.SIZE_ORIGINAL : size.height);
        this.mUrl = url;
        this.mCallback = new WeakReference<>(callback);
    }
    
    @Override
    public void removeCallback(SizeReadyCallback cb) {
    
    }
    
    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        if(null != mCallback && null != mCallback.get()) {
            mCallback.get().onDownLoadFailed(mUrl);
        }
    }
    
    @Override
    public void onResourceReady(File resource, Transition<? super File> transition) {
        if(null != mCallback && null != mCallback.get()) {
            mCallback.get().onDownloadCompleted(mUrl, resource);
        }
    }
    
    @Override
    public void onDestroy() {
        if(null != mCallback) {
            mCallback.clear();
            mCallback = null;
        }
    }
}
