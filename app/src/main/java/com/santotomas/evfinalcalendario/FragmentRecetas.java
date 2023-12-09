package com.santotomas.evfinalcalendario;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FragmentRecetas extends Fragment {

    private List<Recetas> recetasList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapterReceta;
    ListView listview;
    Button btnCrearReceta;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recetas, container, false);
        listview = view.findViewById(R.id.listview);
        btnCrearReceta = view.findViewById(R.id.btnCrearReceta);

        iniciarFirebase();
        listarDatos();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recetas recetasSeleccionada = recetasList.get(position);
                mostrarDetalles(recetasSeleccionada);
            }
        });

        btnCrearReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CrearRecetas.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void listarDatos() {
        databaseReference.child("Recetas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recetasList.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Recetas recetas = objSnapshot.getValue(Recetas.class);
                    if (recetas != null) {
                        recetasList.add(recetas);
                    }
                }

                List<String> nombresRecetas = obtenerNombresRecetas(recetasList);
                arrayAdapterReceta = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, nombresRecetas);
                listview.setAdapter(arrayAdapterReceta);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private List<String> obtenerNombresRecetas(List<Recetas> recetasList) {
        List<String> nombres = new ArrayList<>();
        for (Recetas recetas : recetasList){
            nombres.add(recetas.getNombre());
        }
        return nombres;
    }

    private void mostrarDetalles(Recetas receta) {
        Intent intent = new Intent(getActivity(), MostrarReceta.class);
        intent.putExtra("nombreReceta", receta.getNombre());
        intent.putExtra("ingredientesReceta", receta.getIngredientes());
        intent.putExtra("descripcionReceta", receta.getDescripcion());
        startActivity(intent);
    }
    private void iniciarFirebase() {
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}