package com.example.proyectoandres;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ModPetActivity extends AppCompatActivity {
    EditText cnombreMascota, ctipoMascota, csexoMascota, cfechanacMascota, crazaMascota;
    TextView txtModNombreUsuario;
    Button btCambiarMascota;
    FirebaseFirestore db;
    private static final String TAG = "ModPetActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_pet);

        cnombreMascota = findViewById(R.id.cnombreMascota);
        ctipoMascota = findViewById(R.id.ctipoMascota);
        txtModNombreUsuario = findViewById(R.id.txtModNombreUsuario);

        Bundle extras = getIntent().getExtras();
        String usuario = extras.getString("nombreusuario");
        String idmascota = extras.getString("idmascota");

        txtModNombreUsuario.setText(usuario);
        db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("usuarios").document(usuario).collection("mascotas").document(idmascota);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, String> mapamascota = new HashMap(document.getData());
                        cnombreMascota.setText(mapamascota.get("nombrePet"));



                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });














    }
}