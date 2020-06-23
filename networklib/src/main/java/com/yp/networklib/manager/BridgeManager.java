package com.yp.networklib.manager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yp.baseframworklib.utils.StringUtils;
import com.yp.networklib.bean.BaseDataBean;
import com.yp.networklib.log.Logger;
import com.yp.networklib.manager.callback.BaseCallback;
import com.yp.networklib.manager.callback.BaseRawCallback;
import com.yp.networklib.service.IService;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.yp.networklib.context.ypContext.DOMAIN;
import static com.yp.networklib.context.ypContext.KEY_DATA;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class BridgeManager extends BaseManager {

    protected <B extends BaseDataBean> void get(Object obj, String url, Map<String, Object> map, Callback<B> cb) {
        Call<String> call = getService(IService.class).get(url, map);
        call.enqueue(new BaseCallback<String, B>(cb));
    }


    protected <B extends BaseDataBean> void post(Object obj, String url, Map<String, Object> map, Callback<B> cb) {
        Call<String> call = getService(IService.class).post(url, map);
        call.enqueue(new BaseCallback<String, B>(cb));
    }

    protected <B extends BaseDataBean> void post(Object obj, String url, Map<String, Object> mapHeader, Map<String, Object> map, Callback<B> cb) {
        Call<String> call = getService(IService.class).post(mapHeader, url, map);
        call.enqueue(new BaseCallback<String, B>(cb));
    }

//    protected <B extends BaseDataBean> B getSync(Object obj, String url, Map<String, Object> map, Class<B> cls) {
//        Call<String> call = getService(IService.class).get(url, map);
//        try {
//            Response<String> response = call.execute();
//
//            Logger.w(getClass().getName(), response.body());
//            JsonElement je = new JsonParser().parse(response.body());
//            if (je.isJsonObject()) {
//
//                JsonObject jo = je.getAsJsonObject();
//
//                B bean = JsonInstance.getInstance().parse(jo.get(KEY_DATA).getAsString(), cls);
//
//                return bean;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    protected <B extends BaseDataBean> B postSync(Object obj, String url, Map<String, Object> map, Class<B> cls) {
        Call<String> call = getService(IService.class).post(url, map);
        try {
            Response<String> response = call.execute();
            return convert(response.body(), cls);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private <B extends BaseDataBean> B convert(String str, Class<B> cls) {
        Logger.w(getClass().getName(), str);
        JsonElement je = new JsonParser().parse(str);
        if (je.isJsonObject()) {
            JsonObject jo = je.getAsJsonObject();

            B bean = new Gson().fromJson(jo.get(KEY_DATA), cls);

            return bean;
        }
        return null;
    }


    protected void download(Object obj, String url, Map<String, Object> map, Callback<ResponseBody> cb) {
        Call<ResponseBody> call = getService(IService.class).getStream(url, map);
        call.enqueue(new BaseRawCallback<ResponseBody, BaseDataBean>(cb));
    }

//    protected <B extends BaseDataBean> void post(Object obj, String url, RequestBody requestBody, Callback<B> cb) {
////        Call<String> call = getService(IService.class).post(url, requestBody);
////        call.enqueue(new BaseCallback<String, B>(cb));
//    }

    protected <B extends BaseDataBean> void upload(Object obj, String url, Map<String, RequestBody> map, Callback<B> cb) {
        Call<String> call = getService(IService.class).postPart(url, map);
        call.enqueue(new BaseCallback<String, B>(cb));
    }

    protected RequestBody parseRequestBody(String text) {
        return RequestBody.create(MediaType.parse("text/plain"), text);
    }

    protected RequestBody parseRequestBody(File file) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), file);
    }

    public void cancel() {

    }

    protected String subHost(String api) {
        return StringUtils.sub(DOMAIN, api);
    }

    protected String subHost(String domain, String api) {
        return StringUtils.sub(domain, api);
    }

}
