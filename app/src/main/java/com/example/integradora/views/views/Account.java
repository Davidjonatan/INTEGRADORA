package com.example.integradora.views.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.integradora.R;

public class Account extends AppCompatActivity {
    private LinearLayout llFeeders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        llFeeders = findViewById(R.id.llFeeders);

        if (llFeeders != null) { llFeeders.setOnClickListener(view -> {
            Intent intent = new Intent(Account.this, MainActivity.class);
            startActivity(intent);
        });
        } else {
            Log.e("MainActivity", "llProfile is null");
        }

    }
}