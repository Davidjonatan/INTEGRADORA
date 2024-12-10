package com.example.integradora.views.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.Context;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.integradora.R;
import com.example.integradora.views.viewModel.AuthViewModel;

public class Login extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private AuthViewModel authViewModel;
    private TextView tvRegister;
    private TextView tvActivate;

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

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                showErrorMessage("Por favor, completa los campos");
                return;
            }

            // Llamar al método login del ViewModel
            performLogin(email, password);
        });

        tvRegister.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
            finish();
        });

        tvActivate.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, verification_code.class);
            startActivity(intent);
            finish();
        });


    }

    private void performLogin(String email, String password) {
        authViewModel.login(email, password).observe(this, loginResponse -> {
            if (loginResponse != null && loginResponse.getToken() != null) {
                // Guardar el token en SharedPreferences
                saveToken(loginResponse.getToken());

                // Redirigir a MainActivity
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
}
