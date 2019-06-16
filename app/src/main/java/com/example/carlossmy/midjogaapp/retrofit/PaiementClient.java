package com.example.carlossmy.midjogaapp.retrofit;

import com.example.carlossmy.midjogaapp.utils.StaticFunctions;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaiementClient {

    private static final String BASE_URL = "http://"+StaticFunctions.IP_ADDRESS+":8080/api/paiements/";
    private static PaiementClient mInstance;
    private Retrofit retrofit;
    private PaiementClient(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static  synchronized PaiementClient getInstance(){
        if (mInstance==null){
            mInstance = new PaiementClient();
        }
        return mInstance;
    }
    public PaiementApi getApi(){
        return retrofit.create(PaiementApi.class);
    }
}
