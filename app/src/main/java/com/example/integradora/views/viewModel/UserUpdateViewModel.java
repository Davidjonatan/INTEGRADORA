package com.example.integradora.views.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.integradora.views.models.UpdateRequest;
import com.example.integradora.views.repository.UserUpdateRepository;
import com.example.integradora.views.response.UserUpdateResponse;

public class UserUpdateViewModel extends ViewModel {
    private final UserUpdateRepository repository;
    private final MutableLiveData<UserUpdateResponse> userUpdateResponse;

    public UserUpdateViewModel() {
        repository = UserUpdateRepository.getInstance();
        userUpdateResponse = new MutableLiveData<>();
    }

    public LiveData<UserUpdateResponse> getUserUpdateResponse() {
        return userUpdateResponse;
    }

    public void updateUser(String name, String oldPassword, String newPassword, String newPasswordConfirmation, String token) {
        UpdateRequest request = new UpdateRequest(name, oldPassword, newPassword, newPasswordConfirmation);
        LiveData<UserUpdateResponse> repositoryResponse = repository.updateUser(request, token);

        repositoryResponse.observeForever(new Observer<UserUpdateResponse>() {
            @Override
            public void onChanged(UserUpdateResponse response) {
                userUpdateResponse.setValue(response);
                repositoryResponse.removeObserver(this);
            }
        });
    }

}


