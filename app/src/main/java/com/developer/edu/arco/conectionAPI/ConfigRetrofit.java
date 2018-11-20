package com.developer.edu.arco.conectionAPI;


import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ConfigRetrofit {

    public static RetrofitService getService() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://192.168.10.110:8052")
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        return service;
    }


}
