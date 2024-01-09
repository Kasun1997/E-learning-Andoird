package com.crystalmartin.crystalmartinelearning.ApiSegment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_Client {

    public static Gson gson = new GsonBuilder().setLenient().create();
  //  public static OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();



    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant_BaseUrl.CRYSTALMARTIN_DNS)
          //  .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public static Retrofit getRetrofitInstance(){
        return retrofit;
    }
}
