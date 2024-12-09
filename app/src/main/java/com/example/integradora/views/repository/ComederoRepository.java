package com.example.integradora.views.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.integradora.views.api.apiComedero;
import com.example.integradora.views.models.ComederoResponse;
import com.example.integradora.views.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComederoRepository {
    private static ComederoRepository instance;
    private final apiComedero api;

    private ComederoRepository() {
        api = RetrofitClient.getInstance().create(apiComedero.class);
    }

    public static ComederoRepository getInstance() {
        if (instance == null) {
            instance = new ComederoRepository();
        }
        return instance;
    }

    public MutableLiveData<ComederoResponse> getComederos(String token) {
        MutableLiveData<ComederoResponse> comederosData = new MutableLiveData<>();

        api.verComederos("Bearer " + token).enqueue(new Callback<ComederoResponse>() {
            @Override
            public void onResponse(Call<ComederoResponse> call, Response<ComederoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    comederosData.setValue(response.body());
                } else {
                    comederosData.setValue(null); // En caso de error
                }
            }

            @Override
            public void onFailure(Call<ComederoResponse> call, Throwable t) {
                comederosData.setValue(null);
            }
        });

        return comederosData;
    }
}
