package com.example.integradora.views.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.integradora.views.api.apiComedero;

import com.example.integradora.R;
import com.example.integradora.views.models.Estado;
import com.example.integradora.views.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServirComida extends AppCompatActivity {
    private apiComedero apiComedero;
    private LinearLayout llFeeders, llProfile;
    private Button btnPequeña, btnMediana, btnGrande, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servir_comida);

        llFeeders = findViewById(R.id.llFeeders);
        llProfile = findViewById(R.id.llProfile);
        btnPequeña = findViewById(R.id.btnPequeña);
        btnMediana = findViewById(R.id.btnMediana);
        btnGrande = findViewById(R.id.btnGrande);
        btnBack = findViewById(R.id.btnBack);

        apiComedero = RetrofitClient.getInstance().create(apiComedero.class);

        llFeeders.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        llProfile.setOnClickListener(view -> {
            startActivity(new Intent(this, Account.class));
        });

        btnBack.setOnClickListener(view -> {
            finish();
        });

        btnPequeña.setOnClickListener(v -> enviarValue(1));
        btnMediana.setOnClickListener(v -> enviarValue(2));
        btnGrande.setOnClickListener(v -> enviarValue(3));

    }

    private void enviarValue(int estado) {
        Estado valueObject = new Estado(estado);

        apiComedero.activarServo(valueObject).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ServirComida.this, "Servo activado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ServirComida.this, "Error al activar el servo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ServirComida.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}