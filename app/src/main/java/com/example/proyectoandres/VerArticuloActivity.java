package com.example.proyectoandres;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class VerArticuloActivity extends AppCompatActivity {
    TextView txtVerArtTitutlo, txtVerArtTexto, txtVerArtSub;
    ImageView imagenVerArt;
    Button btSalir;
    FirebaseFirestore db;
    String tituloarticulo;
    private static final String TAG = "VerArticuloActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_articulo);
        txtVerArtTitutlo = findViewById(R.id.txtVerArtTitutlo);
        txtVerArtTexto = findViewById(R.id.txtVerArtTexto);
        imagenVerArt = findViewById(R.id.imagenVerArt);
        btSalir = findViewById(R.id.btSalir);
        txtVerArtSub = findViewById(R.id.txtVerArtSub);
        Bundle extras = getIntent().getExtras();
        tituloarticulo = extras.getString("tituloarticulo");





        db = FirebaseFirestore.getInstance();

        mostrarArt();

    }

    public void mostrarArt(){
        DocumentReference docRef = db.collection("articulos").document(tituloarticulo);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());


                        Map<String, Object> mapaarticulo = new HashMap(document.getData());
                        txtVerArtTitutlo.setText(mapaarticulo.get("titulo").toString());
                        txtVerArtSub.setText(mapaarticulo.get("subtitulo").toString());
                        txtVerArtTexto.setText(mapaarticulo.get("texto").toString());


                        Picasso.get().load(mapaarticulo.get("imagen").toString()).resize(0, 400).centerCrop().error(R.mipmap.ic_launcher_round)
                                .into(imagenVerArt);



                        //txturiimagen.setText((CharSequence) uri);

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