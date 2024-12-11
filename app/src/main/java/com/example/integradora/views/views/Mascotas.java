package com.example.integradora.views.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.integradora.R;
import com.example.integradora.views.adapter.MascotaAdapter;
import com.example.integradora.views.models.Mascota;
import com.example.integradora.views.viewModel.MascotaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Mascotas extends AppCompatActivity implements MascotaAdapter.OnMascotaEliminarListener {
    private MascotaViewModel mascotaViewModel;
    private LinearLayout llFeeders, llAccount;
    private MascotaAdapter adapter;
    private TextView emptyTextView;
    private Button btnBack;
    private FloatingActionButton btnAdd;
    private static final String SHARED_PREFS_NAME = "MyAppPrefs";
    private static final String TOKEN_KEY = "BearerToken";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascotas);

        llFeeders = findViewById(R.id.llFeeders);
        llAccount = findViewById(R.id.llAccount);

        if (llFeeders != null) {
            llFeeders.setOnClickListener(view -> {
                Intent intent = new Intent(Mascotas.this, MainActivity.class);
                startActivity(intent);
            });
        } else {
            Log.e("MainActivity", "llProfile is null");
        }

        if (llAccount != null) {
            llAccount.setOnClickListener(view -> {
                Intent intent = new Intent(Mascotas.this, Account.class);
                startActivity(intent);
            });
        }

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            finish();
        });

        btnAdd = findViewById(R.id.add);
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(Mascotas.this, CrearMascota.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MascotaAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, null);

        mascotaViewModel = new ViewModelProvider(this).get(MascotaViewModel.class);
        emptyTextView = findViewById(R.id.empty);

        // Observa los datos de mascotas
        mascotaViewModel.getMascotas(token).observe(this, mascotaResponse -> {
            if (mascotaResponse != null && mascotaResponse.getData() != null) {
                emptyTextView.setVisibility(View.GONE);
                adapter.setMascotaList(mascotaResponse.getData(), this);

                if (adapter.getItemCount() == 0) {
                    emptyTextView.setVisibility(View.VISIBLE);
                }
            } else {
                Log.d("Token", "Bearer " + token);
                Toast.makeText(this, "Error al obtener las mascotas", Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public void onMascotaEliminar(int id) {
        // Elimina la mascota a través del ViewModel
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, null);

        mascotaViewModel.eliminarMascota(token, id).observe(this, eliminado -> {
            if (eliminado) {
                // Actualiza la lista del RecyclerView
                List<Mascota> mascotaList = adapter.getMascotaList();
                mascotaList.removeIf(mascota -> mascota.getId() == id);
                adapter.setMascotaList(mascotaList, this);
                Toast.makeText(this, "Mascota eliminada con éxito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "La mascota está registrada en un comedero", Toast.LENGTH_LONG).show();
            }
        });
    }



}