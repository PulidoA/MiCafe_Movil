package com.yuli.micafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yuli.micafe.modelo.Pedido;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConfirmarActivity extends AppCompatActivity {

    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);

        // Retrieve the total amount passed via Intent extras
        int totalAmount = getIntent().getIntExtra("total", 0);

        // Find the TextView and set the total amount text
        TextView txtResumen = findViewById(R.id.txtResumen);
        txtResumen.setText("Total pagado: $ " + totalAmount);

        // Find the button and set an OnClickListener to navigate to SeguimientoActivity
        Button btnSeguirPedido = findViewById(R.id.btnSeguirPedido);
        btnSeguirPedido.setOnClickListener(view -> {
            // Create a new Pedido object with dummy data
            Pedido pedido = new Pedido();
            pedido.setCliente("Cliente Fijo");
            pedido.setTotal(totalAmount);
            pedido.setEstado("EN_CAMINO");
            pedido.setFecha(new Date().getTime());

            // Save pedido in background thread
            executor.execute(() -> {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                db.pedidoDao().insertar(pedido);

                runOnUiThread(() -> {
                    Toast.makeText(ConfirmarActivity.this, "Pedido guardado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConfirmarActivity.this, SeguimientoActivity.class);
                    startActivity(intent);
                });
            });
        });
    }
}