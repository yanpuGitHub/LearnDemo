package com.yp.networklib.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import com.yp.baseframworklib.utils.StringUtils;
import com.yp.networklib.application.BaseApplication;
import com.yp.networklib.context.NetContext;
import com.yp.baseframworklib.log.Logger;

import java.io.IOException;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.yp.networklib.context.NetContext.HEADER_COOKIE_SET;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class CookieSetInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);

        if (!originalResponse.headers(HEADER_COOKIE_SET).isEmpty()) {
            List<Cookie> listCookie = Cookie.parseAll(request.url(), originalResponse.headers());

            Logger.w(getClass().getSimpleName(), listCookie);

            for (Cookie cookie : listCookie) {
                if (null == cookie) continue;
                SharedPreferences.Editor editor = BaseApplication.getContext().getSharedPreferences(NetContext.NAME_PREFERCE, Context.MODE_PRIVATE).edit();
                Logger.w(getClass().getSimpleName(), cookie, cookie.domain(), cookie.name(), cookie.path(), cookie.value());
                editor.putString(StringUtils.sub(cookie.domain(), "|", cookie.name()), cookie.toString());
                editor.apply();
            }
        }

        return originalResponse;
    }

}
