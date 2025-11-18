package com.yuli.micafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class PerfilActivity extends Activity {
    EditText etNombre, etTelefono, etDireccion, etCorreo;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        etDireccion = findViewById(R.id.etDireccion);
        etCorreo = findViewById(R.id.etCorreo);

        findViewById(R.id.btnGuardarPerfil).setOnClickListener(v ->
                Toast.makeText(this, "Perfil guardado (demo)", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnEditarPerfil).setOnClickListener(v ->
                Toast.makeText(this, "Modo edición (demo)", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnEliminarPerfil).setOnClickListener(v -> {
            etNombre.setText(""); etTelefono.setText(""); etDireccion.setText(""); etCorreo.setText("");
            Toast.makeText(this, "Perfil eliminado (demo)", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnContinuar).setOnClickListener(v ->
                startActivity(new Intent(this, CategoriasActivity.class)));
    }
}