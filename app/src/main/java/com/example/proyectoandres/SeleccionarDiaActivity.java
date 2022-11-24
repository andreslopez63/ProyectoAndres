package com.example.proyectoandres;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.FormatMethod;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class SeleccionarDiaActivity extends AppCompatActivity {

    Button bt1011, bt1112, bt1314, bt1718, bt1819, bt1920;
    EditText etDate, etcomentarioCita;
    TextView txtprueba;
    FirebaseFirestore db;
    @ServerTimestamp
    Date daterecogido3;
    Date daterecogido, daterecogido2;
    String usuario;
    private int dia, mes, anio;
    int dias, mess, anios, horas;
    private static final String TAG = "SeleccionarDiaACtivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_dia);
        etDate=findViewById(R.id.etDate);
        etcomentarioCita = findViewById(R.id.etcomentarioCita);
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

        Bundle extras = getIntent().getExtras();
        usuario = extras.getString("nombreusuario");



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


       // String str_date="4-11-2022";
        //DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //Date date = null;
        //try {
        //    daate = (Date)formatter.parse(str_date);
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //}
        //txtprueba.setText(" Today is " +daate.getTime());
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
            etDate.setText(i2+"-"+(i1+1)+"-"+i);
            actualizarBotones (i2, i1, i);
            dias=i2;
            mess=i1;
            anios=i;
            }
        }
        ,anio,mes,dia);

        datePickerDialog.show();

    }

    public void actualizarBotones (int dia, int mes, int anio){



        bt1011.setEnabled(true);
        bt1112.setEnabled(true);
        bt1314.setEnabled(true);
        bt1718.setEnabled(true);
        bt1819.setEnabled(true);
        bt1920.setEnabled(true);
        bt1011.setBackgroundResource(R.color.purple_200);
        bt1112.setBackgroundResource(R.color.purple_200);
        bt1314.setBackgroundResource(R.color.purple_200);
        bt1718.setBackgroundResource(R.color.purple_200);
        bt1819.setBackgroundResource(R.color.purple_200);
        bt1920.setBackgroundResource(R.color.purple_200);






        //txtprueba.setText(daterecogido.toString());


        txtprueba.setText("");


        db.collection("notificaciones")
                .whereEqualTo("Anioc", anio)
                .whereEqualTo("Mesc", (mes+1))
                .whereEqualTo("Diac", dia)
                .whereEqualTo("Horac", 10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Object ss =document.getData().get("Fecha");
                                // txtprueba.setText(ss.toString());

                                txtprueba.setText(document.getId() + " => " + document.getData());
                                Toast.makeText(SeleccionarDiaActivity.this, "Hola", Toast.LENGTH_SHORT).show();

                                if(txtprueba.getText().toString()!=null){
                                   bt1011.setBackgroundColor(getResources().getColor(R.color.teal_200));
                                    bt1011.setEnabled(false);
                                }



                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        txtprueba.setText("");


        db.collection("notificaciones")
                .whereEqualTo("Anioc", anio)
                .whereEqualTo("Mesc", (mes+1))
                .whereEqualTo("Diac", dia)
                .whereEqualTo("Horac", 11)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Object ss =document.getData().get("Fecha");
                                // txtprueba.setText(ss.toString());

                                txtprueba.setText(document.getId() + " => " + document.getData());
                                Toast.makeText(SeleccionarDiaActivity.this, "Hola", Toast.LENGTH_SHORT).show();

                                if(txtprueba.getText().toString()!=null){
                                  //  bt1112.setBackgroundColor(getResources().getColor(R.color.grey));
                                    bt1112.setEnabled(false);
                                }



                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        txtprueba.setText("");


        db.collection("notificaciones")
                .whereEqualTo("Anioc", anio)
                .whereEqualTo("Mesc", (mes+1))
                .whereEqualTo("Diac", dia)
                .whereEqualTo("Horac", 13)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Object ss =document.getData().get("Fecha");
                                // txtprueba.setText(ss.toString());

                                txtprueba.setText(document.getId() + " => " + document.getData());
                                Toast.makeText(SeleccionarDiaActivity.this, "Hola", Toast.LENGTH_SHORT).show();

                                if(txtprueba.getText().toString()!=null){
                                   // bt1314.setBackgroundColor(getResources().getColor(R.color.grey));
                                    bt1314.setEnabled(false);
                                }



                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });





        txtprueba.setText("");



        db.collection("notificaciones")
                .whereEqualTo("Anioc", anio)
                .whereEqualTo("Mesc", (mes+1))
                .whereEqualTo("Diac", dia)
                .whereEqualTo("Horac", 17)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                 //Object ss =document.getData().get("Fecha");
                                // txtprueba.setText(ss.toString());

                                    txtprueba.setText(document.getId() + " => " + document.getData());


                                    if(txtprueba.getText().toString()!=null){
                                     //   bt1718.setBackgroundColor(getResources().getColor(R.color.grey));
                                        bt1718.setEnabled(false);
                                    }



                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        txtprueba.setText("");


        db.collection("notificaciones")
                .whereEqualTo("Anioc", anio)
                .whereEqualTo("Mesc", (mes+1))
                .whereEqualTo("Diac", dia)
                .whereEqualTo("Horac", 18)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Object ss =document.getData().get("Fecha");
                                // txtprueba.setText(ss.toString());

                                txtprueba.setText(document.getId() + " => " + document.getData());


                                if(txtprueba.getText().toString()!=null){
                                    bt1819.setBackgroundColor(getResources().getColor(R.color.grey));
                                    bt1819.setEnabled(false);
                                }



                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        txtprueba.setText("");


        db.collection("notificaciones")
                .whereEqualTo("Anioc", anio)
                .whereEqualTo("Mesc", (mes+1))
                .whereEqualTo("Diac", dia)
                .whereEqualTo("Horac", 19)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Object ss =document.getData().get("Fecha");
                                // txtprueba.setText(ss.toString());

                                txtprueba.setText(document.getId() + " => " + document.getData());


                                if(txtprueba.getText().toString()!=null){
                                    bt1920.setBackgroundColor(getResources().getColor(R.color.grey));
                                    bt1920.setEnabled(false);
                                }



                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });






    }


    public void agregarNotificacion(View view) {


        if(etcomentarioCita.getText().toString().isEmpty()){
            Toast.makeText(this, "Error: Agrega el asunto de la cita", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> notificacionesmap = new HashMap<>();

        notificacionesmap.put("Comentario", etcomentarioCita.getText().toString());
        notificacionesmap.put("UsuarioAsignado", usuario);
        notificacionesmap.put("Anioc", anios);
        notificacionesmap.put("Mesc", (mess+1));
        notificacionesmap.put("Diac", dias);








        //txtprueba.setText(daterecogido.toString());

        switch(view.getId()) {
            case R.id.bt1011:
                notificacionesmap.put("Horac", 10);
                horas = 10;
                break;
            case R.id.bt1112:
                notificacionesmap.put("Horac", 11);
                horas = 11;
                break;
            case R.id.bt1314:
                notificacionesmap.put("Horac", 13);
                horas = 13;
                break;
            case R.id.bt1718:
                notificacionesmap.put("Horac", 17);
                horas = 17;
                break;
            case R.id.bt1819:
                notificacionesmap.put("Horac", 18);
                horas = 18;
                break;
            case R.id.bt1920:
                notificacionesmap.put("Horac", 19);
                horas = 19;
                break;
        }
        //daterecogido3 = cal2.getTime();
        //Toast.makeText(this,   daterecogido3.toString(), Toast.LENGTH_SHORT).show();

       // Toast.makeText(this, "Fecha: "+daterecogido3.toString(), Toast.LENGTH_SHORT).show();
        //Timestamp ts = new Timestamp(Math.round(daterecogido3.getTime()/1000), 83000000);
        // Timestamp ts = Timestamp.now();


        //Toast.makeText(this, "Fecha: "+ts.getNanoseconds()+ ts.getSeconds(), Toast.LENGTH_SHORT).show();
       // notificacionesmap.put("Fecha", daterecogido3);

        agregarDocRef(notificacionesmap);






    }


    public void agregarDocRef(Map <String, Object> mapa){



        DocumentReference docRef = db.collection("notificaciones").document(usuario+anios+mess+dias+horas);
        docRef.set(mapa).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SeleccionarDiaActivity.this, ":D se pudieron guardar los nuevos datos", Toast.LENGTH_SHORT).show();



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SeleccionarDiaActivity.this, "error al guardar los datos :(", Toast.LENGTH_SHORT).show();
            }
        });

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("usuario", usuario);
        i.putExtra("irafragment","2");
        this.startActivity(i);




    }






}