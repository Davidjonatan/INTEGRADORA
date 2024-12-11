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
import com.example.integradora.views.adapter.ElegirMascotaAdapter;
import com.example.integradora.views.adapter.MascotaAdapter;
import com.example.integradora.views.viewModel.MascotaViewModel;

import java.util.ArrayList;

public class ElegirMascota extends AppCompatActivity {
    private MascotaViewModel mascotaViewModel;
    private LinearLayout llFeeders, llProfile;
    private RecyclerView recyclerView;
    private Button bntBack;
    private ElegirMascotaAdapter adapter;
    private static final String SHARED_PREFS_NAME = "MyAppPrefs";
    private static final String TOKEN_KEY = "BearerToken";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_mascota);

        llFeeders = findViewById(R.id.llFeeders);
        llProfile = findViewById(R.id.llAccount);
        TextView emptyTextView = findViewById(R.id.empty);

        if (llProfile != null) { llProfile.setOnClickListener(view -> {
            Intent intent = new Intent(ElegirMascota.this, Account.class);
            startActivity(intent);
        });
        } else {
            Log.e("MainActivity", "llProfile is null");
        }

        llFeeders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElegirMascota.this, MainActivity.class);
                startActivity(intent);

            }
        });

        bntBack = findViewById(R.id.btnBack);

        bntBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }});

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ElegirMascotaAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, null);

        mascotaViewModel = new ViewModelProvider(this).get(MascotaViewModel.class);

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
}