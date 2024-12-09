package com.example.integradora.views.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.integradora.views.viewModel.ComederoViewModel;

import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String SHARED_PREFS_NAME = "MyAppPrefs";
    private static final String TOKEN_KEY = "BearerToken";
    private LinearLayout llProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, null);

        if (token == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.activity_main);

        llProfile = findViewById(R.id.llProfile);

        if (llProfile != null) { llProfile.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Account.class);
            startActivity(intent);
        });
        } else {
            Log.e("MainActivity", "llProfile is null");
        }

        ComederoViewModel viewModel = new ViewModelProvider(this).get(ComederoViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ComederoAdapter adapter = new ComederoAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        viewModel.getComederos(token).observe(this, response -> {
            if (response != null && response.getData() != null) {
                adapter.setComederoList(response.getData(), this);
            } else {
                Log.e("MainActivity", "Response or data is null");
            }
        });


    }
}