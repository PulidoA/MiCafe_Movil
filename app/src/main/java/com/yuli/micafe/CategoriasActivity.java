package com.yuli.micafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class CategoriasActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_categorias);
        DrawerHelper.setup(this);

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