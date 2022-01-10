package com.example.retrofitexample;

import com.example.retrofitexample.model.PostResponse;
import com.example.retrofitexample.model.User;
import com.example.retrofitexample.model.UserResponse;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("public/v1/users")
    Call<UserResponse> doGetUsers();

    @GET("public/v1/posts")
    Call<PostResponse> doGetPosts(@Query("page") String page, @Query("user_id") String userId);
}
