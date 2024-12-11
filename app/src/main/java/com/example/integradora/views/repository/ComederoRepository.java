package com.example.integradora.views.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.integradora.views.api.apiComedero;
import com.example.integradora.views.models.ComederoResponse;
import com.example.integradora.views.models.CrearComederoRequest;
import com.example.integradora.views.models.CrearMascotaRequest;
import com.example.integradora.views.network.RetrofitClient;
import com.example.integradora.views.response.CrearComederoResponse;
import com.example.integradora.views.response.CrearMascotaResponse;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                }else if(response.code() == 404){
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

    public void crearComedero(String token, CrearComederoRequest mascota, MutableLiveData<Map<String, String>> errores, MutableLiveData<Boolean> exito) {
        api.crearComedero("Bearer " + token, mascota).enqueue(new Callback<CrearComederoResponse>() {
            @Override
            public void onResponse(Call<CrearComederoResponse> call, Response<CrearComederoResponse> response) {
                if (response.isSuccessful() && response.code() == 201) {
                    exito.setValue(true);
                    errores.setValue(null);
                } else if (response.code() == 422 && response.errorBody() != null) {
                    try {
                        CrearMascotaResponse errorResponse = new Gson().fromJson(response.errorBody().string(), CrearMascotaResponse.class);
                        Map<String, String> errorMap = new HashMap<>();
                        for (Map.Entry<String, List<String>> entry : errorResponse.getValidator().entrySet()) {
                            errorMap.put(entry.getKey(), entry.getValue().get(0));
                        }
                        errores.setValue(errorMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                        errores.setValue(null);
                    }
                    exito.setValue(false);
                } else {
                    exito.setValue(false);
                    errores.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<CrearComederoResponse> call, Throwable t) {
                exito.setValue(false);
                errores.setValue(null);
            }
        });
    }

    public void eliminarComedero(String token, int id, MutableLiveData<Boolean> exito) {
        api.eliminarComedero("Bearer " + token, id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                    exito.setValue(true);
                    } else {
                        exito.setValue(false);
                    }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                exito.setValue(false);
            }
        });
    }
}
