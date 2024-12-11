package com.example.integradora.views.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.integradora.R;
import com.example.integradora.views.adapter.ComederoAdapter;
import com.example.integradora.views.models.Comedero;
import com.example.integradora.views.viewModel.ComederoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String SHARED_PREFS_NAME = "MyAppPrefs";
    private static final String TOKEN_KEY = "BearerToken";
    private LinearLayout llProfile;
    private TextView tvEmpty;
    private FloatingActionButton btnAdd;

    private ComederoAdapter adapter;
    private ComederoViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, null);

        if (token == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
            return;
        }

        llProfile = findViewById(R.id.llProfile);

        if (llProfile != null) { llProfile.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Account.class);
            startActivity(intent);
        });
        } else {
            Log.e("MainActivity", "llProfile is null");
        }

        btnAdd = findViewById(R.id.add);
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ElegirMascota.class);
            startActivity(intent);
        });

        viewModel = new ViewModelProvider(this).get(ComederoViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new ComederoAdapter(new ArrayList<>(), id -> eliminarComedero(token, id));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        tvEmpty = findViewById(R.id.empty);

        viewModel.getComederos(token).observe(this, response -> {
            if (response != null && response.getData() != null) {
                tvEmpty.setVisibility(View.GONE);
                adapter.setComederoList(response.getData(), this);

                if (adapter.getItemCount() == 0) {
                    tvEmpty.setVisibility(View.VISIBLE);
                }
            } else {

                Log.e("MainActivity", "Response or data is null");
            }
        });


    }

    private void eliminarComedero(String token, int id) {
        viewModel.eliminarComedero(token, id).observe(this, exito -> {
            if (exito != null && exito) {
                Toast.makeText(this, "Comedero eliminado exitosamente", Toast.LENGTH_SHORT).show();

                List<Comedero> comederoList = adapter.getComederoList();
                comederoList.removeIf(comedero -> comedero.getId() == id);
                adapter.setComederoList(comederoList, this);

                if (adapter.getItemCount() == 0) {
                    tvEmpty.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(this, "Error al eliminar el comedero", Toast.LENGTH_SHORT).show();
            }
        });
    }
}