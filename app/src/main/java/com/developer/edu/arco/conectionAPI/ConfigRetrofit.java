package com.developer.edu.arco.conectionAPI;


import java.net.URI;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ConfigRetrofit {

   public static String URL_BASE = "http://191.252.193.192:8052";

    public static RetrofitService getService() {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(URL_BASE)
                .client(okHttpClient)
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        return service;
    }


}
