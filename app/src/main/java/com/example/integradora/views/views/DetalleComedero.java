package com.example.integradora.views.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleComedero extends AppCompatActivity {
    private static final String SHARED_PREFS_NAME = "MyAppPrefs";
    private static final String TOKEN_KEY = "BearerToken";
    private TextView tvNumeroSerie, tvEstado, tvMascotaNombre,
            tvCantidadComida, tvCantidadAgua, tvCantidadComidaServida, tvCantidadAguaServida, tvHumedad, tvGases, tvTemperaturaAgua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_comedero);

        tvNumeroSerie = findViewById(R.id.tvNumeroSerie);
        tvEstado = findViewById(R.id.tvEstado);
        tvMascotaNombre = findViewById(R.id.tvMascotaNombre);
        tvCantidadComida = findViewById(R.id.tvCantidadComida);
        tvCantidadAgua = findViewById(R.id.tvCantidadAgua);
        tvCantidadComidaServida = findViewById(R.id.tvCantidadComidaServida);
        tvCantidadAguaServida = findViewById(R.id.tvCantidadAguaServida);
        tvHumedad = findViewById(R.id.tvHumedad);
        tvGases = findViewById(R.id.tvGases);
        tvTemperaturaAgua = findViewById(R.id.tvTemperaturaAgua);

        int comederoId = getIntent().getIntExtra("comederoId", -1);

        if (comederoId != -1) {
            obtenerDetallesComedero(comederoId);
        }
    }
    private void obtenerDetallesComedero(int comederoId) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, null);

        apiComedero api = RetrofitClient.getInstance().create(apiComedero.class);

        api.obtenerComedero("Bearer " + token, comederoId).enqueue(new Callback<ComederoResponse>() {
            @Override
            public void onResponse(Call<ComederoResponse> call, Response<ComederoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Comedero comedero = response.body().getData().get(0); // Asume que hay un único comedero
                    tvNumeroSerie.setText("Número de Serie: " + comedero.getNumero_serie());
                    tvEstado.setText("Estado: " + comedero.getEstado());
                    tvMascotaNombre.setText("Mascota: " + comedero.getMascota().getNombre());
                    tvCantidadComida.setText("Cantidad de Comida: " + comedero.getCantidad_comida());
                    tvCantidadAgua.setText("Cantidad de Agua: " + comedero.getCantidad_agua());
                    tvCantidadComidaServida.setText("Cantidad de Comida Servida: " + comedero.getCantidad_comida_servida());
                    tvCantidadAguaServida.setText("Cantidad de Agua Servida: " + comedero.getCantidad_agua_servida());
                    tvHumedad.setText("Humedad: " + comedero.getHumedad());
                    tvGases.setText("Gases: " + comedero.getGases());
                    tvTemperaturaAgua.setText("Temperatura de Agua: " + comedero.getTemperatura_agua());
                }
            }

            @Override
            public void onFailure(Call<ComederoResponse> call, Throwable t) {
                Toast.makeText(DetalleComedero.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}