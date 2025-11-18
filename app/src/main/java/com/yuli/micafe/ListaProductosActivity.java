package com.yuli.micafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class ListaProductosActivity extends Activity {
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_lista_productos);

        String categoria = getIntent().getStringExtra("categoria");
        ((TextView)findViewById(R.id.tvTitulo)).setText(categoria);

        // 1) Obtén los arreglos según la categoría
        String[] nombres; String[] precios; String[] descripciones;
        switch (categoria) {
            case "Cafés":
                nombres = getResources().getStringArray(R.array.nombres_cafes);
                precios = getResources().getStringArray(R.array.precios_cafes);
                descripciones = getResources().getStringArray(R.array.descripciones_cafes);
                break;
            case "Bebidas":
                nombres = getResources().getStringArray(R.array.nombres_bebidas);
                precios = getResources().getStringArray(R.array.precios_bebidas);
                descripciones = getResources().getStringArray(R.array.descripciones_bebidas);
                break;
            case "Postres":
                nombres = getResources().getStringArray(R.array.nombres_postres);
                precios = getResources().getStringArray(R.array.precios_postres);
                descripciones = getResources().getStringArray(R.array.descripciones_postres);
                break;
            default: // Snacks
                nombres = getResources().getStringArray(R.array.nombres_snacks);
                precios = getResources().getStringArray(R.array.precios_snacks);
                descripciones = getResources().getStringArray(R.array.descripciones_snacks);
        }

        // 2) Muestra solo nombres en la lista (adapter nativo)
        ListView lista = findViewById(R.id.listaProductos);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombres);
        lista.setAdapter(adapter);

        // 3) Al tocar un ítem, pasamos nombre/precio/descripcion por Intent (sin modelo)
        String[] finalNombres = nombres;
        String[] finalPrecios = precios;
        String[] finalDescripciones = descripciones;

        lista.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, DetalleProductoActivity.class);
            i.putExtra("nombre", finalNombres[position]);
            i.putExtra("precio", Integer.parseInt(finalPrecios[position]));
            i.putExtra("descripcion", finalDescripciones[position]);
            startActivity(i);
        });
    }
}