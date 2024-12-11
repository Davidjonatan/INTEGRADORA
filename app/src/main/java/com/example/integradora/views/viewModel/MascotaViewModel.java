package com.example.integradora.views.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.integradora.views.models.CrearMascotaRequest;
import com.example.integradora.views.repository.MascotaRepository;
import com.example.integradora.views.response.MascotaResponse;

import java.util.Map;

public class MascotaViewModel extends ViewModel {
    private MutableLiveData<MascotaResponse> mascotas;
    private final MascotaRepository repository;

    public MascotaViewModel() {
        repository = MascotaRepository.getInstance();
    }

    public MutableLiveData<MascotaResponse> getMascotas(String token) {
        if (mascotas == null) {
            mascotas = new MutableLiveData<>();
            loadMascotas(token);
        }
        return mascotas;
    }

    private void loadMascotas(String token) {
        mascotas = repository.getMascotas(token);
    }

    public MutableLiveData<Boolean> eliminarMascota(String token, int id) {
        MutableLiveData<Boolean> resultado = new MutableLiveData<>();
        repository.eliminarMascota(token, id, resultado);
        return resultado;
    }

    public void crearMascota(String token, CrearMascotaRequest request, MutableLiveData<Map<String, String>> errores, MutableLiveData<Boolean> exito) {
        repository.crearMascota(token, request, errores, exito);
    }

}
