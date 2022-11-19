package com.example.proyectoandres.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoandres.R;

import java.util.ArrayList;

public class ArticuloAdapter extends RecyclerView.Adapter<ArticuloAdapter.ViewHolder> {
    Context context;
    ArrayList<Articulo> listaArticulos;

    public ArticuloAdapter(Context context, ArrayList<Articulo> listaArticulos){
        this.listaArticulos= listaArticulos;
        this.context = context;
    }
    @NonNull
    @Override
    public ArticuloAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ArticuloAdapter.ViewHolder viewHolder, int position) {

        Articulo articulo= listaArticulos.get(position);


        viewHolder.titulo.setText(listaArticulos.get(position).getTitulo());
        viewHolder.imagen.setText(listaArticulos.get(position).getImagen());
        // viewHolder.foto.setImageResource(listaArticulos.get(position).getimagenId());
    }

    @Override
    public int getItemCount() {
         return listaArticulos.size();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
      TextView titulo, imagen;


              public ViewHolder(View itemView) {
                  super(itemView);
                  titulo = (TextView) itemView.findViewById(R.id.tvfirstName);
                 imagen= (TextView) itemView.findViewById(R.id.tvLastName);


              }


    }


}
