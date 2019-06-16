package com.example.carlossmy.midjogaapp.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PayGateApi {

    @FormUrlEncoded
    @GET("page")
    Call<ResponseBody> payment(
            @Query("token") String paygateToken,
            @Query("amount") double amount,
            @Query("description") String description,
            @Query("identifier") int id
    );
}
