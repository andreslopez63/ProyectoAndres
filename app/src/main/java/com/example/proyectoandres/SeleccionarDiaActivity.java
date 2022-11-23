package com.example.proyectoandres;

import android.app.DatePickerDialog;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SeleccionarDiaActivity extends AppCompatActivity {

    Button bt1011, bt1112, bt1314, bt1718, bt1819, bt1920;
    EditText etDate;
    TextView txtprueba;
    FirebaseFirestore db;
    private int dia, mes, anio;
    private static final String TAG = "SeleccionarDiaACtivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_dia);
        etDate=findViewById(R.id.etDate);
        bt1011 = findViewById(R.id.bt1011);
        bt1112 = findViewById(R.id.bt1112);
        bt1314 = findViewById(R.id.bt1314);
        bt1718 = findViewById(R.id.bt1718);
        bt1819 = findViewById(R.id.bt1819);
        bt1920 = findViewById(R.id.bt1920);
        txtprueba = findViewById(R.id.txtprueba);

        db = FirebaseFirestore.getInstance();
        //CollectionReference notRef = db.collection("notificaciones");
        //Query query = notRef.whereEqualTo("UsuarioAsignado", "andreslp@gmail.com");
        //txtprueba.setText(query.get().toString());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2022);
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 4);
        cal.set(Calendar.HOUR_OF_DAY, 13);
        Date daate = cal.getTime();

      //  cal.set(Calendar.DAY_OF_MONTH, 4);
        //cal.set(Calendar.HOUR_OF_DAY, 0);
        //Date daate2 = cal.getTime();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       // sdf.format(daate);


        String str_date="4-11-2022";
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            daate = (Date)formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtprueba.setText(" Today is " +daate.getTime());
        //DateFormat dt = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SSS");
        //long af = 343535345;
        //Timestamp ts= Timestamp.now();
        // ts.
       // Date startAt = new GregorianCalendar(2022, 4, 18).getTime();
        //.whereEqualTo("UsuarioAsignado", "andreslp@gmail.com")
        /*
        db.collection("notificaciones").whereEqualTo("Fecha", daate)


                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //  Object ss =document.getData().get("Fecha");
                              // txtprueba.setText(ss.toString());
                                txtprueba.setText(document.getId() + " => " + document.getData());

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
                */

    }


    public void selecDate(View v) {

       final Calendar c= Calendar.getInstance();
        dia= c.get(Calendar.DAY_OF_MONTH);
        mes= c.get(Calendar.MONTH);
        anio=  c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            etDate.setText(i2+"/"+i1+"/"+i);
            }
        }
        ,dia,mes,anio);
        datePickerDialog.show();

    }






}