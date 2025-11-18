package com.yuli.micafe;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuli.micafe.modelo.Pedido;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PedidoListActivity extends AppCompatActivity {

    private RecyclerView rvPedidos;
    private TextView tvVacio;
    private PedidoAdapter adapter;
    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_list);

        rvPedidos = findViewById(R.id.rvPedidos);
        tvVacio   = findViewById(R.id.tvVacio);

        adapter = new PedidoAdapter();
        rvPedidos.setLayoutManager(new LinearLayoutManager(this));
        rvPedidos.setAdapter(adapter);

        cargarPedidos();
    }

    private void cargarPedidos() {
        exec.execute(() -> {
            List<Pedido> lista = AppDatabase.getInstance(getApplicationContext())
                    .pedidoDao()
                    .listarTodo(); // Asegúrate de tener este método en tu DAO

            runOnUiThread(() -> {
                adapter.submit(lista);
                tvVacio.setVisibility((lista == null || lista.isEmpty()) ? View.VISIBLE : View.GONE);
            });
        });
    }
}