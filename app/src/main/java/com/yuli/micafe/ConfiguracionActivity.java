package com.yuli.micafe;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfiguracionActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        DrawerHelper.setup(this);

        Switch sw = findViewById(R.id.switchNotificaciones);
        sw.setOnCheckedChangeListener((buttonView, activado) ->
                Toast.makeText(this,
                        activado ? "Notificaciones activadas (demo)" : "Notificaciones desactivadas (demo)",
                        Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onBackPressed() {
        if (!DrawerHelper.handleOnBackPressed(this)) {
            super.onBackPressed();
        }
    }
}