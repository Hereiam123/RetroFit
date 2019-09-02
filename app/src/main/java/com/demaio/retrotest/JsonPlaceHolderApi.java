package com.demaio.retrotest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface JsonPlaceHolderApi {
    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") /*int user*/ /*Nullable wrapper*/ Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order);

    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String,String> parameter);


    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);
}
