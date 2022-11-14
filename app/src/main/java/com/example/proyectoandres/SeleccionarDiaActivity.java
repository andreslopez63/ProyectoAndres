package com.example.proyectoandres;

import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class SeleccionarDiaActivity extends AppCompatActivity {


    EditText etDate;
    private int dia, mes, anio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_dia);
        etDate=(EditText) findViewById(R.id.etDate);



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