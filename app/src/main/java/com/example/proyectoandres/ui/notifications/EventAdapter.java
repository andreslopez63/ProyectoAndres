package com.example.proyectoandres.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoandres.R;
import com.example.proyectoandres.ui.pets.Pet;
import com.example.proyectoandres.ui.pets.PetAdapter;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    Context context;
    ArrayList<Notification> notArrayList;

    public EventAdapter(Context context, ArrayList<Notification> notArrayList) {
        this.context = context;
        this.notArrayList = notArrayList;
    }





    @NonNull
    @Override
    public EventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.event_cell, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.MyViewHolder holder, int position) {

        Notification notification = notArrayList.get(position);
        String eventTitle = notification.getUsuarioAsignado() +" "+ notification.getComentario();
        holder.eventCellTV.setText(eventTitle);
    }

    @Override
    public int getItemCount() {
        return notArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView eventCellTV ;
        Context context1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context1 = itemView.getContext();
            eventCellTV = itemView.findViewById(R.id.eventCellTV);
        }
    }
}
