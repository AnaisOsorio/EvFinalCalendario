package com.santotomas.evfinalcalendario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        Button btnlogin = findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivityHome.class);
            startActivity(intent);
        });

        Button btnregister = findViewById(R.id.btnregister);

        btnregister.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivityRegister.class);
            startActivity(intent);
        });
    }
}
