package com.example.proyectoandres.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoandres.MainActivity;
import com.example.proyectoandres.ModPetActivity;
import com.example.proyectoandres.R;
import com.example.proyectoandres.VerArticuloActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArticuloAdapter extends RecyclerView.Adapter<ArticuloAdapter.ViewHolder> {
    Context context;
    ArrayList<Articulo> listaArticulos;
    FirebaseFirestore db;
    String usuario;
    Boolean Vetono = false;

    public ArticuloAdapter(Context context, ArrayList<Articulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticuloAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.view_articulo, parent, false);

        db = FirebaseFirestore.getInstance();
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ArticuloAdapter.ViewHolder viewHolder, int position) {

        Articulo articulo = listaArticulos.get(position);


        viewHolder.titulo.setText(listaArticulos.get(position).getTitulo());
        // viewHolder.imagen.setText(listaArticulos.get(position).getImagen());
        // viewHolder.imagen.
        if (listaArticulos.get(position).getImagen().isEmpty()) {

        } else {
            Picasso.get().load(listaArticulos.get(position).getImagen()).error(R.mipmap.ic_launcher_round)
                    .into(viewHolder.imagen);
        }

        viewHolder.setOnClickListeners(articulo.getTitulo());


        String usuario = articulo.getUsuario();

        DocumentReference docRef = db.collection("usuarios").document(usuario);

        if (context instanceof MainActivity) {
            ((MainActivity) context).readData(new MainActivity.FirebaseCallBack() {
                @Override
                public void onCallback(Boolean booli) {
                    if (booli==null)return;
                    if (booli){
                        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {


                                new MaterialAlertDialogBuilder(context)

                                        .setMessage("¿Quieres borrar el artículo?")
                                        .setPositiveButton("Ok", /* listener = */ new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                if (context instanceof MainActivity) {
                                                    ((MainActivity) context).clonguis(articulo.getTitulo());
                                                    Toast.makeText(context, articulo.getTitulo(), Toast.LENGTH_SHORT).show();
                                                    listaArticulos.remove(viewHolder.getAdapterPosition());

                                                }
                                            }
                                        })
                                        .setNegativeButton("Cancel", /* listener = */ null)
                                        .show();


                                return true;
                            }
                        });
                    }


                }
            });






            }



    }

    @Override
    public int getItemCount() {
        return listaArticulos.size();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titulo;
        ImageView imagen;
        Context context1;
        CardView cv_articulos;
        String sttitulo;

        public ViewHolder(View itemView) {
            super(itemView);
            context1 = itemView.getContext();
            titulo = itemView.findViewById(R.id.tvfirstName);
            imagen = itemView.findViewById(R.id.imagenart);
            cv_articulos = itemView.findViewById(R.id.cv_articulos);


        }

        public void setOnClickListeners(String sttitulo) {
            cv_articulos.setOnClickListener(this);
            this.sttitulo = sttitulo;


        }


        @Override
        public void onClick(View v) {
            Intent i = new Intent(context1, VerArticuloActivity.class);
            i.putExtra("tituloarticulo", sttitulo);

            context1.startActivity(i);
        }



    }


}
