package com.example.carlossmy.midjogaapp.retrofit;

import com.example.carlossmy.midjogaapp.entities.CustomDate;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserApi {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody>login(
            @Field("email") String email,
            @Field("password")String password
    );
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody>register(
            @Field("nom_complet")String nom_complet,
            @Field("email") String email,
            @Field("password")String password,
            @Field("date_naissance")String date_naissance,
            @Field("phone")String phone
    );
    @FormUrlEncoded
    @POST("googleRegister")
    Call<ResponseBody>googleRegister(
            @Field("nom_complet")String nom_complet,
            @Field("email") String email,
            @Field("password")String password
    );

    @FormUrlEncoded
    @POST("verify")
    Call<ResponseBody>verify(
            @Field("email") String email
    );
    @GET("me")
    Call<ResponseBody>getUserProfile(@Header("Authorization")String token);
    @PUT("me")
    Call<ResponseBody>updateProfile(@Field("nomComplet")String nom,
                                    @Field("phone")String phone,
                                    @Field("dateNaissance")String date);
}
