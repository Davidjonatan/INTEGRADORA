package com.example.integradora.views.api;

import com.example.integradora.views.models.LoginRequest;
import com.example.integradora.views.models.ReactivateRequest;
import com.example.integradora.views.models.SignUpRequest;
import com.example.integradora.views.response.LoginResponse;
import com.example.integradora.views.response.SignUpResponse;
import com.example.integradora.views.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface apiComedero {
    @Headers( "Content-Type: application/json")
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @Headers( "Content-Type: application/json")
    @POST("register")
    Call<SignUpResponse> register(@Body SignUpRequest signUpRequest);

    @Headers( "Content-Type: application/json")
    @POST("reactivate")
    Call<Void> reactivate(@Body ReactivateRequest reactivateRequest);

    @Headers( "Content-Type: application/json")
    @GET("user/me")
    Call<UserResponse> getUserData(@Header("Authorization") String token);

    @Headers( "Content-Type: application/json")
    @GET("user/logout")
    Call<Void> logoutUser(@Header("Authorization") String token);
}
