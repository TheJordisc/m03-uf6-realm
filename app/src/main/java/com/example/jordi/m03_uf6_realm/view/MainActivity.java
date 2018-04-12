package com.example.jordi.m03_uf6_realm.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jordi.m03_uf6_realm.R;
import com.example.jordi.m03_uf6_realm.model.Persona;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        Persona persona = realm.createObject(Persona.class);
        persona.setNom("Jordi");
        persona.setCognom("Solà Ceada");
        persona.setGenere(Persona.Genere.M);
        persona.setNacionalitat(new Locale("es", "ES"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(1992,12,30);
        persona.setDataNaixement(calendar);

        realm.commitTransaction();
    }
}
