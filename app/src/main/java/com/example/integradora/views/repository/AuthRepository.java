package com.example.integradora.views.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.integradora.views.api.apiComedero;
import com.example.integradora.views.models.LoginRequest;
import com.example.integradora.views.models.SignUpRequest;
import com.example.integradora.views.network.RetrofitClient;
import com.example.integradora.views.response.ErrorResponse;
import com.example.integradora.views.response.LoginResponse;
import com.example.integradora.views.response.SignUpResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final apiComedero authApi;
    private final MutableLiveData<ErrorResponse> errorResponseLiveData;

    public AuthRepository() {
        authApi = RetrofitClient.getInstance().create(apiComedero.class);
        errorResponseLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<SignUpResponse> register(SignUpRequest signUpRequest) {
        MutableLiveData<SignUpResponse> signUpResponseLiveData = new MutableLiveData<>();
        authApi.register(signUpRequest).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    signUpResponseLiveData.setValue(response.body());
                    errorResponseLiveData.setValue(null);
                } else {
                    try {
                        Gson gson = new Gson();
                        ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                        errorResponseLiveData.setValue(errorResponse);
                        signUpResponseLiveData.setValue(null);
                    }
                    catch (IOException e) {
                            errorResponseLiveData.setValue(null);
                            signUpResponseLiveData.setValue(null);
                        }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                signUpResponseLiveData.setValue(null);
                errorResponseLiveData.setValue(null);

            }
        });
        return signUpResponseLiveData;
    }

    public MutableLiveData<ErrorResponse> getErrorResponseLiveData() { return errorResponseLiveData; }


    public LiveData<LoginResponse> login(String email, String password) {
        MutableLiveData<LoginResponse> loginResponseLiveData = new MutableLiveData<>();

        LoginRequest loginRequest = new LoginRequest(email, password);
        authApi.login(loginRequest).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("AuthRepository", "Se armó la respuesta");
                    loginResponseLiveData.postValue(response.body());
                    errorResponseLiveData.setValue(null);
                } else {
                    if(response.code() == 422){
                        try {
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                            errorResponseLiveData.setValue(errorResponse);
                            loginResponseLiveData.postValue(null);
                        }catch (IOException e){
                            errorResponseLiveData.setValue(null);
                        }
                    } else {
                        LoginResponse errorResponse = new LoginResponse();
                        if (response.errorBody() != null && response.code() == 401) {
                            errorResponse.setMessage("Credenciales inválidas.");
                        } else if(response.errorBody() != null && response.code() == 403){
                            errorResponse.setMessage("Acceso denegado. Por favor, active su cuenta para obtener acceso.");
                        } else {
                            errorResponse.setMessage("Error desconocido");
                        }
                        loginResponseLiveData.postValue(errorResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Manejar fallo de la red
                LoginResponse errorResponse = new LoginResponse();
                errorResponse.setMessage("Network Error: " + t.getMessage());
                loginResponseLiveData.postValue(errorResponse);
                Log.d("AuthRepository", "Error de red: " + t.getMessage());
                errorResponseLiveData.setValue(null);
            }
        });
        return loginResponseLiveData;
    }
}
