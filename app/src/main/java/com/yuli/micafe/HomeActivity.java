package com.yuli.micafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuli.micafe.modelo.Pedido;
import com.yuli.micafe.modelo.PedidoListener;

import java.util.List;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {
    private TextView tvHola, tvVacio;
    private RecyclerView rvPedidos;
    private Button btnCrear;
    private PedidoAdapter adapter;

    private long userId;
    private String userName;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_home);
        DrawerHelper.setup(this);

        tvHola = findViewById(R.id.tvHola);
        tvVacio = findViewById(R.id.tvVacio);
        rvPedidos = findViewById(R.id.rvPedidos);
        btnCrear = findViewById(R.id.btnCrearPedido);

        userId = getIntent().getLongExtra("userId", -1);
        userName = getIntent().getStringExtra("userName");
        tvHola.setText("Hola, " + (userName != null ? userName : "usuario"));

        adapter = new PedidoAdapter();
        rvPedidos.setLayoutManager(new LinearLayoutManager(this));
        rvPedidos.setAdapter(adapter);

        btnCrear.setOnClickListener(v -> {
            Intent i = new Intent(this, CategoriasActivity.class);
            i.putExtra("userId", userId);
            startActivity(i);
        });

        adapter.setListener(p -> new Thread(() -> {
            AppDatabase.getInstance(getApplicationContext()).pedidoDao().eliminar(p);

            runOnUiThread(() -> {
                Toast.makeText(HomeActivity.this, "Pedido eliminado", Toast.LENGTH_SHORT).show();
                cargarPedidos(); // refresca lista
            });
        }).start());
    }

    @Override protected void onResume() {
        super.onResume();
        cargarPedidos();
    }

    @Override
    public void onBackPressed() {
        if (!DrawerHelper.handleOnBackPressed(this)) {
            super.onBackPressed();
        }
    }

    private void cargarPedidos() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Pedido> datos = AppDatabase.getInstance(this)
                    .pedidoDao().listarTodo(); // si quieres filtrar por usuario, añade campo userId a Pedido
            runOnUiThread(() -> {
                adapter.submit(datos);
                tvVacio.setVisibility((datos == null || datos.isEmpty()) ? View.VISIBLE : View.GONE);
            });
        });
    }
}