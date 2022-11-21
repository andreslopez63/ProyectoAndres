package com.example.proyectoandres.ui.pets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        View v= LayoutInflater.from(context).inflate(R.layout.view_pets, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PetAdapter.MyViewHolder viewHolder, int position) {

        Pet pet = petArrayList.get(position);
        viewHolder.nombreMascota.setText(pet.nombrePet);
        Picasso.get().load(petArrayList.get(position).getImagenPet()).error(R.mipmap.ic_launcher_round)
                .into(viewHolder.imagenMascota);
    }

    @Override
    public int getItemCount() {
        return  petArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
    TextView nombreMascota;
    ImageView imagenMascota;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreMascota = itemView.findViewById(R.id.tvnombreMascota);
            imagenMascota = itemView.findViewById(R.id.iv_Mascota);
        }
    }
}
