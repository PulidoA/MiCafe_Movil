package com.yuli.micafe.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pedidos")
public class Pedido {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String cliente;     // nombre o id del cliente
    public double total;       // total del pedido
    public String estado;      // p.ej.: "EN_PROCESO", "EN_CAMINO", "ENTREGADO"
    public long fechaMs;       // System.currentTimeMillis()

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getFecha() {
        return fechaMs;
    }

    public void setFecha(long fechaMs) {
        this.fechaMs = fechaMs;
    }

}