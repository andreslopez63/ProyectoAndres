package com.example.proyectoandres.ui.notifications;

import static com.example.proyectoandres.ui.notifications.CalendarUtils.daysInWeekArray;
import static com.example.proyectoandres.ui.notifications.CalendarUtils.monthYearFromDate;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoandres.MainActivity;
import com.example.proyectoandres.R;
import com.example.proyectoandres.SeleccionarDiaActivity;
import com.example.proyectoandres.databinding.FragmentNotificationsBinding;
import com.example.proyectoandres.databinding.FragmentWeekViewBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment implements CalendarAdapter.OnItemListener{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private RecyclerView eventListView;

    private FirebaseAuth mAuth;
    EventAdapter eventAdapter;
    ArrayList<Notification> dailyEvents;
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
    private FragmentWeekViewBinding binding2;

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



        binding.recyclerViewNotifications.setHasFixedSize(true);
        binding.recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(getActivity()));

        db= FirebaseFirestore.getInstance();

        notificationArrayList = new ArrayList<Notification>();
        notificationAdapter = new NotificationAdapter(getActivity(), notificationArrayList);

        //
        Bundle extras = getActivity().getIntent().getExtras();
        usuario = extras.getString("usuario");

        binding.recyclerViewNotifications.setAdapter(notificationAdapter);

        //////////////////////////////////////
        CalendarUtils.selectedDate = LocalDate.now();
        binding.recyclerViewNotifications.setHasFixedSize(true);
        binding.recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(getContext()));

        dailyEvents = new ArrayList<Notification>();
        eventAdapter = new EventAdapter(getContext(), dailyEvents);



        setWeekView();

        if (getContext() instanceof MainActivity) {
            ((MainActivity) getContext()).readData(new MainActivity.FirebaseCallBack() {
                @Override
                public void onCallback(Boolean booli) {
                    if (booli) {

                       // binding.botonnuevoevento.setVisibility(View.VISIBLE);
                        binding.calendarRecyclerView.setVisibility(View.VISIBLE);
                        binding.layoutDiasdelasemana.setVisibility(View.VISIBLE);
                        binding.layoutMesaniofelchas.setVisibility(View.VISIBLE);
                        binding.recyclerViewNotifications.setAdapter(eventAdapter);
                        binding.btpopup.hide();
                    } else {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Fetching Data...");
                        progressDialog.show();



                        EventChangeListener();

                    }
                }
            });
        }



        binding.btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
                setWeekView();
            }
        });

        binding.btnalante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
                setWeekView();
            }
        });





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


    private void setWeekView() {

        binding.monthYearTV.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 7);

        binding.calendarRecyclerView.setLayoutManager(layoutManager);
        binding.calendarRecyclerView.setAdapter(calendarAdapter);
        binding.calendarRecyclerView.setHasFixedSize(true);


        setEventAdpater();
    }


    public void previousWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();

    }

    public void nextWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
        //dailyEvents.clear();

    }


    private void setEventAdpater() {
        dailyEvents.clear();
        eventAdapter.notifyDataSetChanged();
        //  eventListView.invalidateViews();
        int anioc = CalendarUtils.selectedDate.getYear();
        int mesc = CalendarUtils.selectedDate.getMonth().getValue();
        int diac = CalendarUtils.selectedDate.getDayOfMonth();

        //Toast.makeText(this, "anio=" + anioc + " mes=" + mesc + " dia=" + diac, Toast.LENGTH_SHORT).show();

        db.collection("notificaciones").whereEqualTo("Anioc", anioc).whereEqualTo("Mesc", mesc).whereEqualTo("Diac", diac)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if(dc.getType() == DocumentChange.Type.ADDED){

                                dailyEvents.add(dc.getDocument().toObject(Notification.class));


                            }
                            eventAdapter.notifyDataSetChanged();

                        }
                    }
                });








        //  ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);








    }






    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}