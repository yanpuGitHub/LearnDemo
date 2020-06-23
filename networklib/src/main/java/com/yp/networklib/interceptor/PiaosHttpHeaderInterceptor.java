package com.yp.networklib.interceptor;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @author senrsl
 * @ClassName: PiaosHttpHeaderInterceptor
 * @Package: com.piaoshen.common.net.interceptor
 * @CreateTime: 2019/8/2 3:12 PM
 */
public class PiaosHttpHeaderInterceptor extends HttpHeaderInterceptor {

    private final String HEADER_PLATFORM = "X-PS-Platform";
    private final String HEADER_TIMESTAMP = "X-PS-SendTS";
    private final String HEADER_VERSION = "X-PS-Version";
    private final String HEADER_PACKAGE_NAME = "X-PS-PackageName";
    private final String HEADER_CHECK = "X-PS-Check";
    private final String HEADER_UDID = "X-PS-UDID";
    private final String HEADER_TIMESTAMP_INSTALL = "X-PS-AppInstallTS";
    private final String HEADER_DEVICE = "X-PS-Device";
    private final String HEADER_CHANNEL = "X-PS-DownloadChannel";
    private final String HEADER_CID = "X-PS-CID";

    //    private final String KEY_CHECK = "388C4D156924C9C20E335A888CBBEBCBFB08C08A1A499415FD46D18DD706E866";
    private final String KEY_CHECK = "12345";

    //json header
    private final String HEADER_CONTENT_TYPE = "Content-Type";
    private final String HEADER_ACCEPT = "Accept";
    private final String HEADER_CONTENT_TYPE_JSON = "application/json";

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();

        long currentTime = System.currentTimeMillis();

        HttpUrl url = originalRequest.url();

//        //piaos通用header
        Request.Builder builder = originalRequest.newBuilder();
//                .addHeader(HEADER_PLATFORM, .PLATFROM)
//                .addHeader(HEADER_TIMESTAMP, String.valueOf(currentTime))
//                .addHeader(HEADER_VERSION, PiaosContext.VERSION_NAME)
//                .addHeader(HEADER_PACKAGE_NAME, PiaosContext.PACKAGE_NAME)
//                .addHeader(HEADER_CHECK, generateCheckCode(currentTime, url.toString(), getBody(originalRequest.body()), KEY_CHECK, PiaosContext.PLATFROM))
//                .addHeader(HEADER_UDID, PiaosContext.UDID)
//                .addHeader(HEADER_TIMESTAMP_INSTALL, String.valueOf(PiaosContext.TIME_INSTALL))
//                .addHeader(HEADER_DEVICE, Build.MODEL)
//                .addHeader(HEADER_CHANNEL, FlavorInfo.CHANNEL)
//                .addHeader(HEADER_CID, UUID.randomUUID().toString().replace("-", ""));
//

        //仅作用于logx TODO 挪到logx lib
//        if (url.host().startsWith(HOST_LOGX)) {
//            builder.addHeader("referer", "PiaoShen")
//                    .addHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_TYPE_JSON)
//                    .addHeader(HEADER_ACCEPT, HEADER_CONTENT_TYPE_JSON);
//        }

//        Logger.w(getClass().getName(), originalRequest, originalRequest.method(), originalRequest.headers(), originalRequest.body());
        return chain.proceed(builder.build());
//        return chain.proceed(originalRequest.Builder())
    }

    private String getBody(RequestBody body) {
        StringBuffer sb = new StringBuffer();
        if (null != body)
            if (body instanceof FormBody) {
                FormBody bs = (FormBody) body;
                int size = bs.size();
                for (int i = 0; i < size; i++) {
                    if (i > 0) sb.append("&");
                    sb.append(bs.encodedName(i)).append("=").append(bs.encodedValue(i));
                }
            }
        return sb.toString();
    }

}
