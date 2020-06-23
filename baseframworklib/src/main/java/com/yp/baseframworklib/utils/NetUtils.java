package com.yp.baseframworklib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.yp.baseframworklib.comment.CoreCode;
import com.yp.baseframworklib.comment.Global;

import java.util.Map;


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


    public static boolean isUrl(String url) {
        if (isHttp(url)) return true;
        return false;
    }

    public static String parseGetUrl(String url, Map<String, String> map) {
        StringBuffer sb = new StringBuffer(url);
        for (String key : map.keySet()) {
            sb.append("&").append(key).append("=").append(map.get(key));
        }
        Log.w("parse: " ,  sb.toString().replaceFirst("&", "?"));
        return sb.toString().replaceFirst("&", "?");
    }

    public static String formatUrl(String url, Object... str) {
        return String.format(url, str);
    }

    public static String cleanNet(String str) {
//		return str.replace("http://", "头").replace("t.cn", "短").replace("/", "斜").replace("\\", "反斜").replace(":", "冒").replace("\'", "单").replace("\"", "").replaceAll("\\d+", "数").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
        return str.replace("http://", "").replace("www.", "").replace(".com", "").replace(".cn", "").replace("/", "").replace("\\", "").replace(":", "").replace("\'", "").replace("\"", "").replaceAll("\\d+", "").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
    }

}
