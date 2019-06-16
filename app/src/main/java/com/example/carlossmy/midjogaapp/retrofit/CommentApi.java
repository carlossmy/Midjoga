package com.example.carlossmy.midjogaapp.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CommentApi {

    @FormUrlEncoded
    @POST("new")
    Call<ResponseBody> New(
            @Field("comment")String content,
            @Field("cagnotteId")int cagnotteId,
            @Field("user")int userId
    );
}
