package com.yuli.micafe;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DrawerHelper {

    public static void setup(final AppCompatActivity activity) {
        DrawerLayout drawerLayout = activity.findViewById(R.id.drawerLayout);
        NavigationView navigationView = activity.findViewById(R.id.navigationView);
        Toolbar toolbar = activity.findViewById(R.id.toolbar);

        if (drawerLayout == null || navigationView == null || toolbar == null) {
            // Por si alguna pantalla no tiene drawer, no hacemos nada
            return;
        }

        // Configurar toolbar como ActionBar
        activity.setSupportActionBar(toolbar);

        // Botón hamburguesa
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity,
                drawerLayout,
                toolbar,
                R.string.nav_open,
                R.string.nav_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Manejo de clics en el menú lateral
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                if (!(activity instanceof HomeActivity)) {
                    activity.startActivity(new Intent(activity, HomeActivity.class));
                }
            } else if (id == R.id.nav_config) {
                if (!(activity instanceof ConfiguracionActivity)) {
                    activity.startActivity(new Intent(activity, ConfiguracionActivity.class));
                }
            } else if (id == R.id.nav_perfil) {
                if (!(activity instanceof PerfilActivity)) {
                    activity.startActivity(new Intent(activity, PerfilActivity.class));
                }
            } else if (id == R.id.nav_logout) {
                // Borrar sesión
                SharedPreferences prefs = activity.getSharedPreferences("session", AppCompatActivity.MODE_PRIVATE);
                prefs.edit().clear().apply();

                Intent i = new Intent(activity, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(i);
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    // Para manejar el botón "atrás" y cerrar el drawer si está abierto
    public static boolean handleOnBackPressed(AppCompatActivity activity) {
        DrawerLayout drawerLayout = activity.findViewById(R.id.drawerLayout);
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true; // ya manejamos el back
        }
        return false; // que la Activity haga el super.onBackPressed()
    }
}