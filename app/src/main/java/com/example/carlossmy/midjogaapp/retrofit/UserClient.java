package com.example.carlossmy.midjogaapp.retrofit;

import com.example.carlossmy.midjogaapp.activities.MainActivity;
import com.example.carlossmy.midjogaapp.utils.StaticFunctions;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserClient {
    private static final String BASE_URL = "http://"+StaticFunctions.IP_ADDRESS+":8080/api/users/";
    private static UserClient mInstance;
    private Retrofit retrofit;
    private UserClient(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static  synchronized UserClient getInstance(){
        if (mInstance==null){
            mInstance = new UserClient();
        }
        return mInstance;
    }
    public UserApi getApi(){
        return retrofit.create(UserApi.class);
    }
}
