package com.yuli.micafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class CarritoActivity extends Activity {
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_carrito);

        String nombre = getIntent().getStringExtra("nombre");
        int precio = getIntent().getIntExtra("precio", 0);

        TextView items = findViewById(R.id.txtItemsCarrito);
        if (nombre != null) items.setText("1 x " + nombre + "  -  $ " + precio);

        findViewById(R.id.btnEditarPedido).setOnClickListener(v ->
                Toast.makeText(this, "Editar pedido (demo)", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnEliminarPedido).setOnClickListener(v -> {
            items.setText("Carrito vacío");
            Toast.makeText(this, "Pedido eliminado (demo)", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnConfirmar).setOnClickListener(v -> {
            Intent i = new Intent(this, ConfirmarActivity.class);
            i.putExtra("total", precio);
            startActivity(i);
        });
    }
}