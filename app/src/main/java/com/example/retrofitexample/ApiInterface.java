package com.example.retrofitexample;

import com.example.retrofitexample.model.Activity;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/api/activity")
    Call<Activity> doGetActivity();
}
