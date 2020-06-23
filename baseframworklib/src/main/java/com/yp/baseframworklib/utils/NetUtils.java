package com.yp.baseframworklib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yp.baseframworklib.comment.CoreCode;
import com.yp.baseframworklib.comment.Global;


/**
 * @author : yanpu
 * @date : 2020-06-20
 * @description:
 */
public class NetUtils {

    public static NetworkInfo getNetworkInfo(Context context) {
        if (null != context) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return mConnectivityManager.getActiveNetworkInfo();
        }
        return null;
    }

    /**
     * 是否存在可用连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        if (null != networkInfo) return networkInfo.isConnected();
        return false;
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static int getNetworkType(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        if (null != networkInfo) return networkInfo.getType();
        return CoreCode.DEFAULT;
    }

    /**
     * 是否是WiFi
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        return getNetworkType(context) == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isHttps(String url){
        if (null != url && url.startsWith(Global.TRANS_HEAD_HTTPS)) return true;
        return false;
    }

    public static boolean isHttp(String url){
        if (null != url && url.startsWith(Global.TRANS_HEAD_HTTP)) return true;
        return false;
    }
}
