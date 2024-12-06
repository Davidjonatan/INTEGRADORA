package com.example.integradora.views.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.integradora.R;
import com.example.integradora.views.response.ErrorResponse;
import com.example.integradora.views.viewModel.AuthViewModel;

public class SendEmail extends AppCompatActivity {
    Button btnSend;
    private EditText etEmail;
    private TextView tvEmailError, tvGeneralError, tvLogin, tvGeneralSuccess;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        etEmail = findViewById(R.id.etEmail);
        tvEmailError = findViewById(R.id.tvEmailError);
        tvGeneralError = findViewById(R.id.tvGeneralError);
        btnSend = findViewById(R.id.btnSend);
        tvLogin = findViewById(R.id.tvLogin);
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
        tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(SendEmail.this, Login.class);
            startActivity(intent);
            finish();
        });

        /*
        Intent intent = new Intent(SendEmail.this, verification_code.class);
        startActivity(intent);
        finish(); */

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearErrorMessages();
                authViewModel.clearErrors();

                String email = etEmail.getText().toString();


                if (email.isEmpty()) {
                    tvEmailError.setText("El campo de correo es obligatorio.");
                    tvEmailError.setVisibility(View.VISIBLE);
                    return;
                }

                authViewModel.getErrorResponseLiveData().observe(SendEmail.this, new Observer<ErrorResponse>() {
                    @Override
                    public void onChanged(ErrorResponse errorResponse) {
                        if (errorResponse != null) {
                            mostrarErroresDeValidacion(errorResponse);
                        }
                    }
                });

                authViewModel.getGeneralErrorLiveData().observe(SendEmail.this, new Observer<String>() {
                            @Override
                            public void onChanged(String errorMessage) {
                                if (errorMessage != null) {
                                    tvGeneralError.setText(errorMessage);
                                    tvGeneralError.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                authViewModel.reactivate(email).observe(SendEmail.this, new Observer<Void>() {
                    @Override
                    public void onChanged(Void aVoid) {
                        tvGeneralSuccess.setText("Correo enviado exitosamente");
                        tvGeneralSuccess.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(SendEmail.this, verification_code.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    };

        private void clearErrorMessages() {
            tvEmailError.setVisibility(View.GONE);
            tvGeneralError.setVisibility(View.GONE);
        }

        private void mostrarErroresDeValidacion(ErrorResponse errorResponse) {
            if (errorResponse.getValidator().getEmail() != null) {
                tvEmailError.setText(errorResponse.getValidator().getEmail().get(0));
                tvEmailError.setVisibility(View.VISIBLE);
            }
        }
    }
