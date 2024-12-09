package com.example.integradora.views.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.integradora.views.api.apiComedero;
import com.example.integradora.views.models.UpdateRequest;
import com.example.integradora.views.network.RetrofitClient;
import com.example.integradora.views.response.UserUpdateResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserUpdateRepository {
    private static UserUpdateRepository instance;
    private final apiComedero apiComedero;

    private UserUpdateRepository() {
        apiComedero = RetrofitClient.getInstance().create(apiComedero.class);
    }

    public static synchronized UserUpdateRepository getInstance() {
        if (instance == null) {
            instance = new UserUpdateRepository();
        }
        return instance;
    }

    public LiveData<UserUpdateResponse> updateUser(UpdateRequest request, String token) {
        final MutableLiveData<UserUpdateResponse> data = new MutableLiveData<>();
        apiComedero.updateUser(request, "Bearer " + token).enqueue(new Callback<UserUpdateResponse>() {
            @Override
            public void onResponse(Call<UserUpdateResponse> call, Response<UserUpdateResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else if (response.code() == 422 || response.code() == 400 || response.code() == 401) {
                    Log.d("UserUpdateRepository", "Código de estado: " + response.code());
                    Log.d("UserUpdateRepository", "Cuerpo de respuesta: " + response.body());
                    Log.d("UserUpdateRepository", "Error del cuerpo: " + response.errorBody());
                    try {
                        Gson gson = new Gson();
                        UserUpdateResponse errorResponse = gson.fromJson(response.errorBody().string(), UserUpdateResponse.class);
                        data.setValue(errorResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                        data.setValue(null);
                    }
                } else {

                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<UserUpdateResponse> call, Throwable t) {
                // Manejo de fallos
                data.setValue(null);
            }
        });
        return data;
    }
}


