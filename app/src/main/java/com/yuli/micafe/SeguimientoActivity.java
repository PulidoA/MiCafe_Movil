package com.yuli.micafe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class SeguimientoActivity extends AppCompatActivity {

    private static final int REQ_LOCATION = 1001;

    private TextView tvEstado;
    private Button btnAbrirMapa;
    private Button btnCompartir;

    private FusedLocationProviderClient fusedClient;
    private Double lastLat = null, lastLng = null;

    private Button btnVerPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento);
        DrawerHelper.setup(this);

        tvEstado = findViewById(R.id.tvEstado);
        btnAbrirMapa = findViewById(R.id.btnAbrirMapa);
        btnCompartir = findViewById(R.id.btnCompartir);
        btnVerPedidos = findViewById(R.id.btnVerPedidos);

        fusedClient = LocationServices.getFusedLocationProviderClient(this);

        // Botón: abrir Google Maps en mi ubicación actual (si ya la tenemos)
        btnAbrirMapa.setOnClickListener(v -> abrirMapaConMiUbicacion());

        // Botón: compartir estado del pedido (con link a Maps si hay ubicación)
        btnCompartir.setOnClickListener(v -> compartirEstado());

        btnVerPedidos.setOnClickListener(v -> {
            Intent i = new Intent(SeguimientoActivity.this, HomeActivity.class);
            startActivity(i);
        });

        // Opcional: intenta obtener la última ubicación una sola vez al abrir
        obtenerUltimaUbicacion();
    }

    private void obtenerUltimaUbicacion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQ_LOCATION
            );
            return;
        }

        fusedClient.getLastLocation().addOnSuccessListener(loc -> {
            if (loc != null) {
                lastLat = loc.getLatitude();
                lastLng = loc.getLongitude();
                tvEstado.setText("Tu pedido va en camino\nLat: " + lastLat + "  Lng: " + lastLng);
            }
        });
    }

    private void abrirMapaConMiUbicacion() {
        if (lastLat == null || lastLng == null) {
            Toast.makeText(this, "Aún no tengo tu ubicación, intenta de nuevo.", Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = Uri.parse("geo:" + lastLat + "," + lastLng + "?q=" + lastLat + "," + lastLng + "(Mi ubicación)&z=16");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(intent);
        } catch (Exception e) {
            intent.setPackage(null);
            startActivity(intent);
        }
    }

    private void compartirEstado() {
        // Mensaje base
        StringBuilder sb = new StringBuilder();
        sb.append("Tu pedido de MiCafé va en camino 🚗☕");

        // Si tienes ubicación, añade coordenadas y link a Google Maps
        if (lastLat != null && lastLng != null) {
            String mapsLink = "https://maps.google.com/?q=" + lastLat + "," + lastLng;
            sb.append("\nUbicación aprox: ").append(lastLat).append(", ").append(lastLng);
            sb.append("\nVer en mapa: ").append(mapsLink);
        }

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, "Estado del pedido - MiCafé");
        share.putExtra(Intent.EXTRA_TEXT, sb.toString());

        startActivity(Intent.createChooser(share, "Compartir con..."));
    }

    // Permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUltimaUbicacion();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}