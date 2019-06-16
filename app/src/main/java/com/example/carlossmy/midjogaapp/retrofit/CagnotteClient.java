package com.example.carlossmy.midjogaapp.retrofit;

import com.example.carlossmy.midjogaapp.activities.MainActivity;
import com.example.carlossmy.midjogaapp.entities.Cagnotte;
import com.example.carlossmy.midjogaapp.utils.StaticFunctions;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CagnotteClient {
    private static final String BASE_URL = "http://"+StaticFunctions.IP_ADDRESS+":8080/api/cagnottes/";
    private static CagnotteClient mInstance;
    private Retrofit retrofit;
    private CagnotteClient(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static  synchronized CagnotteClient getInstance(){
        if (mInstance==null){
            mInstance = new CagnotteClient();
        }
        return mInstance;
    }
    public CagnotteApi getApi(){
        return retrofit.create(CagnotteApi.class);
    }
}
