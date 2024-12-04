package com.example.integradora.views.api;

import com.example.integradora.views.models.LoginRequest;
import com.example.integradora.views.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface apiComedero {
    @Headers( "Content-Type: application/json")
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
