package com.santotomas.evfinalcalendario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityLogin extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        mAuth = FirebaseAuth.getInstance();

        Button btninvitado = findViewById(R.id.btninvitado);
        Button btnregister = findViewById(R.id.btnregister);
        Button btnlogin = findViewById(R.id.btningresar);
        EditText editcorreo = findViewById(R.id.editcorreo);
        EditText editpass = findViewById(R.id.editpass);

        btninvitado.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivityHome.class);
            startActivity(intent);
        });

        btnregister.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityRegister.class);
            startActivity(intent);
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correoUser = editcorreo.getText().toString().trim();
                String passUser = editpass.getText().toString().trim();

                if (correoUser.isEmpty() || passUser.isEmpty()) {
                    Toast.makeText(ActivityLogin.this, "Ingrese los datos", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(correoUser, passUser);
                }
            }
        });
    }

    private void loginUser(String correoUser, String passUser) {
        Log.d("LoginUser", "Intentando iniciar sesión con correo: " + correoUser);
        mAuth.signInWithEmailAndPassword(correoUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("LoginUser", "Inicio de sesión exitoso");
                    finish();
                    Toast.makeText(ActivityLogin.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ActivityLogin.this, MainActivityHome.class));
                }
                else{
                    Log.e("LoginUser", "Error al iniciar sesión", task.getException());
                    Toast.makeText(ActivityLogin.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("LoginUser", "Error al iniciar sesión", e);
                Toast.makeText(ActivityLogin.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}