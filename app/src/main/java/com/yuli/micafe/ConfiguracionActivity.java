package com.yuli.micafe;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

public class ConfiguracionActivity extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        Switch sw = findViewById(R.id.switchNotificaciones);
        sw.setOnCheckedChangeListener((buttonView, activado) ->
                Toast.makeText(this,
                        activado ? "Notificaciones activadas (demo)" : "Notificaciones desactivadas (demo)",
                        Toast.LENGTH_SHORT).show());
    }
}