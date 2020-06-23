package com.yp.pic.imageload.glideloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yp.pic.imageload.IImageLoadCallback;

/**
 * Created by ZhouSuQiang on 2018/2/4.
 */

public class ViewRequestTarget<Z> extends ViewTarget<View, Z> implements Transition.ViewAdapter {
    
    @Nullable
    private Animatable animatable;
    @Nullable
    private IImageLoadCallback mLoadCallback;
    
    public ViewRequestTarget(View view, IImageLoadCallback callback) {
        this(view, false, callback);
    }
    
    public ViewRequestTarget(View view, boolean waitForLayout, IImageLoadCallback callback) {
        super(view, waitForLayout);
        this.mLoadCallback = callback;
    }
    
    /**
     * Returns the current {@link Drawable} being displayed in the view
     * using {@link ImageView#getDrawable()}.
     */
    @Override
    @Nullable
    public Drawable getCurrentDrawable() {
        if(view instanceof ImageView) {
            return ((ImageView)view).getDrawable();
        } else {
            return view.getBackground();
        }
    }

    /**
     * Sets the given {@link Drawable} on the view using {@link
     * ImageView#setImageDrawable(Drawable)}.
     *
     * @param drawable {@inheritDoc}
     */
    @Override
    public void setDrawable(Drawable drawable) {
        if(view instanceof ImageView) {
            ((ImageView)view).setImageDrawable(drawable);
        } else {
            if(Build.VERSION.SDK_INT > 15) {
                view.setBackground(drawable);
            } else {
                view.setBackgroundDrawable(drawable);
            }
        }
    }

    /**
     * Sets the given {@link Drawable} on the view using {@link
     * ImageView#setImageDrawable(Drawable)}.
     *
     * @param placeholder {@inheritDoc}
     */
    @Override
    public void onLoadStarted(@Nullable Drawable placeholder) {
        super.onLoadStarted(placeholder);
        setResourceInternal(null);
        setDrawable(placeholder);
    }

    /**
     * Sets the given {@link Drawable} on the view using {@link
     * ImageView#setImageDrawable(Drawable)}.
     *
     * @param errorDrawable {@inheritDoc}
     */
    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        super.onLoadFailed(errorDrawable);
        setResourceInternal(null);
        setDrawable(errorDrawable);
        if(null != mLoadCallback) {
            mLoadCallback.onLoadFailed();
        }
    }

    /**
     * Sets the given {@link Drawable} on the view using {@link
     * ImageView#setImageDrawable(Drawable)}.
     *
     * @param placeholder {@inheritDoc}
     */
    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {
        super.onLoadCleared(placeholder);
        setResourceInternal(null);
        setDrawable(placeholder);
    }
    
    @Override
    public void onResourceReady(Z resource, @Nullable Transition<? super Z> transition) {
        if (transition == null || !transition.transition(resource, this)) {
            setResourceInternal(resource);
        } else {
            maybeUpdateAnimatable(resource);
        }
        if(null != mLoadCallback) {
            if(resource instanceof Bitmap) {
                mLoadCallback.onLoadCompleted((Bitmap) resource);
            } else {
                mLoadCallback.onLoadCompleted((Drawable) resource);
            }
        }
    }
    
    @Override
    public void onStart() {
        if (animatable != null) {
            animatable.start();
        }
    }
    
    @Override
    public void onStop() {
        if (animatable != null) {
            animatable.stop();
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoadCallback = null;
    }
    
    private void setResourceInternal(@Nullable Z resource) {
        // Order matters here. Set the resource first to make sure that the Drawable has a valid and
        // non-null Callback before starting it.
        setResource(resource);
        maybeUpdateAnimatable(resource);
    }
    
    private void maybeUpdateAnimatable(@Nullable Z resource) {
        if (resource instanceof Animatable) {
            animatable = (Animatable) resource;
            animatable.start();
        } else {
            animatable = null;
        }
    }
    
    protected void setResource(@Nullable Z resource) {
        if(null == resource) {
            return;
        }
        
        if(resource instanceof Bitmap) {
            if(view instanceof ImageView) {
                ((ImageView)view).setImageBitmap((Bitmap) resource);
            } else {
                if(Build.VERSION.SDK_INT > 15) {
                    view.setBackground(new BitmapDrawable(view.getResources(), (Bitmap) resource));
                } else {
                    view.setBackgroundDrawable(new BitmapDrawable(view.getResources(), (Bitmap) resource));
                }
            }
        } else {
            if(view instanceof ImageView) {
                ((ImageView)view).setImageDrawable((Drawable) resource);
            } else {
                if(Build.VERSION.SDK_INT > 15) {
                    view.setBackground((Drawable) resource);
                } else {
                    view.setBackgroundDrawable((Drawable) resource);
                }
            }
        }
    }
}
