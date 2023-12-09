package com.santotomas.evfinalcalendario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santotomas.evfinalcalendario.model.Recetas;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrearRecetas extends AppCompatActivity {

    private List<Recetas> recetasList = new ArrayList<Recetas>();
    ArrayAdapter<Recetas> arrayAdapterReceta;
    EditText txtNombre, txtIngredientes, txtDescripcion;
    ListView listview;
    Button btnAgregar, btnGuardar, btnEliminar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Recetas recetaseleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_recetas);

        txtNombre = findViewById(R.id.txtNombre);
        txtIngredientes = findViewById(R.id.txtIngredientes);
        txtDescripcion = findViewById(R.id.txtDescripcion);

        listview = findViewById(R.id.listview);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEliminar = findViewById(R.id.btnEliminar);

        iniciarFirebase();
        listarDatos();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recetaseleccionada = (Recetas) parent.getItemAtPosition(position);
                txtNombre.setText(recetaseleccionada.getNombre());
                txtIngredientes.setText(recetaseleccionada.getIngredientes());
                txtDescripcion.setText(recetaseleccionada.getDescripcion());
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = txtNombre.getText().toString().trim();
                String ingredientes = txtIngredientes.getText().toString().trim();
                String descripcion = txtDescripcion.getText().toString().trim();
                if (nombre.isEmpty() || ingredientes.isEmpty() || descripcion.isEmpty()){
                    Toast.makeText(CrearRecetas.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.length() >30) {
                    Toast.makeText(CrearRecetas.this, "El nombre de la receta es muy largo", Toast.LENGTH_SHORT).show();
                }
                else {
                    Recetas recetas = new Recetas();
                    recetas.setUid(UUID.randomUUID().toString());
                    recetas.setNombre(nombre);
                    recetas.setIngredientes(ingredientes);
                    recetas.setDescripcion(descripcion);
                    databaseReference.child("Recetas").child(recetas.getUid()).setValue(recetas);
                    Toast.makeText(CrearRecetas.this, "Receta Agregada con exito", Toast.LENGTH_SHORT).show();
                    LimpiarTexto();
                }
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recetas recetas = new Recetas();
                recetas.setUid(recetaseleccionada.getUid());
                recetas.setNombre(txtNombre.getText().toString().trim());
                recetas.setIngredientes(txtIngredientes.getText().toString().trim());
                recetas.setDescripcion(txtDescripcion.getText().toString().trim());
                databaseReference.child("Recetas").child(recetas.getUid()).setValue(recetas);
                Toast.makeText(CrearRecetas.this, "Guardado con exito", Toast.LENGTH_SHORT).show();
                LimpiarTexto();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recetas recetas = new Recetas();
                recetas.setUid(recetaseleccionada.getUid());
                databaseReference.child("Recetas").child(recetas.getUid()).removeValue();
                Toast.makeText(CrearRecetas.this, "Eliminado con exito", Toast.LENGTH_SHORT).show();
                LimpiarTexto();
            }
        });
    }
    private void listarDatos() {
        databaseReference.child("Recetas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recetasList.clear();
                for (DataSnapshot objSnaptshop : dataSnapshot.getChildren()){
                    Recetas recetas = objSnaptshop.getValue(Recetas.class);
                    recetasList.add(recetas);

                }
                arrayAdapterReceta = new ArrayAdapter<Recetas>(CrearRecetas.this, android.R.layout.simple_list_item_1, recetasList);
                listview.setAdapter(arrayAdapterReceta);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(CrearRecetas.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void LimpiarTexto() {
        txtNombre.setText("");
        txtIngredientes.setText("");
        txtDescripcion.setText("");
    }
}