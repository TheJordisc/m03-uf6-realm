package com.example.jordi.m03_uf6_realm.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jordi.m03_uf6_realm.R;
import com.example.jordi.m03_uf6_realm.model.Persona;

public class ActivityNewContact extends AppCompatActivity {
    TextInputEditText dni, nom, cognoms, genere;
    boolean newPersona = getIntent().getBooleanExtra("new",false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (newPersona){
            toolbar.setTitle("Editar un contacte existent");
            Persona persona = (Persona) getIntent().getSerializableExtra("persona");
            dni = findViewById(R.id.dni);
            nom = findViewById(R.id.nom);
            cognoms = findViewById(R.id.cognoms);
            genere = findViewById(R.id.genere);

            dni.setText(persona.getDni());
            nom.setText(persona.getNom());
            cognoms.setText(persona.getCognom());
            genere.setText(persona.getGenere().toUpperCase());
        }else{
            toolbar.setTitle("Afegir un nou contacte");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
