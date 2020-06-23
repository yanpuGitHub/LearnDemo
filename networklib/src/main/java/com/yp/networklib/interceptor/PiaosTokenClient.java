package com.yp.networklib.interceptor;

import okhttp3.OkHttpClient;

/**
 * @author senrsl
 * @ClassName: PiaosTokenClient
 * @Package: com.piaoshen.common.net.client
 * @CreateTime: 2019/8/2 3:10 PM
 */
public class PiaosTokenClient extends TokenClient {

    @Override
    public OkHttpClient.Builder customBuilder(OkHttpClient.Builder builder) {
        return builder.addInterceptor(new PiaosHttpHeaderInterceptor())
                .addInterceptor(new CookieGetInterceptor())
                .addInterceptor(new PiaosCookieSetInterceptor())
                .addInterceptor(new PiaosTokenInterceptor())
                .addInterceptor(new TimeoutInterceptor())
                .addInterceptor(new TimeSyncInterceptor());
    }
}
