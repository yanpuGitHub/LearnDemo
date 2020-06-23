package com.yp.networklib.service;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author : yanpu
 * @date : 2020-06-20
 * @description:
 */
public interface IService {

    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> map);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST
    Call<String> post(@HeaderMap Map<String, Object> mapHeader,  @Url String url, @FieldMap Map<String, Object> map);

    @POST
    Call<String> post(@HeaderMap Map<String, Object> mapHeader, @Url String url, @Body String str);

    @Streaming
    @GET
    Call<ResponseBody> getStream(@Url String url, @QueryMap Map<String, Object> map);

//    @POST
//    Call<String> post(@Url String url, @Body RequestBody requestBody);

    @Multipart
    @POST
    Call<String> postPart(@Url String url, @PartMap Map<String, RequestBody> map);

}
