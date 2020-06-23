package com.yp.networklib.interceptor;

import com.yp.baseframworklib.utils.StringUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.yp.networklib.code.BaseRetcode.DEFAULT;
import static com.yp.networklib.context.ypContext.KEY_TIMEOUT_CONNECT;
import static com.yp.networklib.context.ypContext.KEY_TIMEOUT_READ;
import static com.yp.networklib.context.ypContext.KEY_TIMEOUT_WRITE;

/**
 * @author senrsl
 * @ClassName: TimeoutInterceptor
 * @Package: com.piaoshen.common.net.interceptor
 * @CreateTime: 2019/10/30 3:25 PM
 */
public class TimeoutInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        int connectTimeout = chain.connectTimeoutMillis();
        int readTimeout = chain.readTimeoutMillis();
        int writeTimeout = chain.writeTimeoutMillis();

        String newConnect = request.header(KEY_TIMEOUT_CONNECT);
        String newRead = request.header(KEY_TIMEOUT_READ);
        String newWrite = request.header(KEY_TIMEOUT_WRITE);

        if (!StringUtils.isEmpty(newConnect)) {
            int conn = StringUtils.toInteger(newConnect);
            if (DEFAULT != conn) connectTimeout = conn;
        }
        if (!StringUtils.isEmpty(newRead)) {
            int read = Integer.valueOf(newRead);
            if (DEFAULT != read) readTimeout = read;
        }
        if (!StringUtils.isEmpty(newWrite)) {
            int write = Integer.valueOf(newWrite);
            if (DEFAULT != write) writeTimeout = write;
        }

        return chain
                .withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .withWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .proceed(request);
    }
}
