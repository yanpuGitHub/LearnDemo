package com.yp.networklib.service;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
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
}
