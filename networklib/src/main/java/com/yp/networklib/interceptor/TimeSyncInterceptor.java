package com.yp.networklib.interceptor;

import com.yp.baseframworklib.utils.StringUtils;

import java.io.IOException;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @author senrsl
 * @ClassName: TimeSyncInterceptor
 * @Package: com.piaoshen.common.net.interceptor
 * @CreateTime: 2019/11/1 5:18 PM
 */
public class TimeSyncInterceptor implements Interceptor {

    public static long TIME_SYNC_DIFF = 0;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);

        String dateResp = originalResponse.header("date");
        if (originalResponse.isSuccessful() && !StringUtils.isEmpty(dateResp))
            TIME_SYNC_DIFF = System.currentTimeMillis() - new Date(dateResp).getTime();

        return originalResponse;
    }
}
