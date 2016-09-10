package com.move4mobile.hack.athon.teamblue;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.move4mobile.hack.athon.teamblue.network.ApiService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    public static ApiService getApiService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        ApiService service = retrofit.create(ApiService.class);
        return service;
    }

}
