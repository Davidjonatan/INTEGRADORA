package com.example.integradora.views.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.integradora.views.models.ComederoResponse;
import com.example.integradora.views.models.CrearComederoRequest;
import com.example.integradora.views.models.CrearMascotaRequest;
import com.example.integradora.views.repository.ComederoRepository;

import java.util.Map;

public class ComederoViewModel extends ViewModel {
    private final ComederoRepository repository;
    private MutableLiveData<ComederoResponse> comederoResponse;

    public ComederoViewModel() {
        repository = ComederoRepository.getInstance();
    }

    public LiveData<ComederoResponse> getComederos(String token) {
        if (comederoResponse == null) {
            comederoResponse = repository.getComederos(token);
        }
        return comederoResponse;
    }

    public void crearComedero(String token, CrearComederoRequest mascota, MutableLiveData<Map<String, String>> errores, MutableLiveData<Boolean> exito) {
        repository.crearComedero(token, mascota, errores, exito);
    }

    public MutableLiveData<Boolean> eliminarComedero(String token, int id) {
        MutableLiveData<Boolean> exito = new MutableLiveData<>();
        repository.eliminarComedero(token, id, exito);
        return exito;
    }
}
