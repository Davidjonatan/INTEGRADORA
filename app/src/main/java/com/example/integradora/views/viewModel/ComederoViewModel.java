package com.example.integradora.views.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.integradora.views.models.ComederoResponse;
import com.example.integradora.views.repository.ComederoRepository;

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
}
