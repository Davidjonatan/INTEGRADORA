package com.example.integradora.views.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.integradora.R;
import com.example.integradora.views.response.UserResponse;
import com.example.integradora.views.viewModel.UserUpdateViewModel;
import com.example.integradora.views.viewModel.UserViewModel;

import java.util.List;

public class updateData extends AppCompatActivity {
    private UserUpdateViewModel viewModel;
    private EditText etUpdateName, etOldPassword, etNewPassword, etNewPasswordConfirmation;
    private TextView tvNameError, tvOldPasswordError, tvNewPasswordError, tvNewPasswordConfirmationError, tvGeneralError;
    Button btnClose, btnSave;
    LinearLayout llFeeders;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        etUpdateName = findViewById(R.id.etUpdateName);
        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etNewPasswordConfirmation = findViewById(R.id.etNewPasswordConfirmation);
        tvNameError = findViewById(R.id.tvNameError);
        tvOldPasswordError = findViewById(R.id.tvOldPasswordError);
        tvNewPasswordError = findViewById(R.id.tvNewPasswordError);
        tvNewPasswordConfirmationError = findViewById(R.id.tvNewPasswordConfirmationError);
        tvGeneralError = findViewById(R.id.tvGeneralError);
        btnClose = findViewById(R.id.btnClose);
        btnSave = findViewById(R.id.btnSave);
        llFeeders = findViewById(R.id.llFeeders);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);

        userViewModel.getUserLiveData().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse user) {
                if (user != null && user.getName() != null && user.getEmail() != null) {
                    etUpdateName.setText( user.getName());

                }
            }
        });



        llFeeders.setOnClickListener(view -> {
            Intent intent = new Intent(updateData.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnClose.setOnClickListener(view -> {
            Intent intent = new Intent(updateData.this, Account.class);
            startActivity(intent);
            finish();
        });

        viewModel = new ViewModelProvider(this).get(UserUpdateViewModel.class);

        btnSave.setOnClickListener(v -> {
            String name = etUpdateName.getText().toString();
            String oldPassword = etOldPassword.getText().toString();
            String newPassword = etNewPassword.getText().toString();
            String newPasswordConfirmation = etNewPasswordConfirmation.getText().toString();
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("BearerToken", "");

            viewModel.updateUser(name, oldPassword, newPassword, newPasswordConfirmation, token);

            viewModel.getUserUpdateResponse().observe(this, response -> {
                tvNameError.setVisibility(View.GONE);
                tvOldPasswordError.setVisibility(View.GONE);
                tvNewPasswordError.setVisibility(View.GONE);
                tvNewPasswordConfirmationError.setVisibility(View.GONE);
                tvGeneralError.setVisibility(View.GONE);

                if (response != null) {
                    if (response.getValidator() != null) {
                        tvNameError.setText(getFirstError(response.getValidator().getName()));
                        tvOldPasswordError.setText(getFirstError(response.getValidator().getOld_password()));
                        tvNewPasswordError.setText(getFirstError(response.getValidator().getNew_password()));
                        tvNewPasswordConfirmationError.setText(getFirstError(response.getValidator().getNew_password_confirmation()));

                        tvNameError.setVisibility(response.getValidator().getName() != null ? View.VISIBLE : View.GONE);
                        tvOldPasswordError.setVisibility(response.getValidator().getOld_password() != null ? View.VISIBLE : View.GONE);
                        tvNewPasswordError.setVisibility(response.getValidator().getNew_password() != null ? View.VISIBLE : View.GONE);
                        tvNewPasswordConfirmationError.setVisibility(response.getValidator().getNew_password_confirmation() != null ? View.VISIBLE : View.GONE);
                    } else if (response.getMessage() != null) {
                        if (isSuccessfulMessage(response.getMessage())) {
                            Log.d("UserUpdate", "Actualización exitosa");
                        } else {
                            tvGeneralError.setText(response.getMessage());
                            tvGeneralError.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    tvGeneralError.setText("Fallo en la actualización. Intenta nuevamente.");
                    tvGeneralError.setVisibility(View.VISIBLE);
                }
            });

        });



    }
    private String getFirstError(List<String> errors) { if (errors != null && !errors.isEmpty()) { return errors.get(0); } return null; }
    private boolean isSuccessfulMessage(String message) {
        return message.toLowerCase().contains("correctamente") || message.toLowerCase().contains("actualización exitosa");
    }

}