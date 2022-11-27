package com.example.proyectoandres.ui.pets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.proyectoandres.AddPetActivity;
import com.example.proyectoandres.AuthActivity;
import com.example.proyectoandres.MainActivity;
import com.example.proyectoandres.R;
import com.example.proyectoandres.databinding.FragmentPetsBinding;
import com.example.proyectoandres.databinding.FragmentPetsVetBinding;
import com.example.proyectoandres.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PetsFragment extends Fragment {

    Spinner spinnerUsuarios;
    RecyclerView recyclerView;
    ArrayList<Pet> petArrayList;
    PetAdapter petAdapter;
    FirebaseFirestore db;
    private FragmentPetsBinding binding;
    //private FragmentPetsVetBinding binding2;
    ProgressDialog progressDialog;
    String usuarioSeleccionado = "";
    String usuario;
    String usuarioacambiar;


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
        usuario = extras.getString("usuario");
        usuarioacambiar = extras.getString("usuarioacambiar");
        binding.txtusuarioMascota.setText(usuario);






        //
        binding.btagregarmascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), AddPetActivity.class);
                i.putExtra("nombreusuario", usuario);
                i.putExtra("nombreusuarioacambiar", usuarioacambiar);
                startActivity(i);
            }
        });

        if (getContext() instanceof MainActivity) {
            ((MainActivity) getContext()).readData(new MainActivity.FirebaseCallBack() {
                @Override
                public void onCallback(Boolean booli) {
                    if (booli) {
                        cargarUsuarios();
                    } else {
                        EventChangeListener();
                        binding.spinnerUsuarios.setVisibility(View.GONE);
                    }
                }
            });
        }


        return root;
    }

    public void cargarUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        db.collection("usuarios").whereEqualTo("Veterinario", false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                Usuario usuario =   dc.getDocument().toObject(Usuario.class);
                                String nombre = usuario.getNombre();
                                String apellidos = usuario.getApellidos();
                                int telefono = usuario.getTelefono();
                                Boolean veterinario = usuario.getVeterinario();
                                String idusuario = dc.getDocument().getId();

                                usuarios.add(new Usuario(nombre, apellidos, telefono, veterinario, idusuario));

                                Log.e("cosas", dc.getDocument().toString());

                            }
                            ArrayAdapter<Usuario> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, usuarios);

                            binding.spinnerUsuarios.setAdapter(arrayAdapter);
                            binding.spinnerUsuarios.setBackgroundColor(getResources().getColor(R.color.morado_app));

                            binding.spinnerUsuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    Usuario usu1 = (Usuario) parent.getItemAtPosition(position);
                                    usuarioacambiar = usu1.getIdusuario();
                                    EventChangeListenerVet(usu1.getIdusuario());

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });

    }







    private void EventChangeListener() {
        Bundle extras = getActivity().getIntent().getExtras();
        String usuario = extras.getString("usuario");

        db.collection("usuarios").document(usuario).collection("mascotas").orderBy("nombrePet", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Pet pet1 = dc.getDocument().toObject(Pet.class);
                                pet1.setUsuario(usuario);
                                pet1.setIdmascota(dc.getDocument().getId());

                                petArrayList.add(pet1);

                                Log.e("cosas", dc.getDocument().toString());
                            }
                            petAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }

                    }
                });
    }

    private void EventChangeListenerVet(String usuario) {
        petArrayList.clear();

        db.collection("usuarios").document(usuario).collection("mascotas").orderBy("nombrePet", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Pet pet1 = dc.getDocument().toObject(Pet.class);
                                pet1.setUsuario(usuario);
                                pet1.setIdmascota(dc.getDocument().getId());

                                petArrayList.add(pet1);

                                Log.e("cosas", dc.getDocument().toString());
                            }
                            petAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
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