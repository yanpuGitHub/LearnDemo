package com.yp.networklib.interceptor;

import com.yp.networklib.context.NetContext;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static com.yp.networklib.context.NetContext.TIMEOUT_CONNECT;
import static com.yp.networklib.context.NetContext.TIMEOUT_READ;
import static com.yp.networklib.context.NetContext.TIMEOUT_WRITE;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class TokenClient {

    public OkHttpClient getTokenClient() {
        return customBuilder(getBuilder()).build();
    }

    public OkHttpClient.Builder customBuilder(OkHttpClient.Builder builder) {
        return builder;
    }

    private OkHttpClient.Builder getBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
                .addInterceptor(new HttpHeaderInterceptor())
                .addInterceptor(new LoggerInterceptor())
//                .addInterceptor(new TokenInterceptor())
//                .addInterceptor(new StatInterceptor())
//                .addInterceptor(new RobustCacheInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
//                .addInterceptor(new CookieGetInterceptor())
//                .addInterceptor(new CookieSetInterceptor())
//                .addInterceptor(new CacheInterceptor())
//                .cache(new Cache(new File(CandyContext.DIR_STORAGE, CandyContext.NAME_COMP), CandyContext.CACHE_MAX))
//                .authenticator(new TokenAuthenticator())
                ;
        if (!NetContext.isDebug) builder.proxy(Proxy.NO_PROXY);
        return builder;
    }
}
