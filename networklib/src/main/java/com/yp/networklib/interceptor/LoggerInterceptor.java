package com.yp.networklib.interceptor;

import com.yp.baseframworklib.log.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class LoggerInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Logger.w(String.format("%s on %s%n%s", request.url(), chain.connection(), request.headers()));
        return chain.proceed(request);
    }
}
