package com.example.carlossmy.midjogaapp.retrofit;

import com.example.carlossmy.midjogaapp.utils.StaticFunctions;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentClient {
    private static final String BASE_URL = "http://"+StaticFunctions.IP_ADDRESS+":8080/api/comments/";
    private static CommentClient mInstance;
    private Retrofit retrofit;
    private CommentClient(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static  synchronized CommentClient getInstance(){
        if (mInstance==null){
            mInstance = new CommentClient();
        }
        return mInstance;
    }
    public CommentApi getApi(){
        return retrofit.create(CommentApi.class);
    }
}
