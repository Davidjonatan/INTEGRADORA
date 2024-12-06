package com.example.integradora.views.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.integradora.views.api.apiComedero;
import com.example.integradora.views.response.UserResponse;
import com.example.integradora.views.network.RetrofitClient;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private apiComedero apiService;
    private SharedPreferences sharedPreferences;

    public UserRepository(Context context) {
        apiService = RetrofitClient.getInstance().create(apiComedero.class);
        sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
    }

    public LiveData<UserResponse> getUserData() {
        MutableLiveData<UserResponse> userData = new MutableLiveData<>();
        String token = sharedPreferences.getString("BearerToken", "");

        apiService.getUserData("Bearer " + token).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userData.setValue(response.body());
                } else {
                    userData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                userData.setValue(null);
            }
        });

        return userData;
    }
}

