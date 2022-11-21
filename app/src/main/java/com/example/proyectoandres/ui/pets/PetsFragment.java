package com.example.proyectoandres.ui.pets;

import android.app.ProgressDialog;
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
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.proyectoandres.R;
import com.example.proyectoandres.databinding.FragmentPetsBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PetsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Pet> petArrayList;
    PetAdapter petAdapter;
    FirebaseFirestore db;
    private FragmentPetsBinding binding;
    ProgressDialog progressDialog;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        //
        binding = FragmentPetsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Toast.makeText(getActivity(), "Usuario: "+ getArguments().getString("amount"), Toast.LENGTH_LONG).show();
      //  Toast.makeText(getActivity(), "Usuario: "+ getArguments().getString("privacyPolicyLink"), Toast.LENGTH_LONG).show();

        //
        binding.recyclerPetsView.setHasFixedSize(true);
        binding.recyclerPetsView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //
        db = FirebaseFirestore.getInstance();
        petArrayList = new ArrayList<Pet>();
        petAdapter = new PetAdapter(getActivity(), petArrayList);
        binding.recyclerPetsView.setAdapter(petAdapter);

        //
        Bundle extras = getActivity().getIntent().getExtras();
        String usuario = extras.getString("usuario");
        binding.txtusuarioMascota.setText(usuario);

        //
        EventChangeListener();
        return root;
    }

    private void EventChangeListener() {
        Bundle extras = getActivity().getIntent().getExtras();
        String usuario = extras.getString("usuario");

        db.collection("usuarios").document(usuario).collection("mascotas").orderBy("nombrePet", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error != null){

                    if(progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Firestore error", error.getMessage());
                    return;
                }
                for(DocumentChange dc : value.getDocumentChanges()){

                    if(dc.getType() == DocumentChange.Type.ADDED){
                        Pet pet1 = dc.getDocument().toObject(Pet.class);
                        pet1.setUsuario(usuario);
                        pet1.setIdmascota(dc.getDocument().getId());

                        petArrayList.add(pet1);

                        Log.e("cosas",dc.getDocument().toString());
                    }
                    petAdapter.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}