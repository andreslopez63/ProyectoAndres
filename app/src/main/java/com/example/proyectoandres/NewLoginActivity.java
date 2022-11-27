package com.example.proyectoandres;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class NewLoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button aceptarlogin;
    EditText edtemail, etxtUsuario, etxtTelefono, etxtApellidos;
    EditText edtpass;
    CheckBox ckbVete;
    FirebaseFirestore db;

    private static final String TAG = "EmailPassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        aceptarlogin = findViewById(R.id.aceptarlogin);
        edtemail = findViewById(R.id.edtemail);
        edtpass = findViewById(R.id.edtpass);
        ckbVete = findViewById(R.id.ckbVete);
        etxtUsuario = findViewById(R.id.etxtUsuario);
        etxtTelefono = findViewById(R.id.etxtTelefono);
        etxtApellidos = findViewById(R.id.etxtApellidos);
        //Setup
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }


    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            crearUsuarioEnBD();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(NewLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }
    private void updateUI(FirebaseUser user) {

    }

    public void eventoBotonlogin(View view) {
        createAccount(edtemail.getText().toString(), edtpass.getText().toString());



    }

    public void crearUsuarioEnBD(){

       Map<String, Object> usuario = new HashMap<>();

        usuario.put("Nombre", etxtUsuario.getText().toString());
        usuario.put("Telefono", Integer.parseInt(etxtTelefono.getText().toString()));
        usuario.put("Apellidos", etxtApellidos.getText().toString());
        usuario.put("Veterinario", ckbVete.isChecked());

        DocumentReference docRef = db.collection("usuarios").document(edtemail.getText().toString());
        docRef.set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(NewLoginActivity.this, ":D se pudieron guardar los nuevos datos", Toast.LENGTH_SHORT).show();
                Toast.makeText(NewLoginActivity.this, "Ingresa tu nueva cuenta", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewLoginActivity.this, "error al guardar los datos :(", Toast.LENGTH_SHORT).show();
            }
        });

        Intent i = new Intent(this, AuthActivity.class);
        this.startActivity(i);


    }










}