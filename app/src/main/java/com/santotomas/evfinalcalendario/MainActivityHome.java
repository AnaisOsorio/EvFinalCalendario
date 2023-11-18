
package com.santotomas.evfinalcalendario;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

enum ProviderType{
    BASIC,
}
public class MainActivityHome extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;

    FragmentInicio fragmentInicio = new FragmentInicio();
    FragmentRecetas fragmentRecetas = new FragmentRecetas();
    FragmentLista fragmentLista = new FragmentLista();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragmentInicio).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragmentInicio).commit();
                    return true;
                }
                else if (item.getItemId() == R.id.nav_recetas){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragmentRecetas).commit();
                    return true;
                }
                else if (item.getItemId() == R.id.nav_lista){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragmentLista).commit();
                    return true;
                }
                else{
                    return false;
                }
            }
        });
    }
}
