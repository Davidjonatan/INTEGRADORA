package com.example.integradora.views.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.integradora.R;
import com.example.integradora.views.api.apiComedero;
import com.example.integradora.views.models.Comedero;
import com.example.integradora.views.models.ComederoResponse;
import com.example.integradora.views.network.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleComedero extends AppCompatActivity {
    private static final String SHARED_PREFS_NAME = "MyAppPrefs";
    private static final String TOKEN_KEY = "BearerToken";
    private static final String KEY_DATE = "savedDate";
    private SharedPreferences shared;
    private Button bntUpdate, btnBack, btnServir;
    private LinearLayout llFeeders, llProfile;
    private TextView ultimaActualizacion ,tvtittle,tvNumeroSerie, tvEstado, tvMascotaNombre,
            tvCantidadComida, tvCantidadAgua, tvCantidadComidaServida, tvCantidadAguaServida, tvHumedad, tvGases, tvTemperaturaAgua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_comedero);
        shared = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);





        tvEstado = findViewById(R.id.tvEstado);
        tvMascotaNombre = findViewById(R.id.tvMascotaNombre);
        tvCantidadComida = findViewById(R.id.tvCantidadComida);
        tvCantidadAgua = findViewById(R.id.tvCantidadAgua);
        tvCantidadComidaServida = findViewById(R.id.tvCantidadComidaServida);
        tvCantidadAguaServida = findViewById(R.id.tvCantidadAguaServida);
        tvHumedad = findViewById(R.id.tvHumedad);
        tvGases = findViewById(R.id.tvGases);
        tvTemperaturaAgua = findViewById(R.id.tvTemperaturaAgua);
        llFeeders = findViewById(R.id.llFeeders);
        llProfile = findViewById(R.id.llProfile);
        btnBack = findViewById(R.id.btnBack);
        btnServir = findViewById(R.id.btnServir);
        tvtittle = findViewById(R.id.tittle);
        ultimaActualizacion = findViewById(R.id.ultimaActualizacion);

        String savedDate = shared.getString(KEY_DATE, "No se ha registrado una fecha de actualización.");
        ultimaActualizacion.setText("Ultima actualización: " +savedDate);

        int comederoId = getIntent().getIntExtra("comederoId", -1);

        if (comederoId != -1) {
            obtenerDetallesComedero(comederoId);
        }

        llProfile = findViewById(R.id.llProfile);

        if (llProfile != null) { llProfile.setOnClickListener(view -> {
            Intent intent = new Intent(DetalleComedero.this, Account.class);
            startActivity(intent);
        });
        } else {
            Log.e("MainActivity", "llProfile is null");
        }

        llFeeders = findViewById(R.id.llFeeders);

        if (llFeeders != null) { llFeeders.setOnClickListener(view -> {
            Intent intent = new Intent(DetalleComedero.this, Account.class);
            startActivity(intent);
        });
        }
         /*
                Intent intent = new Intent(DetalleComedero.this, MainActivity
                        .class);
                startActivity(intent);*/

        bntUpdate = findViewById(R.id.btnUpdate);

        bntUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncComedero();

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnServir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalleComedero.this, ServirComida.class);
                startActivity(intent);
            }
        });
    }

    private void syncComedero() {
        apiComedero apiService = RetrofitClient.getInstance().create(apiComedero.class);
        int comederoId = getIntent().getIntExtra("comederoId", -1);

        Call<Void> call = apiService.syncComedero();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DetalleComedero.this, "Sincronización exitosa", Toast.LENGTH_SHORT).show();

                    String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString(KEY_DATE, currentDate);
                    editor.apply();

                    ultimaActualizacion.setText( "Ultima actualización: "+ currentDate);

                    obtenerDetallesComedero(comederoId);
                } else {
                    Toast.makeText(DetalleComedero.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(DetalleComedero.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerDetallesComedero(int comederoId) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, null);

        apiComedero api = RetrofitClient.getInstance().create(apiComedero.class);

        api.obtenerComedero("Bearer " + token, comederoId).enqueue(new Callback<ComederoResponse>() {
            @Override
            public void onResponse(Call<ComederoResponse> call, Response<ComederoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Comedero comedero = response.body().getData().get(0);

                    // estado
                    if(comedero.getEstado().equals("ACTIVO")){
                        tvEstado.setText("Estado: " + comedero.getEstado());
                        tvEstado.setBackgroundResource(R.drawable.success_background);
                    }else{
                        tvEstado.setText("Estado: " + comedero.getEstado());
                        tvEstado.setBackgroundResource(R.drawable.error_background);
                    }

                    tvMascotaNombre.setText("Mascota: " + comedero.getMascota().getNombre());

                    // cuanta comida queda
                    tvCantidadComida.setText("Cantidad de Comida: " + comedero.getCantidad_comida() + "cm");

                    // cuanta agua queda
                    tvCantidadAgua.setText("Cantidad de Agua: " + comedero.getCantidad_agua() + " cm");
                    tvCantidadComidaServida.setText("Cantidad de Comida Servida: " + comedero.getCantidad_comida_servida() + " gr");
                    tvCantidadAguaServida.setText("Cantidad de Agua Servida: " + comedero.getCantidad_agua_servida() + " gr");

                    // humedad
                    tvHumedad.setText("Humedad: ");
                    TextView tvHumedo = findViewById(R.id.tvHumedo);

                    if (comedero.getHumedad()  < 30){
                        tvHumedo.setBackgroundResource(R.drawable.error_background);
                        tvHumedo.setText("Ambiente seco con " + comedero.getHumedad() + "% de humedad.");
                        tvHumedo.setVisibility(View.VISIBLE);

                    } else if (comedero.getHumedad() > 61){
                        tvHumedo.setBackgroundResource(R.drawable.medium_background);
                        tvHumedo.setText("Ambiente muy húmedo con " + comedero.getHumedad() + "% de humedad.");
                        tvHumedo.setVisibility(View.VISIBLE);
                    } else {
                        tvHumedo.setBackgroundResource(R.drawable.success_background);
                        tvHumedo.setText("Ambiente óptimo con " + comedero.getHumedad() + "% de humedad.");
                        tvHumedo.setVisibility(View.VISIBLE);
                    }

                    //gases
                    tvGases.setText("Gases: " + comedero.getGases() + " ppm");

                    // temperatura del agua
                    tvTemperaturaAgua.setText("Temperatura de Agua: ");
                    TextView tvCaliente = findViewById(R.id.tvCaliente);
                    TextView tvFrio = findViewById(R.id.tvFrio);
                    TextView tvAmbiente = findViewById(R.id.tvAmbiente);
                    if (comedero.getTemperatura_agua()  < 19){
                        tvCaliente.setVisibility(View.GONE);
                        tvAmbiente.setVisibility(View.GONE);
                        tvFrio.setVisibility(View.VISIBLE);
                    } else if (comedero.getTemperatura_agua() > 30){
                        tvFrio.setVisibility(View.GONE);
                        tvAmbiente.setVisibility(View.GONE);
                        tvCaliente.setVisibility(View.VISIBLE);
                    } else {
                        tvFrio.setVisibility(View.GONE);
                        tvCaliente.setVisibility(View.GONE);
                        tvAmbiente.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onFailure(Call<ComederoResponse> call, Throwable t) {
                Toast.makeText(DetalleComedero.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}