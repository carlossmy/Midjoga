package com.example.carlossmy.midjogaapp.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PaiementApi {

    @FormUrlEncoded
    @POST("new")
    Call<ResponseBody> payment(
            @Field("libelle") String libelle,
            @Field("montant") double amount,
            @Field("cagnotteId") int cagnotteId,
            @Field("typePaiementId") int typePaiementId,
            @Field("userId") int userId
    );
}
