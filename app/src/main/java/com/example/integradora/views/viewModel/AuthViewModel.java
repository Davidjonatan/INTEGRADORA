package com.example.integradora.views.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.integradora.views.models.SignUpRequest;
import com.example.integradora.views.repository.AuthRepository;
import com.example.integradora.views.response.ErrorResponse;
import com.example.integradora.views.response.LoginResponse;
import com.example.integradora.views.response.SignUpResponse;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<LoginResponse> loginResponseLiveData = new MutableLiveData<>();
    private  MutableLiveData<SignUpResponse> signUpResponseLiveData = new MutableLiveData<>();


    public AuthViewModel() {
        authRepository = new AuthRepository();


    }
    public LiveData<SignUpResponse> register(SignUpRequest signUpRequest) {
        if (signUpResponseLiveData == null) {
            signUpResponseLiveData = new MutableLiveData<>();
        }
        signUpResponseLiveData = authRepository.register(signUpRequest);
        return signUpResponseLiveData;

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
    public LiveData<ErrorResponse> getErrorResponseLiveData() { return authRepository.getErrorResponseLiveData(); }
    public void clearErrors() { authRepository.getErrorResponseLiveData().setValue(null); }
}
