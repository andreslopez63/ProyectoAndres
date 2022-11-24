package com.example.proyectoandres.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoandres.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    Context context;
    ArrayList<Notification> notificationArrayList;

    public NotificationAdapter(Context context, ArrayList<Notification> notificationArrayList) {
        this.context = context;
        this.notificationArrayList = notificationArrayList;
    }

    @NonNull
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);




        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {

        Notification notification = notificationArrayList.get(position);

        holder.txtComentario.setText(notification.Comentario);
        holder.txtUsuarioAsignado.setText(notification.UsuarioAsignado);
        holder.txtfechac.setText(notification.Anioc + " " + notification.Mesc + " " + notification.Diac + " " + notification.Horac);

    }

    @Override
    public int getItemCount() {
        return notificationArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtfechac, txtComentario, txtUsuarioAsignado;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtfechac = itemView.findViewById(R.id.txtfechac);
            txtComentario = itemView.findViewById(R.id.txtComentario);
            txtUsuarioAsignado = itemView.findViewById(R.id.txtUsuarioAsignado);

        }
    }
}
