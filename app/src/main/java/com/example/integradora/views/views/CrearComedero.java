package com.example.integradora.views.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.integradora.R;
import com.example.integradora.views.models.CrearComederoRequest;
import com.example.integradora.views.models.Mascota;
import com.example.integradora.views.viewModel.ComederoViewModel;

import java.util.Map;

public class CrearComedero extends AppCompatActivity {
    private LinearLayout llAccount, llFeeders;
    private Button btnSave, btnBack;
    private static final String SHARED_PREFS_NAME = "MyAppPrefs";
    private static final String TOKEN_KEY = "BearerToken";
    private ComederoViewModel comederoViewModel;
    private TextView tvIdError;
    private EditText etId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_comedero);

        llAccount = findViewById(R.id.llProfile);
        llFeeders = findViewById(R.id.llFeeders);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        tvIdError = findViewById(R.id.tvIdError);



        llAccount.setOnClickListener(view -> {
            Intent intent = new Intent(CrearComedero.this, Account.class);
            startActivity(intent);
        });

        llFeeders.setOnClickListener(view -> {
            Intent intent = new Intent(CrearComedero.this, MainActivity.class);
            startActivity(intent);
        });

        btnBack.setOnClickListener(view -> {
           finish();
        });

        comederoViewModel = new ViewModelProvider(this).get(ComederoViewModel.class);

        Intent intentMascota = getIntent();
        Mascota mascotaElegida = (Mascota) intentMascota.getSerializableExtra("mascota");

        int id = mascotaElegida.getId();
        String nombreMascota = mascotaElegida.getNombre();

        TextView tvMascotaNombre= findViewById(R.id.tvMascotaNombre);
        TextView tvMascotaId = findViewById(R.id.tvMascotaId);
        tvMascotaNombre.setText("Nombre de mascota: " + nombreMascota);
        tvMascotaId.setText("Id de mascota: " + String.valueOf(id));

        btnSave.setOnClickListener(view -> {
            tvIdError.setVisibility(View.GONE);




            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
            String token = sharedPreferences.getString(TOKEN_KEY, null);

            CrearComederoRequest mascota = new CrearComederoRequest(id);

            MutableLiveData<Map<String, String>> errores = new MutableLiveData<>();
            MutableLiveData<Boolean> exito = new MutableLiveData<>();

            comederoViewModel.crearComedero(token, mascota, errores, exito);


            errores.observe(this, errorMap -> {
                if (errorMap != null) {
                    tvIdError.setVisibility(View.VISIBLE);
                    tvIdError.setText(errorMap.getOrDefault("mascota_id", "La mascota debe existir"));
                } else {
                    tvIdError.setText("");
                    tvIdError.setVisibility(View.GONE);
                }
            });

            exito.observe(this, isSuccessful -> {
                if (isSuccessful) {
                    Toast.makeText(this, "Comedero creado con éxito", Toast.LENGTH_SHORT).show();
                    tvIdError.setVisibility(View.GONE);
                    Intent intent = new Intent(CrearComedero.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (errores.getValue() == null) {
                    Toast.makeText(this, "Error al crear el comedero", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}