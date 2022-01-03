package com.example.retrofitexample;

import com.example.retrofitexample.model.Activity;
import com.example.retrofitexample.model.PostResponse;
import com.example.retrofitexample.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/api/activity")
    Call<Activity> doGetActivity();

    @GET("public/v1/users")
    Call<UserResponse> doGetUsers();

    @GET("public/v1/posts?")
    Call<PostResponse> doGetPosts(@Query("page") String page);
}
