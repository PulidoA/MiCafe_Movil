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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);
        DrawerHelper.setup(this);

        // Retrieve the total amount passed via Intent extras
        int totalAmount = getIntent().getIntExtra("total", 0);

        // Find the TextView and set the total amount text
        TextView txtResumen = findViewById(R.id.txtResumen);
        txtResumen.setText("Total pagado: $ " + totalAmount);

        // Find the button and set an OnClickListener to navigate to SeguimientoActivity
        Button btnSeguirPedido = findViewById(R.id.btnSeguirPedido);
        btnSeguirPedido.setOnClickListener(view -> {
            Intent intent = new Intent(ConfirmarActivity.this, SeguimientoActivity.class);
            startActivity(intent);
        });
    }
}