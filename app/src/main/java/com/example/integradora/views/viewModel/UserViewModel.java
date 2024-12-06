package com.example.integradora.views.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.integradora.views.repository.UserRepository;
import com.example.integradora.views.response.UserResponse;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private LiveData<UserResponse> userLiveData;

    public UserViewModel() {
        // Constructor vacío
    }

    public void init(Context context) {
        if (userRepository == null) {
            userRepository = new UserRepository(context);
            userLiveData = userRepository.getUserData();
        }
    }

    public LiveData<UserResponse> getUserLiveData() {
        return userLiveData;
    }

}

