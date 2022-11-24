package com.example.proyectoandres;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoandres.ui.pets.PetsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {
    Button aceptar;
    Button cuentanueva;
    Button googleButton;
    Bundle bundle = new Bundle();
    EditText edittxtusuario;
    EditText editTextTextPassword;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        getSupportActionBar().hide();
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);

        bundle.putString("message","Integracion de FireBase");
        analytics.logEvent("InitScreen", bundle);
        aceptar = findViewById(R.id.aceptar);
        googleButton= findViewById(R.id.googleButton);
        cuentanueva = findViewById(R.id.cuentanueva);
        edittxtusuario =findViewById(R.id.edittxtusuario);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        //Setup
        mAuth = FirebaseAuth.getInstance();




    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }


    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);
                            Intent i = new Intent(AuthActivity.this, MainActivity.class);
                            i.putExtra("usuario", email);
                            i.putExtra("irafragment","1");
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(AuthActivity.this, "No existe un usuario con esas credenciales",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }





    public void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error autentificando al usuario");
        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog =builder.create();
        dialog.show();


    }

    public void eventoBoton(View v) {

    if (v.getId()==aceptar.getId()){
        if(edittxtusuario.getText().toString().isEmpty()){
            showAlert();
        }else if(editTextTextPassword.getText().toString().isEmpty()){
            showAlert();
        }else {
            signIn(edittxtusuario.getText().toString(), editTextTextPassword.getText().toString());
        }

    }

    if (v.getId()==googleButton.getId()){

    }

        if (v.getId()==cuentanueva.getId()){
            Intent i = new Intent(AuthActivity.this, NewLoginActivity.class);
            startActivity(i);
        }




    }

    private void reload() { }
    private void updateUI(FirebaseUser user) {

    }





}