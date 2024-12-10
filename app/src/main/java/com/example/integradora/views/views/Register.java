package com.example.integradora.views.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.integradora.R;

public class Register extends AppCompatActivity {
    private TextView tvLogin;
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

    }
}