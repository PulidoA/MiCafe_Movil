package com.yuli.micafe.modelo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PedidoDao {

    @Insert
    long insertar(Pedido p);

    @Update
    int actualizar(Pedido p);

    @Delete
    int eliminar(Pedido p);

    @Query("SELECT * FROM pedidos ORDER BY fechaMs DESC")
    List<Pedido> listarTodo();

    @Query("SELECT * FROM pedidos WHERE id = :id LIMIT 1")
    Pedido porId(long id);
}