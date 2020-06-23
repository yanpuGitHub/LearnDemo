package com.yp.pic.imageload;

/**
 * Created by ZhouSuQiang on 2017/10/25.
 * 图片加载类，为了方便使用进ImageLoadStrategyManager进行进一步的代理
 */

public class ImageLoader extends ImageLoadOptions.Builder {
    
    protected ImageLoader(Object context) {
        super(context);
    }
    
    @Override
    public void showload() {
        ImageLoadStrategyManager.getInstance().loadImage(this.build());
        resetParams();
    }
    
    @Override
    public void download() {
        ImageLoadStrategyManager.getInstance().download(this.build());
        resetParams();
    }

    @Override
    public String getUrlStr(String url, String source) {
        return null;
    }

    public void resetParams() {
        mContext = null;
        mHolderDrawableRes = -1;
        mViewContainer = null;
        mImageSize.setSize(0, 0);
        mErrorDrawableRes = -1;
        mAsGif = false;
        mIsSkipMemoryCache = false;
        mBlurImage = false;
        mBlurRadius = 25;
        mBlurSampling = 1;
        mIsCropCircle = false;
        mDiskCacheStrategy = ImageLoadOptions.DiskCacheStrategy.DEFAULT;
        mCallback = null;
        mRoundedCornersMargin = 0;
        mRoundedCornersRadius = 0;
        mIsRoundedCorners = false;
        mThumbnailScale = 0;
        mThumbnailUrl = null;
    }
}
