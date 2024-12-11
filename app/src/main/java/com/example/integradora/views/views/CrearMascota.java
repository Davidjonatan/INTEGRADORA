package com.example.integradora.views.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.integradora.R;
import com.example.integradora.views.models.CrearMascotaRequest;
import com.example.integradora.views.viewModel.MascotaViewModel;

import java.util.Map;

public class CrearMascota extends AppCompatActivity {
    private LinearLayout llFeeders, llAccount;
    Button btnBack, btnSave;
    private MascotaViewModel mascotaViewModel;
    private TextView tvNameError, tvAnimalError;
    private EditText etName, etAnimal;
    private static final String SHARED_PREFS_NAME = "MyAppPrefs";
    private static final String TOKEN_KEY = "BearerToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_mascota);

        llFeeders = findViewById(R.id.llFeeders);
        llAccount = findViewById(R.id.llAccount);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        tvNameError = findViewById(R.id.tvNameError);
        tvAnimalError = findViewById(R.id.tvAnimalError);
        etName = findViewById(R.id.etName);
        etAnimal = findViewById(R.id.etAnimal);

        if (llFeeders != null) {
            llFeeders.setOnClickListener(view -> {
                Intent intent = new Intent(CrearMascota.this, MainActivity.class);
                startActivity(intent);
            });
        }

        if (llAccount != null) {
            llAccount.setOnClickListener(view -> {
                Intent intent = new Intent(CrearMascota.this, Account.class);
                startActivity(intent);
            });
        }

        btnBack.setOnClickListener(view -> {
            finish();
        });


        mascotaViewModel = new ViewModelProvider(this).get(MascotaViewModel.class);

        btnSave.setOnClickListener(v -> {
            tvNameError.setVisibility(View.GONE);
            tvAnimalError.setVisibility(View.GONE);
            String nombre = etName.getText().toString().trim();
            String animal = etAnimal.getText().toString().trim().toLowerCase();
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
            String token = sharedPreferences.getString(TOKEN_KEY, null);

            CrearMascotaRequest request = new CrearMascotaRequest(nombre, animal);

            MutableLiveData<Map<String, String>> errores = new MutableLiveData<>();
            MutableLiveData<Boolean> exito = new MutableLiveData<>();


            mascotaViewModel.crearMascota(token, request, errores, exito);


            errores.observe(this, errorMap -> {
                if (errorMap != null) {
                    tvNameError.setVisibility(View.VISIBLE);
                    tvAnimalError.setVisibility(View.VISIBLE);
                    tvNameError.setText(errorMap.getOrDefault("nombre", ""));
                    tvAnimalError.setText(errorMap.getOrDefault("animal", ""));
                } else {
                    tvNameError.setText("");
                    tvAnimalError.setText("");
                    tvNameError.setVisibility(View.GONE);
                    tvAnimalError.setVisibility(View.GONE);
                }
            });

            exito.observe(this, isSuccessful -> {
                if (isSuccessful) {
                    Toast.makeText(this, "Mascota creada con éxito", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etAnimal.setText("");
                    tvNameError.setVisibility(View.GONE);
                    tvAnimalError.setVisibility(View.GONE);
                    Intent intent = new Intent(CrearMascota.this, Mascotas.class);
                    startActivity(intent);
                    finish();
                } else if (errores.getValue() == null) {
                    Toast.makeText(this, "Error al crear la mascota", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}