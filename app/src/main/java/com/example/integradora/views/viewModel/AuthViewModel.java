package com.example.integradora.views.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.integradora.views.repository.AuthRepository;
import com.example.integradora.views.response.LoginResponse;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<LoginResponse> loginResponseLiveData = new MutableLiveData<>();

    public AuthViewModel() {
        authRepository = new AuthRepository();

    }

    public LiveData<LoginResponse> login(String email, String password) {
        authRepository.login(email, password).observeForever(new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                loginResponseLiveData.setValue(loginResponse);
            }
        });
        return loginResponseLiveData;
    }
    public LiveData<LoginResponse> getLoginResponseLiveData() { return loginResponseLiveData; }
}
