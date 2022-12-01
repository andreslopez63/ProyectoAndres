package com.example.proyectoandres;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ModPetActivity extends AppCompatActivity {
    EditText cnombreMascota, ctipoMascota, csexoMascota, cfechanacMascota, crazaMascota;
    TextView txtModNombreUsuario, txturiimagen;
    Button btCambiarMascota, cambiarimagen;
    FirebaseFirestore db;
    ImageView cimagenMascota;
    StorageReference storageReference;
    Uri urimascota;
    String usuario, idmascota, nuevaURL;
    private FirebaseAuth mAuth;
    private static final String TAG = "ModPetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_pet);

        cnombreMascota = findViewById(R.id.cnombreMascota);
        ctipoMascota = findViewById(R.id.ctipoMascota);
        txtModNombreUsuario = findViewById(R.id.txtModNombreUsuario);
        cimagenMascota = findViewById(R.id.cimagenMascota);
        btCambiarMascota = findViewById(R.id.btCambiarMascota);
        csexoMascota = findViewById(R.id.csexoMascota);
        cfechanacMascota = findViewById(R.id.cfechanacMascota);
        crazaMascota = findViewById(R.id.crazaMascota);
        txturiimagen = findViewById(R.id.txturiimagen);
        cambiarimagen = findViewById(R.id.cambiarimagen);

        //Recibo datos de la mascota para poder mostrarla por pantalla
        Bundle extras = getIntent().getExtras();
        usuario = extras.getString("nombreusuario");
        idmascota = extras.getString("idmascota");
        txtModNombreUsuario.setText(usuario);

        //inflobotones
        btCambiarMascota.setOnClickListener(v -> uploadData());
        cambiarimagen.setOnClickListener(v -> mGetContent.launch("image/*"));

        //inicio la base de datos
        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(this, mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

        actualizalosdatos();
    }

    //Recojo los datos de la base de datos para mostrar lo que tienen
    protected void actualizalosdatos() {
        DocumentReference docRef = db.collection("usuarios").document(usuario).collection("mascotas").document(idmascota);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());


                        Map<String, Object> mapamascota = new HashMap(document.getData());
                        cnombreMascota.setText(mapamascota.get("nombrePet").toString());
                        ctipoMascota.setText(mapamascota.get("ctipoMascota").toString());
                        csexoMascota.setText(mapamascota.get("csexoMascota").toString());
                        crazaMascota.setText(mapamascota.get("crazaMascota").toString());
                        Picasso.get().load(mapamascota.get("imagenPet").toString()).resize(0, 200).error(R.mipmap.ic_launcher_round)
                                .into(cimagenMascota);
                        txturiimagen.setText(mapamascota.get("imagenPet").toString());


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


    //actualizo los cambios hecho en la mascota
    private void uploadData() {


        Map<String, Object> mascotas = new HashMap<>();

        mascotas.put("nombrePet", cnombreMascota.getText().toString());

        mascotas.put("ctipoMascota", ctipoMascota.getText().toString());

        mascotas.put("csexoMascota", csexoMascota.getText().toString());

        mascotas.put("crazaMascota", crazaMascota.getText().toString());

        mascotas.put("imagenPet", txturiimagen.getText().toString());


        DocumentReference docRef = db.collection("usuarios").document(usuario).collection("mascotas").document(idmascota);
        docRef.update(mascotas).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ModPetActivity.this, ":D se pudieron guardar los nuevos datos", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ModPetActivity.this, "error al guardar los datos :(", Toast.LENGTH_SHORT).show();
            }
        });

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("usuario", mAuth.getCurrentUser().getEmail());
        i.putExtra("irafragment","2");
        this.startActivity(i);
    }

    //método para llamar el explorador de imágenes
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        cambiarImagen(result);
                        // cimagenMascota.setImageURI(result);
                        // urimascota = result;
                        // haztoast();
                    }
                }
            }
    );

    //una vez seleccionada la imagen en mGetContent nos disponemos a cambiarla
    private void cambiarImagen(Uri result) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date now = new Date();
        String filename = formatter.format(now);


        storageReference = FirebaseStorage.getInstance().getReference("images/" + filename);
        storageReference.putFile(result)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                txturiimagen.setText(uri.toString());

                                Picasso.get().load(uri).resize(0, 200).error(R.mipmap.ic_launcher_round)
                                        .into(cimagenMascota);
                                // devolverURL(uri);
                                // Toast.makeText(ModPetActivity.this, nuevaURL, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                    }
                });
    }


}