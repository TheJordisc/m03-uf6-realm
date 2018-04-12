package com.example.jordi.m03_uf6_realm.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jordi.m03_uf6_realm.R;
import com.example.jordi.m03_uf6_realm.model.Persona;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        Persona persona = new Persona();
        persona.setNom("Jordi");
        persona.setCognom("Solà Ceada");
        persona.setGenere("M");
        persona.setDataNaixement(new GregorianCalendar(1992 + 1900, 12, 30).getTime());

        realm.createObject(Persona.class, persona.getDni());


        realm.commitTransaction();
    }
}
