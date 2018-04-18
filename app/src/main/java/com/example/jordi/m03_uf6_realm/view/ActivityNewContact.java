package com.example.jordi.m03_uf6_realm.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jordi.m03_uf6_realm.R;
import com.example.jordi.m03_uf6_realm.model.Persona;

import java.util.GregorianCalendar;

import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class ActivityNewContact extends AppCompatActivity {
    TextInputEditText dni, nom, cognoms;
    RadioGroup genereRadioGroup;
    RadioButton donaRadio;
    RadioButton homeRadio;
    boolean isNewPersona;
    Realm realm;
    Persona persona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();

        isNewPersona = bundle.getBoolean("new",false);

        dni = findViewById(R.id.dni);
        nom = findViewById(R.id.nom);
        cognoms = findViewById(R.id.cognoms);
        genereRadioGroup = findViewById(R.id.radio_group_genere);
        donaRadio = findViewById(R.id.radio_button_dona);
        homeRadio = findViewById(R.id.radio_button_home);

//        CREAR PERSONA
        if (isNewPersona){
            toolbar.setTitle("Crear contacte");
            dni.setEnabled(true);
//        EDITAR PERSONA
        }else{
            toolbar.setTitle("Editar contacte");


            persona = (Persona) bundle.getSerializable("persona");

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
                            // p1.setDataNaixement(new GregorianCalendar(1992, 12, 30).getTime());
                            //p1.setEdat
                            if (homeRadio.isChecked()){
                                p1.setGenere("M");
                            }else{
                                p1.setGenere("F");
                            }
                        }
                    });
                } else {
                    //UPDATE QUERY
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Persona editPersona = realm.where(Persona.class).equalTo("dni", persona.getDni()).findFirst();

                            editPersona.setNom(nom.getText().toString());
                            editPersona.setCognom(cognoms.getText().toString());
                            // p1.setDataNaixement(new GregorianCalendar(1992, 12, 30).getTime());
                            //p1.setEdat
                            if (homeRadio.isChecked()) {
                                editPersona.setGenere("M");
                            } else {
                                editPersona.setGenere("F");
                            }

//                          TODO:CALENDARPICKER
                            realm.copyToRealmOrUpdate(persona);
                        }
                    });
                }

                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
