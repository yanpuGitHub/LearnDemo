package com.yp.pic.imageload.glideloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yp.pic.imageload.IImageLoadCallback;

/**
 * Created by ZhouSuQiang on 2018/2/4.
 */

public class CallbackRequestTarget<Z> extends SimpleTarget<Z> {
    
    private IImageLoadCallback mLoadCallback;
    
    public CallbackRequestTarget(IImageLoadCallback callback) {
        mLoadCallback = callback;
    }
    
    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        super.onLoadFailed(errorDrawable);
        if(null != mLoadCallback) {
            mLoadCallback.onLoadFailed();
        }
    }
    
    @Override
    public void onResourceReady(Z resource, Transition<? super Z> transition) {
        if(null != mLoadCallback) {
            if (resource instanceof Bitmap) {
                mLoadCallback.onLoadCompleted((Bitmap) resource);
            } else {
                mLoadCallback.onLoadCompleted((Drawable) resource);
            }
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoadCallback = null;
    }
}
