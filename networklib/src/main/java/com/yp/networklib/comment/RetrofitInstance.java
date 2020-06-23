package com.yp.networklib.comment;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author : yanpu
 * @date : 2020-06-20
 * @description:
 */
public class RetrofitInstance {

    Retrofit retrofit;

    public RetrofitInstance() {

    }

    private static final RetrofitInstance instance = new RetrofitInstance();

    public static RetrofitInstance getInstance() {
        return instance;
    }

    public void init(String baseUrl, OkHttpClient client) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
