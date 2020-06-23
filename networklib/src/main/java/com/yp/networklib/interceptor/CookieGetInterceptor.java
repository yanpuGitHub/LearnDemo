package com.yp.networklib.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import com.yp.networklib.application.BaseApplication;
import com.yp.networklib.context.NetContext;

import java.io.IOException;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class CookieGetInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(NetContext.NAME_PREFERCE, Context.MODE_PRIVATE);
        Map<String, ?> mapCookies = sp.getAll();

        for (String key : mapCookies.keySet()) {
            Cookie cookie = Cookie.parse(chain.request().url(), String.valueOf(mapCookies.get(key)));
            if (null != cookie) builder.addHeader(NetContext.KEY_COOKIE, cookie.toString());
        }
        return chain.proceed(builder.build());
    }
}
