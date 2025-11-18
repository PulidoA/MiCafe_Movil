package com.yuli.micafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class CategoriasActivity extends Activity {
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_categorias);

        Button btnPerfil = findViewById(R.id.btnPerfil);
        Button btnConfiguracion = findViewById(R.id.btnConfiguracion);
        btnPerfil.setOnClickListener(v -> startActivity(new Intent(this, PerfilActivity.class)));
        btnConfiguracion.setOnClickListener(v -> startActivity(new Intent(this, ConfiguracionActivity.class)));

        ListView lv = findViewById(R.id.listaCategorias);
        String[] categorias = getResources().getStringArray(R.array.categorias);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categorias);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            String categoria = categorias[position];
            Intent i = new Intent(this, ListaProductosActivity.class);
            i.putExtra("categoria", categoria);
            startActivity(i);
        });
    }
}