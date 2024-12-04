package com.example.integradora.views.network;

import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;



public class RetrofitClient {
    private static Retrofit retrofit;

    private RetrofitClient(){};

    public static Retrofit getInstance() {
            if (retrofit == null) {
                synchronized (RetrofitClient.class) {
                    if (retrofit == null) {
                        retrofit = new Retrofit.Builder()
                                .baseUrl("http://3.142.255.52/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                    }
                }
            }
            return retrofit;
    }
}
