package com.example.proyectoandres.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoandres.AddArtActivity;
import com.example.proyectoandres.AddPetActivity;
import com.example.proyectoandres.MainActivity;
import com.example.proyectoandres.R;
import com.example.proyectoandres.databinding.FragmentHomeBinding;
import com.example.proyectoandres.databinding.FragmentHomeVetBinding;
import com.example.proyectoandres.ui.pets.Pet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    ArrayList<Articulo> articuloArrayList;
    ArticuloAdapter articuloAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    String usuario;
    Boolean VetoNo, caca = false;
    private static final String TAG = "HomeFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        db = FirebaseFirestore.getInstance();

        Bundle extras = getActivity().getIntent().getExtras();
        usuario = extras.getString("usuario");




            //.collection("usuarios").document("")

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Fetching Data...");
            progressDialog.show();


            binding = FragmentHomeBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            binding.recycler.setHasFixedSize(true);
            binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));


            articuloArrayList = new ArrayList<Articulo>();
            articuloAdapter = new ArticuloAdapter(getActivity(), articuloArrayList);

            binding.recycler.setAdapter(articuloAdapter);





           // Boolean culo = cogerTipoUsuario();
            //Toast.makeText(getActivity(), culo.toString(), Toast.LENGTH_SHORT).show();
            EventChangeListener();


            readData(new FirebaseCallBack() {
                @Override
                public void onCallback(Boolean booli) {
                   if (booli){

                      binding.btArtpopup.setVisibility(View.VISIBLE);

                   }

                }
            });

        binding.btArtpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), AddArtActivity.class);
                i.putExtra("nombreusuario", usuario);
                startActivity(i);
            }
        });



        // Toast.makeText(getActivity(), caca.toString(), Toast.LENGTH_SHORT).show();


            return root;


    }





    private void EventChangeListener() {
        db.collection("articulos").orderBy("titulo", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                Articulo art1 = dc.getDocument().toObject(Articulo.class);
                                art1.setUsuario(usuario);


                                articuloArrayList.add(art1);

                            }

                            articuloAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });

    }



    private void readData(FirebaseCallBack firebaseCallBack){
        DocumentReference docRef = db.collection("usuarios").document(usuario);
        final Boolean veti ;
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());


                        Map<String, Object> mapamascota = new HashMap(document.getData());

                        if (mapamascota.get("Veterinario") !=null){
                          caca = (boolean) mapamascota.get("Veterinario");
                        }

                        VetoNo= caca;
                        // Toast.makeText(getActivity(), VetoNo.toString(), Toast.LENGTH_SHORT).show();

                    } else {
                        Log.d(TAG, "No such document");
                    }
                firebaseCallBack.onCallback(VetoNo);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }



    public interface FirebaseCallBack {
        void onCallback(Boolean booli);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}