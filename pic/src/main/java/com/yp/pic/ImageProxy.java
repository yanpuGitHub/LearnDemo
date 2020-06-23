package com.yp.pic;


import com.yp.baseframworklib.utils.StringUtils;
import com.yp.pic.imageload.ImageLoadOptions;
import com.yp.pic.imageload.ImageLoadStrategyManager;
import com.yp.pic.imageload.WebPUtils;

import java.util.Locale;


/**
 * Created by ZhouSuQiang on 2018/1/26.
 */

public class ImageProxy {
    public static String PROXY_URL_HOST = "https://imgproxy.mtime.cn/get.ashx?uri=";
    private static int PROXY_URL_IMG_QUALITY = 75; //图片质量
    private static String PROXY_URL =
            "%1$s%2$s&width=%3$d&height=%4$d&quality=%5$d&clipType=%6$d&iswebp=%7$b&progressive=%8$b&v=2";
    private static ImageLoadOptions.ImageSize mProxySize = new ImageLoadOptions.ImageSize(0, 0);

    public static String createProxyUrl(String url, ImageLoadOptions.ImageSize size, SizeType sizeType, ClipType clipType) {
        return createProxyUrl(url, size, sizeType, clipType, PROXY_URL_IMG_QUALITY);
    }

    //生成代理URL
    public static String createProxyUrl(String url, ImageLoadOptions.ImageSize size, SizeType sizeType, ClipType clipType, int imgQuality) {
        mProxySize.setSize(0, 0);

        if (null == sizeType) sizeType = SizeType.ORIGINAL_SIZE;
        if (null == clipType) clipType = ClipType.SCALE_TO_FIT;

        //此处以服务端可能拼接代理前缀但必不会拼接宽高等属性处理
        if (!StringUtils.isEmpty(url) && url.startsWith("http")) {
            switch (sizeType) {
                case ORIGINAL_SIZE: //原始大小
                    mProxySize.setSize(0, 0);
                    break;
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

            return String.format(Locale.ENGLISH, PROXY_URL, hasProxy(url) ? "" : PROXY_URL_HOST, url,
                    mProxySize.width, mProxySize.height, imgQuality,
                    clipType.getValue(), WebPUtils.isWebPSupported(), progressive);
        }

        return url;
    }

    /**
     * 是否含有代理路径的url
     */
    private static boolean hasProxy(String url) {
        return url.startsWith(PROXY_URL_HOST);
    }

    //枚举定义：图片大小类型
    public enum SizeType {
        ORIGINAL_SIZE, //原始大小
        CUSTOM_SIZE, //自定义大小
        RATIO_1_1, //图片比例1:1，多用于用户头像、影人影片的图片列表
        RATIO_2_3, //图片比例2:3，多用于影人、影片的海报
        RATIO_16_9 //图片比例16:9，多用于视频预览图
    }

    //枚举定义：图片裁剪类型
    public enum ClipType {
        // 等比缩放
        SCALE_TO_FIT(0),
        // 定宽等比缩放，如果高大于需求则截取高为需求
        FIX_WIDTH_TRIM_HEIGHT(1),
        // 定宽等比缩放，不限制高
        FIX_WIDTH(2),
        // 固定宽或者固定高
        // 如果宽大于需求，高大于需求，宽>高，宽等于需求宽，高等比
        // 如果宽大于需求，高大于需求，高>宽，高等于需求高，宽等比
        // 如果宽大于需求，高小于需求，宽等于需求宽，高等比
        // 如果宽小于需求，高大于需求，高等于需求高，宽等比
        // 如果都小则不做处理
        FIX_WIDTH_OR_HEIGHT(3),
        // 固定宽和高，如果小于则放大，宽截取中间的需求宽，票神项目后台没有实现此模式，所以注释掉
        FIX_WIDTH_AND_HEIGHT(4);

        private int mValue;

        ClipType(int value) {
            this.mValue = value;
        }

        public int getValue() {
            return mValue;
        }
    }
}
