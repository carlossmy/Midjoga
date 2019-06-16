package com.example.carlossmy.midjogaapp.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CagnotteApi {
    //Add a cagnotte
    @FormUrlEncoded
    @POST("create")
    Call<ResponseBody>create(
            @Field("title")String title,
            @Field("description") String desc,
            @Field("total")float total,
            @Field("amount")float amount,
            @Field("due_date")String date,
            @Field("localisation")String localisation,
            @Field("cagnotte_type")int typeId,
            @Field("user")int userId,
            @Field("visibility")boolean visibility
    );
    //get all the publics cagnottes
    @GET("all")
    Call<ResponseBody>getPublicCagnottes();
    //Get cagnotte from id
    @GET("get/{id}")
    Call<ResponseBody> getCagnotte(@Path("id") int id);
    //get Images urls
    @GET("getImg")
    Call<ResponseBody> getImage(@Query("cagnotteId") int id);
    //get typeCagnottes
    @GET("category")
    Call<ResponseBody> getTypes();
    //Sort Cagnottes by types
    @GET("category/{typeId}")
    Call<ResponseBody> sortByType(@Path("typeId")int typeId);
    //get the number of users that paid for the cagnottes
    @GET("countDonnors")
    Call<ResponseBody> getDonnors(@Query("cagnotteId") int id);
    //get all user that paid for the cagnottes
    @GET("getDonnors")
    Call<ResponseBody> getEachDonnors(@Query("cagnotteId") int id);
    //All the comments of the cagnotte
    @GET("comments")
    Call<ResponseBody> getComments(@Query("cagnotteId") int id);

}