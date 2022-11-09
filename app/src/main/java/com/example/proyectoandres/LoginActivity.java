package com.example.proyectoandres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    Button aceptar;
    Button cuentanueva;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        aceptar = findViewById(R.id.aceptar);
        cuentanueva = findViewById(R.id.cuentanueva);




    }


    public void eventoBoton(View v) {

    if (v.getId()==aceptar.getId()){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }
        if (v.getId()==cuentanueva.getId()){
            Intent i = new Intent(LoginActivity.this, NewLoginActivity.class);
            startActivity(i);
        }




    }
}