package com.example.proyectoandres;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddArtActivity extends AppCompatActivity {

    EditText etVerArtTitutlo, etVerArtSub, etVerArtTexto;
    ImageView imagenVerArt;
    TextView uriartImagen;
    FirebaseFirestore db;
    Button btGuardarArt;
    StorageReference storageReference;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_art);

        etVerArtTitutlo = findViewById(R.id.etVerArtTitutlo);
        etVerArtSub = findViewById(R.id.etVerArtSub);
        etVerArtTexto = findViewById(R.id.etVerArtTexto);
        imagenVerArt = findViewById(R.id.imagenVerArt);
        btGuardarArt = findViewById(R.id.btGuardarArt);
        uriartImagen = findViewById(R.id.uriartImagen);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getString("nombreusuario");
        db = FirebaseFirestore.getInstance();
        //inflobotones
        btGuardarArt.setOnClickListener(v -> uploadData());
        imagenVerArt.setOnClickListener(v -> mGetContent.launch("image/*"));

    }



    private void uploadData() {


        Map<String, Object> articulos = new HashMap<>();

        articulos.put("titulo", etVerArtTitutlo.getText().toString());

        articulos.put("subtitulo", etVerArtSub.getText().toString());

        articulos.put("texto", etVerArtTexto.getText().toString());

        articulos.put("imagen", uriartImagen.getText().toString());



        //   articulos.put("imagenPet", imagenVerArt);



        DocumentReference docRef = db.collection("articulos").document(etVerArtTitutlo.getText().toString());
        docRef.set(articulos).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddArtActivity.this, ":D se pudieron guardar los nuevos datos", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddArtActivity.this, "error al guardar los datos :(", Toast.LENGTH_SHORT).show();
            }
        });

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("usuario", usuario);
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
                                uriartImagen.setText(uri.toString());

                               Picasso.get().load(uri).resize(0, 200).error(R.mipmap.ic_launcher_round).into(imagenVerArt);
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