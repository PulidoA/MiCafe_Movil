package com.yuli.micafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class DetalleProductoActivity extends Activity {

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_detalle_producto);

        String nombre = getIntent().getStringExtra("nombre");
        int precio = getIntent().getIntExtra("precio", 0);
        String descripcion = getIntent().getStringExtra("descripcion");

        ((TextView)findViewById(R.id.txtNombre)).setText(nombre);
        ((TextView)findViewById(R.id.txtPrecio)).setText("$ " + precio);
        ((TextView)findViewById(R.id.txtDescripcion)).setText(descripcion);

        findViewById(R.id.btnAgregar).setOnClickListener(v -> {
            Intent i = new Intent(this, CarritoActivity.class);
            i.putExtra("nombre", nombre);
            i.putExtra("precio", precio);
            startActivity(i);
        });
    }
}