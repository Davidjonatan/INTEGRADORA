package com.example.integradora.views.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.integradora.R;

public class verification_code extends AppCompatActivity {
    private Button btnLogin;
    private TextView tvResend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verification_code);

        btnLogin = findViewById(R.id.btnLogin);
        tvResend = findViewById(R.id.tvResend);

        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(verification_code.this,Login.class);
            startActivity(intent);
            finish();
        });

        tvResend.setOnClickListener(view -> {
            Intent intent = new Intent(verification_code.this,verification_code.class);
            startActivity(intent);
            finish();
        });
    }
}