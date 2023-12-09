package com.santotomas.evfinalcalendario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MostrarReceta extends AppCompatActivity {

    TextView txNombre, txIngredientes, txDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_receta);

        String nombreReceta = getIntent().getStringExtra("nombreReceta");
        String ingredientesReceta = getIntent().getStringExtra("ingredientesReceta");
        String descripcionReceta = getIntent().getStringExtra("descripcionReceta");

        txNombre = findViewById(R.id.txNombre);
        txIngredientes =findViewById(R.id.txIngredientes);
        txDescripcion = findViewById(R.id.txDescripcion);

        txNombre.setText(nombreReceta);
        txIngredientes.setText(ingredientesReceta);
        txDescripcion.setText(descripcionReceta);
    }
}