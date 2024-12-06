package com.example.integradora.views.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.integradora.R;
import com.example.integradora.views.api.apiComedero;
import com.example.integradora.views.response.UserResponse;
import com.example.integradora.views.viewModel.UserViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.integradora.views.network.RetrofitClient;


public class Account extends AppCompatActivity {
    private LinearLayout llFeeders;
    private UserViewModel userViewModel;
    private Button btnLogout;
    private Button btnUpdate;

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

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);

        TextView etName = findViewById(R.id.etName);
        TextView nameTextView = findViewById(R.id.username);
        TextView emailTextView = findViewById(R.id.etEmail);

        userViewModel.getUserLiveData().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse user) {
                if (user != null && user.getName() != null && user.getEmail() != null) {
                    etName.setText("Nombre de usuario: "+user.getName());
                    nameTextView.setText("¡Hola, " + user.getName() + "!");
                    emailTextView.setText("Email: " + user.getEmail());
                }
            }
        });

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        btnUpdate = findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog();

            }
        });

    }
    private void showUpdateDialog() {
        // Crear el dialogo
        final Dialog dialog = new Dialog(Account.this);
        dialog.setContentView(R.layout.dialog_user_update);

        // Obtener vistas del dialogo
        EditText updateNameEditText = dialog.findViewById(R.id.etUpdateName);
        EditText updateEmailEditText = dialog.findViewById(R.id.etOldPassword);
        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnClose = dialog.findViewById(R.id.btnClose);

        userViewModel.getUserLiveData().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse user) {
                if (user != null && user.getName() != null && user.getEmail() != null) {
                    updateNameEditText.setHint(user.getName());
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void logoutUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("BearerToken", "");

        apiComedero apiService = RetrofitClient.getInstance().create(apiComedero.class);
        Call<Void> call = apiService.logoutUser("Bearer " + token);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("BearerToken");
                    editor.apply();

                    Intent intent = new Intent(Account.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("Logout Error", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API Error", "Failure: " + t.getMessage());
            }
        });

    }
}