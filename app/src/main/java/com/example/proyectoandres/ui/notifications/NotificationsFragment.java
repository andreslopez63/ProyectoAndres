package com.example.proyectoandres.ui.notifications;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoandres.R;
import com.example.proyectoandres.SeleccionarDiaActivity;
import com.example.proyectoandres.databinding.FragmentNotificationsBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button btpedircita, btllamar;
    private Button btpopup;
    String usuario;
    RecyclerView recyclerView;
    ArrayList<Notification> notificationArrayList;
    NotificationAdapter notificationAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        binding.btpopup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                createNewContactDialog();
            }
        });
        View root = binding.getRoot();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        binding.recyclerViewNotifications.setHasFixedSize(true);
        binding.recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(getActivity()));

        db= FirebaseFirestore.getInstance();

        notificationArrayList = new ArrayList<Notification>();
        notificationAdapter = new NotificationAdapter(getActivity(), notificationArrayList);

        //
        Bundle extras = getActivity().getIntent().getExtras();
        usuario = extras.getString("usuario");

        binding.recyclerViewNotifications.setAdapter(notificationAdapter);
        EventChangeListener();

        return root;
    }

        private void EventChangeListener(){

        db.collection("notificaciones").whereEqualTo("UsuarioAsignado", usuario)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null){
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Log.e("Firestre error", error.getMessage());
                        return;
                    }
                    for (DocumentChange dc : value.getDocumentChanges()){

                        if(dc.getType() == DocumentChange.Type.ADDED){

                            notificationArrayList.add(dc.getDocument().toObject(Notification.class));

                        }
                        notificationAdapter.notifyDataSetChanged();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                    }
                });
        }








    public void createNewContactDialog() {
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        btpedircita= (Button) contactPopupView.findViewById(R.id.btpedircita);
        btllamar = (Button) contactPopupView.findViewById(R.id.btllamar);
        btpedircita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SeleccionarDiaActivity.class);
                i.putExtra("nombreusuario", usuario);
                startActivity(i);
            }
        });

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}