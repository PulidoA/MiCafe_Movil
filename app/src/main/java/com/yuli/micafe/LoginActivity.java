package com.yuli.micafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.yuli.micafe.modelo.User;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPass;
    private Button btnLogin, btnGoRegister;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPass.getText().toString();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Ingresa email y contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(this);
                User u = db.userDao().login(email, pass);
                runOnUiThread(() -> {
                    if (u == null) {
                        Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
                        prefs.edit()
                                .putLong("userId", u.id)
                                .putString("userName", u.name)
                                .apply();
                        Intent i = new Intent(this, HomeActivity.class);
                        i.putExtra("userId", u.id);
                        i.putExtra("userName", u.name);
                        startActivity(i);
                        finish();
                    }
                });
            }).start();
        });

        btnGoRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegistrarActivity.class))
        );
    }
}