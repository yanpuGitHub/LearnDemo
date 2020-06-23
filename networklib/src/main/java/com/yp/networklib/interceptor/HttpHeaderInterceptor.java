package com.yp.networklib.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class HttpHeaderInterceptor  implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
//        Request originalRequest = chain.request();
//        Request authorised = originalRequest.newBuilder()
////                .addHeader(CandyContext.KEY_R2_TOKEN, token)
////                .addHeader(CandyContext.KEY_R1_TOKEN_TING, tokenTing)
//                .addHeader(getClass().getName(),new Date().toString())
//                .build();
//        return chain.proceed(authorised);

        return chain.proceed(chain.request());
    }
}
