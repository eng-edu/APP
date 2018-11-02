package com.developer.edu.arco.conectionAPI;


import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ConfigRetrofit {

    public static RetrofitService getService() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://191.252.193.192:8052")
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        return service;
    }


}
