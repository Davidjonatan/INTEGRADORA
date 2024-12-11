package com.example.integradora.views.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.integradora.views.api.apiComedero;
import com.example.integradora.views.models.CrearMascotaRequest;
import com.example.integradora.views.network.RetrofitClient;
import com.example.integradora.views.response.CrearMascotaResponse;
import com.example.integradora.views.response.MascotaResponse;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MascotaRepository {
    private static MascotaRepository instance;
    private final apiComedero api;

    private MascotaRepository() {
        api = RetrofitClient.getInstance().create(apiComedero.class);
    }

    public static MascotaRepository getInstance() {
        if (instance == null) {
            instance = new MascotaRepository();
        }
        return instance;
    }

    public MutableLiveData<MascotaResponse> getMascotas(String token) {
        MutableLiveData<MascotaResponse> mascotasData = new MutableLiveData<>();
        api.obtenerMascotas("Bearer " + token).enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mascotasData.setValue(response.body());
                    Log.e("MascotaRepository", "Error de agarrar body: " + response.code());
                } else if (response.code() == 404) {
                    mascotasData.setValue(response.body());
                    Log.e("MascotaRepository", "Error: " + response.code());
                } else {
                    mascotasData.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                mascotasData.setValue(null);
                Log.e("MascotaRepository", "Failure: " + t.getMessage());
            }
        });
        return mascotasData;
    }

    public void eliminarMascota(String token, int id, MutableLiveData<Boolean> resultado) {
        api.eliminarMascota("Bearer " + token, id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    resultado.setValue(true); // Eliminación exitosa
                } else {
                    resultado.setValue(false); // Error en la eliminación
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resultado.setValue(false); // Error de conexión
            }
        });
    }

    public void crearMascota(String token, CrearMascotaRequest request, MutableLiveData<Map<String, String>> errores, MutableLiveData<Boolean> exito) {
        api.crearMascota("Bearer " + token, request).enqueue(new Callback<CrearMascotaResponse>() {
            @Override
            public void onResponse(Call<CrearMascotaResponse> call, Response<CrearMascotaResponse> response) {
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
            public void onFailure(Call<CrearMascotaResponse> call, Throwable t) {
                exito.setValue(false);
                errores.setValue(null);
            }
        });
    }

}
