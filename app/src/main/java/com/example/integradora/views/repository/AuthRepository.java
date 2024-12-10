package com.example.integradora.views.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.integradora.views.api.apiComedero;
import com.example.integradora.views.models.LoginRequest;
import com.example.integradora.views.network.RetrofitClient;
import com.example.integradora.views.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final apiComedero authApi;

    public AuthRepository() {
        authApi = RetrofitClient.getInstance().create(apiComedero.class);
    }

    public LiveData<LoginResponse> login(String email, String password) {
        MutableLiveData<LoginResponse> loginResponseLiveData = new MutableLiveData<>();

        Log.d("AuthRepository", "Iniciando login con Retrofit");
        LoginRequest loginRequest = new LoginRequest(email, password);
        authApi.login(loginRequest).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("AuthRepository", "Entró al onResponse");

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("AuthRepository", "Se armó la respuesta");

                    loginResponseLiveData.postValue(response.body());
                } else {
                    // Manejar error del servidor
                    LoginResponse errorResponse = new LoginResponse();
                    if (response.errorBody() != null) {
                        errorResponse.setMessage("Error: " + response.code() + " - " + response.message());
                    } else { errorResponse.setMessage("Error desconocido"); }
                    loginResponseLiveData.postValue(errorResponse);
                    Log.d("AuthRepository", "No se armó la respuesta" + response.code() + " - " + response.message());

                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Manejar fallo de la red
                LoginResponse errorResponse = new LoginResponse();
                errorResponse.setMessage("Network Error: " + t.getMessage());
                loginResponseLiveData.postValue(errorResponse);
                Log.d("AuthRepository", "Error de red: " + t.getMessage());
            }
        });
        return loginResponseLiveData;
    }
}
