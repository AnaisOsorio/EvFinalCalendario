package com.santotomas.evfinalcalendario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ActivityRegister extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        Button btnrvolver = findViewById(R.id.btnrvvolver);
        Button btnregisterR2 = findViewById(R.id.btnregisterR2);
        EditText correoregister = findViewById(R.id.correoregister);
        EditText passregister = findViewById(R.id.passregister);
        EditText passregisterconfirm = findViewById(R.id.passregisterconfirm);
        EditText editnameregister = findViewById(R.id.editnameregister);

        btnrvolver.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityLogin.class);
            startActivity(intent);
        });

        btnregisterR2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameUser = editnameregister.getText().toString().trim();
                String correoUser = correoregister.getText().toString().trim();
                String passUser = passregister.getText().toString().trim();
                String passUserC = passregisterconfirm.getText().toString().trim();

                if (nameUser.isEmpty() || correoUser.isEmpty() || passUser.isEmpty() || passUserC.isEmpty()) {
                    Toast.makeText(ActivityRegister.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                }
                else if(!(passUser.equals(passUserC))){
                    Toast.makeText(ActivityRegister.this, "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
                }
                else if (passUser.length() < 6) {
                    Toast.makeText(ActivityRegister.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                }
                else{
                    registerUser(nameUser, correoUser, passUser);
                }
            }
        });
    }
    private void registerUser(String nameUser, String correoUser, String passUser) {
        mAuth.createUserWithEmailAndPassword(correoUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", id);
                    map.put("name", nameUser);
                    map.put("pass", passUser);
                    map.put("correo", passUser);

                    Toast.makeText(ActivityRegister.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                    volverlogin();

                } else {
                    Log.e("ActivityRegister", "Error al registrar usuario: " + task.getException().getMessage(), task.getException());
                    Toast.makeText(ActivityRegister.this, "Error al registrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ActivityRegister", "Error al registrarse: " + e.getMessage(), e);
                Toast.makeText(ActivityRegister.this, "Error al registrarse: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void volverlogin() {
        startActivity(new Intent(ActivityRegister.this, ActivityLogin.class));
    }

}
