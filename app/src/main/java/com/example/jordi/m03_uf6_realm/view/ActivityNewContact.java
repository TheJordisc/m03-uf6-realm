package com.example.jordi.m03_uf6_realm.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jordi.m03_uf6_realm.R;
import com.example.jordi.m03_uf6_realm.model.Persona;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import io.realm.Realm;

public class ActivityNewContact extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final int CREATE_PERSON = 5236;

    TextInputEditText dni, nom, cognoms;
    RadioGroup genereRadioGroup;
    RadioButton donaRadio;
    RadioButton homeRadio;
    boolean isNewPersona;
    Realm realm;
    Persona persona;
    Button birthdayButton;
    Date birthdate;

    DatePickerDialog birthdatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        isNewPersona = bundle.getBoolean("new",false);

        dni = findViewById(R.id.dni);
        nom = findViewById(R.id.nom);
        cognoms = findViewById(R.id.cognoms);
        genereRadioGroup = findViewById(R.id.radio_group_genere);
        donaRadio = findViewById(R.id.radio_button_dona);
        homeRadio = findViewById(R.id.radio_button_home);

        birthdayButton = findViewById(R.id.birthday_button);

        birthdate = Calendar.getInstance().getTime();


//        CREAR PERSONA
        if (isNewPersona){
            getSupportActionBar().setTitle("Crear contacte");
            dni.setEnabled(true);
            Calendar today = Calendar.getInstance();

            birthdayButton.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(today.getTime()));

            birthdatePicker = new DatePickerDialog(ActivityNewContact.this, ActivityNewContact.this, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
            birthdatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());

//        EDITAR PERSONA
        }else{
            getSupportActionBar().setTitle("Editar contacte");
            Calendar birthdate = new GregorianCalendar();

            persona = (Persona) bundle.getSerializable("persona");
            birthdate.setTimeInMillis(persona.getDataNaixement().getTime());

            birthdayButton.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(birthdate.getTime()));

            birthdatePicker = new DatePickerDialog(ActivityNewContact.this, ActivityNewContact.this, birthdate.get(Calendar.YEAR), birthdate.get(Calendar.MONTH), birthdate.get(Calendar.DAY_OF_MONTH));
            birthdatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());


            dni.setEnabled(false);
            dni.setText(persona.getDni());
            nom.setText(persona.getNom());
            cognoms.setText(persona.getCognom());

            if (persona.getGenere().equals("M")) {
                homeRadio.setChecked(true);
                donaRadio.setChecked(false);
            } else {
                donaRadio.setChecked(true);
                homeRadio.setChecked(false);
            }

        }

        birthdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthdatePicker.show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm = Realm.getDefaultInstance();
                if (isNewPersona) {
                    //CREATE QUERY
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Persona p1 = realm.createObject(Persona.class,dni.getText().toString());
                            p1.setNom(nom.getText().toString());
                            p1.setCognom(cognoms.getText().toString());
                            p1.setDataNaixement(birthdate);
                            p1.setEdat();
                            if (homeRadio.isChecked()){
                                p1.setGenere("M");
                            }else{
                                p1.setGenere("F");
                            }
                        }
                    });
                } else {
                    //UPDATE QUERY
//                    realm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {

                    Persona editPersona = new Persona();
                    editPersona.setDataNaixement(birthdate);
                    editPersona.setEdat();
                    editPersona.setDni(persona.getDni());
                    editPersona.setNom(nom.getText().toString());
                    editPersona.setCognom(cognoms.getText().toString());
                    // p1.setDataNaixement(new GregorianCalendar(1992, 12, 30).getTime());
                    //p1.setEdat
                    if (homeRadio.isChecked()) {
                        editPersona.setGenere("M");
                    } else {
                        editPersona.setGenere("F");
                    }

                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(editPersona);

                    realm.commitTransaction();
//                    });
                }

                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK,resultIntent);
//                finishActivity(CREATE_PERSON);
                finish();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Date birthdate = new GregorianCalendar(year,month,dayOfMonth).getTime();
        birthdayButton.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(birthdate));
        this.birthdate = birthdate;
    }
}
