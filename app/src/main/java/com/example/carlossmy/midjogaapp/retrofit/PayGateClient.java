package com.example.carlossmy.midjogaapp.retrofit;

import com.example.carlossmy.midjogaapp.utils.StaticFunctions;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayGateClient {
    private static final String BASE_URL = "https://paygateglobal.com/api/v1/";
    private static PayGateClient mInstance;
    private Retrofit retrofit;
    private PayGateClient(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static  synchronized PayGateClient getInstance(){
        if (mInstance==null){
            mInstance = new PayGateClient();
        }
        return mInstance;
    }
    public PayGateApi getApi(){
        return retrofit.create(PayGateApi.class);
    }
}
