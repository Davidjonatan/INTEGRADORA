package com.example.integradora.views.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.integradora.R;
import com.example.integradora.views.models.SignUpRequest;
import com.example.integradora.views.response.ErrorResponse;
import com.example.integradora.views.response.SignUpResponse;
import com.example.integradora.views.viewModel.AuthViewModel;

public class Register extends AppCompatActivity {
    private TextView tvLogin;
    private EditText etEmail, etPassword, etName;
    private Button btnRegister;
    private AuthViewModel authViewModel;
    private TextView tvNameError, tvEmailError, tvPasswordError, tvGeneralSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(Register.this,Login.class);
            startActivity(intent);
            finish();
        });

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        btnRegister = findViewById(R.id.btnRegister);
        tvNameError = findViewById(R.id.tvNameError);
        tvEmailError = findViewById(R.id.tvEmailError);
        tvPasswordError = findViewById(R.id.tvPasswordError);
        tvGeneralSuccess = findViewById(R.id.tvGeneralSuccess);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    tvNameError.setVisibility(View.GONE);
                }
            }
        });

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


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearErrorMessages();
                authViewModel.clearErrors();
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                SignUpRequest signUpRequest = new SignUpRequest(name, email, password);

                authViewModel.getErrorResponseLiveData().observe(Register.this, new Observer<ErrorResponse>() {
                    @Override
                    public void onChanged(ErrorResponse errorResponse) {
                        if (errorResponse != null) {

                            mostrarErroresDeValidacion(errorResponse);
                        }
                    }
                });

                authViewModel.register(signUpRequest).observe(Register.this, new Observer<SignUpResponse>() {
                    @Override
                    public void onChanged(SignUpResponse signUpResponse) {
                        if (signUpResponse != null && signUpResponse.getMessage() != null) {
                            tvGeneralSuccess.setText("Registro exitoso");
                            tvGeneralSuccess.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(Register.this, verification_code.class);
                            startActivity(intent);
                            finish();
                        } else {
                            mostrarErroresDeValidacion(authViewModel.getErrorResponseLiveData().getValue());
                        }
                    }
                });
            }
        });

    }



    private void clearErrorMessages() {
        tvNameError.setVisibility(View.GONE);
        tvEmailError.setVisibility(View.GONE);
        tvPasswordError.setVisibility(View.GONE);
    }

    private void mostrarErroresDeValidacion(ErrorResponse errorResponse) {
        if (errorResponse.getValidator().getName() != null) {
            tvNameError.setText(errorResponse.getValidator().getName().get(0));
            tvNameError.setVisibility(View.VISIBLE);
        }
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