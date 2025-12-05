package com.yuli.micafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.yuli.micafe.modelo.User;

public class PerfilActivity extends AppCompatActivity {
    EditText etNombre, etTelefono, etDireccion, etCorreo;
    private long userId = -1;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        DrawerHelper.setup(this);

        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        userId = prefs.getLong("userId", -1);
        if (userId == -1) {
            // No hay sesión, regresar al login
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }


        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        etDireccion = findViewById(R.id.etDireccion);
        etCorreo = findViewById(R.id.etCorreo);

        cargarDatosUsuario();


        findViewById(R.id.btnEditarPerfil).setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            String direccion = etDireccion.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();

            if (nombre.isEmpty() || correo.isEmpty()) {
                Toast.makeText(this, "Nombre y correo son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                User u = db.userDao().findById(userId);

                if (u != null) {
                    u.name = nombre;
                    u.email = correo;
                    u.telefono = telefono;
                    u.direccion = direccion;

                    db.userDao().update(u);

                    runOnUiThread(() -> {
                        // Actualizar también el nombre en la sesión, por si lo usas en Home
                        prefs.edit().putString("userName", nombre).apply();

                        Toast.makeText(PerfilActivity.this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(PerfilActivity.this, "No se encontró el usuario en BD", Toast.LENGTH_SHORT).show()
                    );
                }
            }).start();
        });
    }


    private void cargarDatosUsuario() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            User u = db.userDao().findById(userId); // Asegúrate de tener este método en UserDao

            if (u != null) {
                runOnUiThread(() -> {
                    etNombre.setText(u.name);
                    etCorreo.setText(u.email);
                     etTelefono.setText(u.telefono);
                     etDireccion.setText(u.direccion);
                });
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        if (!DrawerHelper.handleOnBackPressed(this)) {
            super.onBackPressed();
        }
    }
}