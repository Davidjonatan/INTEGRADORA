package com.example.integradora.views.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.Context;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.integradora.R;
import com.example.integradora.views.response.ErrorResponse;
import com.example.integradora.views.response.LoginResponse;
import com.example.integradora.views.viewModel.AuthViewModel;

public class Login extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private AuthViewModel authViewModel;
    private TextView tvRegister;
    private TextView tvActivate;
    private TextView tvEmailError, tvPasswordError, tvGeneralError, tvGeneralSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Vincular vistas
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvActivate = findViewById(R.id.tvActivate);
        tvEmailError = findViewById(R.id.tvEmailError);
        tvPasswordError = findViewById(R.id.tvPasswordError);
        tvGeneralError = findViewById(R.id.tvGeneralError);
        tvGeneralSuccess = findViewById(R.id.tvGeneralSuccess);


        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    tvEmailError.setVisibility(View.GONE);
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    tvPasswordError.setVisibility(View.GONE);
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearErrorMessages();
                authViewModel.clearErrors(); // Limpiar errores anteriores

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if(email.isEmpty() && password.isEmpty()){
                    tvEmailError.setText("The email field is required.");
                    tvEmailError.setVisibility(View.VISIBLE);
                    tvPasswordError.setText("The password field is required.");
                    tvPasswordError.setVisibility(View.VISIBLE);
                    return;
                }

                if (email.isEmpty()) { tvEmailError.setText("The email field is required."); tvEmailError.setVisibility(View.VISIBLE); return; }
                if (password.isEmpty()) { tvPasswordError.setText("The email field required."); tvPasswordError.setVisibility(View.VISIBLE); return;}


                authViewModel.getErrorResponseLiveData().observe(Login.this, new Observer<ErrorResponse>() {
                    @Override
                    public void onChanged(ErrorResponse errorResponse) {
                        if (errorResponse != null) {
                            mostrarErroresDeValidacion(errorResponse);
                        }
                    }
                });

                authViewModel.login(email, password).observe(Login.this, new Observer<LoginResponse>() {
                    @Override
                    public void onChanged(LoginResponse loginResponse) {
                        if (loginResponse != null && loginResponse.getMessage() != null && loginResponse.getToken() != null ) {
                            tvGeneralSuccess.setText(loginResponse.getMessage());
                            tvGeneralSuccess.setVisibility(View.VISIBLE);
                            saveToken(loginResponse.getToken());


                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if(loginResponse != null && loginResponse.getMessage() != null) {
                            tvGeneralError.setText(loginResponse.getMessage());
                            tvGeneralError.setVisibility(View.VISIBLE);
                            // Aquí manejamos el caso donde no hay respuesta o es nula
                            ErrorResponse errorResponse = authViewModel.getErrorResponseLiveData().getValue();
                            if (errorResponse != null) {
                                tvGeneralError.setText(loginResponse.getMessage());
                                tvGeneralError.setVisibility(View.VISIBLE);
                                mostrarErroresDeValidacion(errorResponse);
                            }
                        }
                    }
                });
            }
        });

        tvRegister.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
            finish();
        });

        tvActivate.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, SendEmail.class);
            startActivity(intent);
            finish();
        });


    }

    private void performLogin(String email, String password) {
        authViewModel.login(email, password).observe(this, loginResponse -> {
            if (loginResponse != null && loginResponse.getToken() != null) {
                saveToken(loginResponse.getToken());
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if (loginResponse != null) {
                showErrorMessage(loginResponse.getMessage());
            } else {
                showErrorMessage("Error desconocido");
            }
        });
    }

    private void saveToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("BearerToken", token);
        editor.apply();
    }

    private void showErrorMessage(String message) {
        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
    }

    private void clearErrorMessages() {
        tvEmailError.setVisibility(View.GONE);
        tvPasswordError.setVisibility(View.GONE);
        tvGeneralError.setVisibility(View.GONE);
    }

    private void mostrarErroresDeValidacion(ErrorResponse errorResponse) {
        if (errorResponse.getValidator().getEmail() != null) {
            tvEmailError.setText(errorResponse.getValidator().getEmail().get(0));
            tvEmailError.setVisibility(View.VISIBLE);
        }
        if (errorResponse.getValidator().getPassword() != null) {
            tvPasswordError.setText(errorResponse.getValidator().getPassword().get(0));
            tvPasswordError.setVisibility(View.VISIBLE);
        }
    }


}
