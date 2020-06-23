package com.yp.pic;


import com.yp.baseframworklib.utils.StringUtils;
import com.yp.pic.imageload.ImageLoadOptions;
import com.yp.pic.imageload.ImageLoadStrategyManager;

import java.util.Locale;


/**
 * @author : yanpu
 * @date : 2019-08-30
 * @description:
 */
public class ImageJyProxy {

    private static final int PROXY_URL_IMG_QUALITY = 75;

    private static ImageLoadOptions.ImageSize mProxySize = new ImageLoadOptions.ImageSize(0, 0);

    private static String PROXY_URL = "%1$s?x-oss-process=image/resize%2$s,w_%3$d,h_%4$d";

    public static String mProxyClip = "";

    public static String createProxyUrl(String url, ImageLoadOptions.ImageSize size, ImageProxy.SizeType sizeType, ImageProxy.ClipType clipType) {

        return createProxyUrl(url, size, sizeType, clipType, PROXY_URL_IMG_QUALITY);
    }

    //生成代理URL
    public static String createProxyUrl(String url, ImageLoadOptions.ImageSize size, ImageProxy.SizeType sizeType, ImageProxy.ClipType clipType, int imgQuality) {
        mProxySize.setSize(0, 0);

        if (null == sizeType) sizeType = ImageProxy.SizeType.ORIGINAL_SIZE;
        if (null == clipType) clipType = ImageProxy.ClipType.SCALE_TO_FIT;

        //此处以服务端可能拼接代理前缀但必不会拼接宽高等属性处理
        if (!StringUtils.isEmpty(url) && url.startsWith("http")) {
            /**
             * 裁剪模式
             */
            switch (clipType) {
                case FIX_WIDTH_OR_HEIGHT:
                    mProxyClip = ",m_lfit,limit_0";
                    break;
                case FIX_WIDTH_AND_HEIGHT:
                    mProxyClip = ",m_fill,limit_0";
                    break;
            }

            switch (sizeType) {
                case ORIGINAL_SIZE: //原始大小
                    mProxySize.setSize(0, 0);
                    return url;
                case CUSTOM_SIZE: //自定义大小
                    mProxySize.setSize(size.width, size.height);
                    break;
                case RATIO_1_1: //图片比例1:1
                    if (size.width <= 120 && size.width > 0) {
                        mProxySize.setSize(180, 180);
                    } else if (size.width <= 240 && size.width > 0) {
                        mProxySize.setSize(360, 360);
                    } else if (size.width > 440) {
                        mProxySize.setSize(660, 660);
                    } else {
                        mProxySize.setSize(540, 540);
                    }
                    break;
                case RATIO_2_3: //图片比例2：3
                    if (size.width >= 360) {
                        mProxySize.setSize(540, 810);
                    } else {
                        mProxySize.setSize(360, 540);
                    }
                    break;
                case RATIO_16_9: //图片比例16：9
                    if (size.width >= 240) {
                        mProxySize.setSize(640, 360);
                    } else {
                        mProxySize.setSize(320, 180);
                    }
                    break;
                default:
                    //默认自定义大小
                    mProxySize.setSize(size.width, size.height);
                    break;
            }

            //屏幕宽度和高度大于屏幕的0.6倍，就启动循序渐进加载效果
            boolean progressive = size.width >= ImageLoadStrategyManager.sScreenWidth * 0.6 || size.height >= ImageLoadStrategyManager.sScreenWidth * 0.6;

            if (size.width > mProxySize.width || size.height > mProxySize.height) {
                size.width = mProxySize.width;
                size.height = mProxySize.height;
            }

            return String.format(Locale.ENGLISH, PROXY_URL, url, mProxyClip, mProxySize.width,
                    mProxySize.height);
        }

        return url;
    }

}
