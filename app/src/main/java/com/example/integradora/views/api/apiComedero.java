package com.example.integradora.views.api;

import com.example.integradora.views.models.ComederoResponse;
import com.example.integradora.views.models.CrearComederoRequest;
import com.example.integradora.views.models.CrearMascotaRequest;
import com.example.integradora.views.models.Estado;
import com.example.integradora.views.models.LoginRequest;
import com.example.integradora.views.models.Mascota;
import com.example.integradora.views.models.ReactivateRequest;
import com.example.integradora.views.models.SignUpRequest;
import com.example.integradora.views.models.UpdateRequest;
import com.example.integradora.views.response.CrearComederoResponse;
import com.example.integradora.views.response.CrearMascotaResponse;
import com.example.integradora.views.response.LoginResponse;
import com.example.integradora.views.response.MascotaResponse;
import com.example.integradora.views.response.SignUpResponse;
import com.example.integradora.views.response.UserResponse;
import com.example.integradora.views.response.UserUpdateResponse;
import com.example.integradora.views.views.CrearComedero;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @Headers( "Content-Type: application/json")
    @PUT("user/update")
    Call<UserUpdateResponse> updateUser(@Body UpdateRequest request, @Header("Authorization") String token);

    @Headers( "Content-Type: application/json")
    @GET("comederos")
    Call<ComederoResponse> verComederos(@Header("Authorization") String token);

    @Headers( "Content-Type: application/json")
    @GET("mascotas")
    Call<MascotaResponse> obtenerMascotas(@Header("Authorization") String token);

    @Headers( "Content-Type: application/json")
    @GET("comedero/{id}")
    Call<ComederoResponse> obtenerComedero(@Header("Authorization") String token, @Path("id") int id);

    @Headers("Content-Type: application/json")
    @DELETE("mascotas/eliminar/{id}")
    Call<Void> eliminarMascota(@Header("Authorization") String token, @Path("id") int id);

    @Headers("Content-Type: application/json")
    @DELETE("comedero/eliminar/{id}")
    Call<Void> eliminarComedero(@Header("Authorization") String token, @Path("id") int id);

    @Headers("Content-Type: application/json")
    @POST("mascotas/crear")
    Call<CrearMascotaResponse> crearMascota(@Header("Authorization") String token, @Body CrearMascotaRequest mascota);

    @Headers("Content-Type: application/json")
    @POST("comederos/crear")
    Call<CrearComederoResponse> crearComedero(@Header("Authorization") String token, @Body CrearComederoRequest mascota);

    @Headers("Content-Type: application/json")
    @GET("sync-comedero")
    Call<Void> syncComedero();

    @Headers("Content-Type: application/json")
    @POST("servo")
    Call<Void> activarServo(@Body Estado estado);


}
