package com.yuli.micafe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.yuli.micafe.modelo.User;

public class RegistrarActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPass;
    private Button btnCreate;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_registrar);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String pass = etPass.getText().toString();

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                try {
                    AppDatabase db = AppDatabase.getInstance(this);
                    if (db.userDao().findByEmail(email) != null) {
                        runOnUiThread(() -> Toast.makeText(this, "Email ya registrado", Toast.LENGTH_SHORT).show());
                        return;
                    }
                    User u = new User();
                    u.name = name; u.email = email; u.password = pass;
                    long id = db.userDao().insert(u);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Usuario creado (id " + id + ")", Toast.LENGTH_SHORT).show();
                        finish(); // vuelve al login
                    });
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }).start();
        });
    }
}