package com.example.retrofitexample;

import com.example.retrofitexample.model.Post;
import com.example.retrofitexample.model.PostResponse;
import com.example.retrofitexample.model.PostsResponse;
import com.example.retrofitexample.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("public/v1/users")
    Call<UserResponse> doGetUsers();

    @GET("public/v1/posts")
    Call<PostsResponse> doGetPosts(@Query("page") String page, @Query("user_id") int userId);

    @GET("public/v1/users/{userId}/posts")
    Call<PostsResponse> doGetPostsByUser(@Path("userId") int userId, @Query("page") String page);

    //TODO: this as a first exercise?
    @POST("public/v1/posts")
    Call<PostResponse> createPost(@Query("access-token") String accessToken, @Body Post post);
}
