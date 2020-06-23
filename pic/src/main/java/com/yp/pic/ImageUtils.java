package com.yp.pic;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.yp.pic.ImageProxy.ClipType;
import com.yp.pic.ImageProxy.SizeType;
import com.yp.pic.imageload.IImageLoadStrategy;
import com.yp.pic.imageload.ImageLoadOptions;
import com.yp.pic.imageload.ImageLoadStrategyManager;
import com.yp.pic.imageload.ImageLoader;

/**
 * Created by ZhouSuQiang on 2017/10/25.
 * 图片加载类
 */

public final class ImageUtils extends ImageLoader {
    protected static volatile ImageLoader sInstance;

    private SizeType mSizeType;
    private ClipType mClipType;

    private ImageUtils(Object context, SizeType sizeType, ClipType clipType) {
        super(context);
        this.mSizeType = sizeType;
        this.mClipType = clipType;
    }

    public static void init(Context context, IImageLoadStrategy.Config config) {
        if (null != config && !TextUtils.isEmpty(config.proxyUrlHost)) {
            ImageProxy.PROXY_URL_HOST = config.proxyUrlHost;
        }
        ImageLoadStrategyManager.getInstance().init(context, config);
    }

    protected static ImageLoadOptions.Builder getInstance(Object context, SizeType sizeType, ClipType clipType) {
        if (sInstance == null) {
            synchronized (ImageUtils.class) {
                if (sInstance == null) {
                    sInstance = new ImageUtils(context, sizeType, clipType);
                    return sInstance;
                }
            }
        }

        sInstance.mContext = context;
        ((ImageUtils) sInstance).mSizeType = sizeType;
        ((ImageUtils) sInstance).mClipType = clipType;
        return sInstance;
    }


    public static ImageLoadOptions.Builder with(Context context, SizeType sizeType, ClipType clipType) {
        return getInstance(context, sizeType, clipType);
    }

    public static ImageLoadOptions.Builder with_ClipType(Context context, SizeType sizeType) {
        return getInstance(context, sizeType, ClipType.FIX_WIDTH_AND_HEIGHT);
    }

    public static ImageLoadOptions.Builder with(Activity activity, SizeType sizeType, ClipType clipType) {
        return getInstance(activity, sizeType, clipType);
    }

    public static ImageLoadOptions.Builder with_clipType(Activity activity, SizeType sizeType) {
        return getInstance(activity, sizeType, ClipType.FIX_WIDTH_AND_HEIGHT);
    }

    public static ImageLoadOptions.Builder with(FragmentActivity activity, SizeType sizeType, ClipType clipType) {
        return getInstance(activity, sizeType, clipType);
    }

    public static ImageLoadOptions.Builder with_clipType(FragmentActivity activity, SizeType sizeType) {
        return getInstance(activity, sizeType, ClipType.FIX_WIDTH_AND_HEIGHT);
    }

    public static ImageLoadOptions.Builder with(Fragment v4Fragment, SizeType sizeType, ClipType clipType) {
        return getInstance(v4Fragment, sizeType, clipType);
    }

    public static ImageLoadOptions.Builder with_clipType(Fragment v4Fragment, SizeType sizeType) {
        return getInstance(v4Fragment, sizeType, ClipType.FIX_WIDTH_AND_HEIGHT);
    }

    public static ImageLoadOptions.Builder with(android.app.Fragment fragment, SizeType sizeType, ClipType clipType) {
        return getInstance(fragment, sizeType, clipType);
    }

    public static ImageLoadOptions.Builder with_clipType(android.app.Fragment fragment, SizeType sizeType) {
        return getInstance(fragment, sizeType, ClipType.FIX_WIDTH_AND_HEIGHT);
    }

    public static ImageLoadOptions.Builder with(SizeType sizeType, ClipType clipType) {
        return getInstance(null, sizeType, clipType);
    }

    public static ImageLoadOptions.Builder with_clipType(SizeType sizeType) {
        return getInstance(null, sizeType, ClipType.FIX_WIDTH_AND_HEIGHT);
    }

    public static ImageLoadOptions.Builder with(Context context) {
        return getInstance(context, null, null);
    }

    public static ImageLoadOptions.Builder with(Activity activity) {
        return getInstance(activity, null, null);
    }

    public static ImageLoadOptions.Builder with(FragmentActivity activity) {
        return getInstance(activity, null, null);
    }

    public static ImageLoadOptions.Builder with(Fragment v4Fragment) {
        return getInstance(v4Fragment, null, null);
    }

    public static ImageLoadOptions.Builder with(android.app.Fragment fragment) {
        return getInstance(fragment, null, null);
    }

    public static ImageLoadOptions.Builder with() {
        return getInstance(null, null, null);
    }

    @Override
    public void showload() {
//        if (mLoad instanceof ImageUrlBean) {
//            ImageUrlBean urlBean = (ImageUrlBean) this.mLoad;
            if ("jydata".equals(imageSource)) {
                mLoad = ImageJyProxy.createProxyUrl(imageUrl, mImageSize, mSizeType, mClipType);
//                Log.e("TAG", "showload: " + mLoad);
            } else {
                mLoad = ImageProxy.createProxyUrl(imageUrl, mImageSize, mSizeType, mClipType);
            }
        Log.e("TAG", "showload: " + mLoad);
//        }
        //
//        if (mLoad instanceof String) {
//            mLoad = ImageProxy.createProxyUrl((String) mLoad, mImageSize, mSizeType, mClipType);
//        }
        super.showload();
    }

    @Override
    public void download() {
        if (mLoad instanceof String) {
            mLoad = ImageProxy.createProxyUrl((String) mLoad, mImageSize, mSizeType, mClipType);
        }
        super.download();
    }

    @Override
    public void resetParams() {
        super.resetParams();
        mSizeType = null;
        mClipType = null;
    }

//    public String getUrlStr(String url, String source){
//        ImageUrlBean urlBean = (ImageUrlBean) this.mLoad;
//        if ("jydata".equals(urlBean.getSource())) {
//            mLoad = ImageJyProxy.createProxyUrl(urlBean.getUrl(), mImageSize, mSizeType, mClipType);
//            Log.e("TAG", "showload: " + mLoad);
//        } else {
//            mLoad = ImageProxy.createProxyUrl(urlBean.getUrl(), mImageSize, mSizeType, mClipType);
//        }
//        return (String) mLoad;
//    }

    @Override
    public String getUrlStr(String url, String source) {
        String urlStr;
        if ("jydata".equals(source)) {
            urlStr = ImageJyProxy.createProxyUrl(url, mImageSize, mSizeType, mClipType);
            Log.e("TAG", "showload: " + mLoad);
        } else {
            urlStr = ImageProxy.createProxyUrl(url, mImageSize, mSizeType, mClipType);
        }
        mLoad = urlStr;
        return urlStr;
    }
}
