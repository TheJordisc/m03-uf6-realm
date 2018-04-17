package com.example.jordi.m03_uf6_realm.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.jordi.m03_uf6_realm.R;
import com.example.jordi.m03_uf6_realm.adapters.MyRecyclerViewAdapter;
import com.example.jordi.m03_uf6_realm.model.Persona;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    private List<Persona> itemList;
    MyRecyclerViewAdapter adapter;
    Realm realm;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Realm.init(this);

        realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
                Persona obj1 = realm.createObject(Persona.class,"4545485B");
                obj1.setNom("Jordi");
                obj1.setCognom("Solà");
                obj1.setGenere("M");
                obj1.setDataNaixement(new GregorianCalendar(1992, 12, 30).getTime());

                Persona obj2 = realm.createObject(Persona.class,"6432545F");
                obj2.setNom("Alejandro");
                obj2.setCognom("Berdún");
                obj2.setGenere("M");
                obj2.setDataNaixement(new GregorianCalendar(1982, 1, 22).getTime());
            }
        });

        rv= findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();

        Persona results2 = realm.where(Persona.class).findFirst();
        System.out.println(results2);


        RealmResults<Persona> results = realm.where(Persona.class).findAll();
        itemList.addAll(results);

        adapter = new MyRecyclerViewAdapter(results);
        rv.setAdapter(adapter);

        realm.where(Persona.class).findAll().addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<Persona>>() {
            @Override
            public void onChange(RealmResults<Persona> personas, OrderedCollectionChangeSet changeSet) {
                itemList.clear();
                itemList.addAll(personas);
                adapter.notifyDataSetChanged();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityNewContact.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.filter_button) {
            CharSequence colors[] = new CharSequence[] {"2 edats", "> gran edat", "< edat", "genere"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecciona una opció per listar");
            builder.setItems(colors, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case 0:

                            break;
                        case 1:

                            break;
                        case 2:

                            break;
                        case 3:

                            break;
                    }
                }
            });
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }


//    private void setDB() {
//        realm.beginTransaction();
//
//        realm.deleteAll();
//        Persona persona = new Persona();
//        persona.setDni("45454545B");
//        persona.setNom("Jordi");
//        persona.setCognom("Solà Ceada");
//        persona.setGenere("M");
//        persona.setDataNaixement(new GregorianCalendar(1992 + 1900, 12, 30).getTime());
//
//        Persona persona2 = new Persona();
//        persona2.setDni("5265358G");
//        persona2.setNom("Jordi");
//        persona2.setCognom("Solà Ceada");
//        persona2.setGenere("M");
//        persona2.setDataNaixement(new GregorianCalendar(1992 + 1900, 12, 30).getTime());
//
//        realm.createObject(Persona.class, persona.getDni());
//        realm.createObject(Persona.class, persona2.getDni());
//
//        realm.commitTransaction();
//    }
}
