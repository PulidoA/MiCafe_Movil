package com.yuli.micafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.yuli.micafe.modelo.Pedido;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CarritoActivity extends AppCompatActivity {
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_carrito);
        DrawerHelper.setup(this);

        String nombre = getIntent().getStringExtra("nombre");
        String descripcion = getIntent().getStringExtra("descripcion");
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
            // Create a new Pedido object with dummy data
            Pedido pedido = new Pedido();
            pedido.setCliente("Cliente Fijo");
            pedido.setTotal(precio);
            pedido.setEstado("EN_CAMINO");
            pedido.setFecha(new Date().getTime());
            pedido.setNombre(nombre);
            pedido.setNombre(descripcion);

            // Save pedido in background thread
            executor.execute(() -> {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                db.pedidoDao().insertar(pedido);

                runOnUiThread(() -> {
                    Toast.makeText(CarritoActivity.this, "Pedido guardado correctamente", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, ConfirmarActivity.class);
                    i.putExtra("total", precio);
                    i.putExtra("nombre", nombre);
                    i.putExtra("descripcion", descripcion);
                    startActivity(i);
                });
            });
        });
    }
}