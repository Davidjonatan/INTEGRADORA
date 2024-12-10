package com.example.integradora.views.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.integradora.R;

public class Splash extends AppCompatActivity {
    private static final String SHARED_PREFS_NAME = "MyAppPrefs";
    private static final String TOKEN_KEY = "BearerToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new CountDownTimer(5000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(TOKEN_KEY, null);

                Intent intent;
                if (token == null) {
                    // Si no hay token, redirigir al Login
                    intent = new Intent(Splash.this, Login.class);
                } else {
                    // Si hay token, redirigir a MainActivity
                    intent = new Intent(Splash.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
