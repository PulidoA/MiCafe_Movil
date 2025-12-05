package com.yuli.micafe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuli.micafe.modelo.Pedido;
import com.yuli.micafe.modelo.PedidoListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.VH> {

    private final List<Pedido> data = new ArrayList<>();
    private final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

    private PedidoListener listener;

    public void setListener(PedidoListener l) {
        this.listener = l;
    }

    public void submit(List<Pedido> nuevos) {
        data.clear();
        if (nuevos != null) data.addAll(nuevos);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pedido, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        Pedido p = data.get(pos);

        // Si tu entidad usa getters:
        String cliente;
        String estado;
        double total;
        Long fechaMs = null;
        Date fechaObj = null;

        try {
            cliente = p.getCliente();
            estado  = p.getEstado();
            total   = p.getTotal();
            // Si usas long:
            try { fechaMs = p.getFecha(); } catch (Exception ignored) {}
        } catch (Exception e) {
            // Si tus campos son públicos:
            cliente = p.cliente;
            estado  = p.estado;
            total   = p.total;
            try { fechaMs = p.fechaMs; } catch (Exception ignored) {}
        }

        h.tvCliente.setText("Cliente: " + cliente);
        h.tvEstado.setText("Estado: " + estado);
        h.tvTotal.setText("Total: $ " + (long) total);

        String fechaTexto = "-";
        if (fechaObj != null) {
            fechaTexto = df.format(fechaObj);
        } else if (fechaMs != null) {
            fechaTexto = df.format(new Date(fechaMs));
        }
        h.tvFecha.setText("Fecha: " + fechaTexto);

        // Botón eliminar
        h.btnEliminar.setOnClickListener(v -> {
            if (listener != null) listener.onEliminar(p);
        });
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvCliente, tvEstado, tvTotal, tvFecha, btnEliminar;
        VH(@NonNull View v) {
            super(v);
            tvCliente = v.findViewById(R.id.tvCliente);
            tvEstado  = v.findViewById(R.id.tvEstado);
            tvTotal   = v.findViewById(R.id.tvTotal);
            tvFecha   = v.findViewById(R.id.tvFecha);
            btnEliminar = v.findViewById(R.id.btnEliminar);
        }
    }
}

