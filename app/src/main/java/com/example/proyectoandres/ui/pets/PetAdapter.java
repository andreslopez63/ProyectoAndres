package com.example.proyectoandres.ui.pets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoandres.ModPetActivity;
import com.example.proyectoandres.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.MyViewHolder> {

    Context context;
    ArrayList<Pet> petArrayList;


    public PetAdapter(Context context, ArrayList<Pet> petArrayList) {
        this.context = context;
        this.petArrayList = petArrayList;
    }

    @NonNull
    @Override
    public PetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.view_pets, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PetAdapter.MyViewHolder viewHolder, int position) {

        Pet pet = petArrayList.get(position);
        viewHolder.nombreMascota.setText(pet.nombrePet);
        if (petArrayList.get(position).getImagenPet() != null) {
            Picasso.get().load(petArrayList.get(position).getImagenPet()).error(R.mipmap.ic_launcher_round)
                    .into(viewHolder.imagenMascota);
        }else{
            viewHolder.imagenMascota.setImageResource(R.drawable._527324);
        }




        viewHolder.setOnClickListeners(pet.getUsuario(), pet.getIdmascota());


    }

    @Override
    public int getItemCount() {
        return petArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nombreMascota;
        ImageView imagenMascota;
        CardView cv_pets;
        Context context1;
        String nombreusuario, idmascota;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context1 = itemView.getContext();
            nombreMascota = itemView.findViewById(R.id.tvnombreMascota);
            imagenMascota = itemView.findViewById(R.id.iv_Mascota);
            cv_pets = itemView.findViewById(R.id.cv_pets);

        }

        public void setOnClickListeners(String nombreUsuario, String idmascota) {
            cv_pets.setOnClickListener(this);
            this.nombreusuario = nombreUsuario;
            this.idmascota = idmascota;

        }


        @Override
        public void onClick(View view) {
            Intent i = new Intent(context1, ModPetActivity.class);
            i.putExtra("nombreusuario", nombreusuario);
            i.putExtra("idmascota", idmascota);
            context1.startActivity(i);

        }
    }
}
