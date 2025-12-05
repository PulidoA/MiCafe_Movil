package com.yuli.micafe;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yuli.micafe.modelo.Pedido;
import com.yuli.micafe.modelo.PedidoDao;
import com.yuli.micafe.modelo.User;
import com.yuli.micafe.modelo.UserDao;

@Database(
        entities = { User.class, Pedido.class },  // añade User
        version = 4,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    public abstract UserDao userDao();
    public abstract PedidoDao pedidoDao();

    public static AppDatabase getInstance(Context ctx) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            ctx.getApplicationContext(),
                            AppDatabase.class, "micafe.db"
                    )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}