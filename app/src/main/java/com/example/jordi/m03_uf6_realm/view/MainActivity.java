package com.example.jordi.m03_uf6_realm.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Realm.init(this);

        realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
                Persona obj1 = realm.createObject(Persona.class,"454545B");
//                obj1.setDni("45454545B");
                obj1.setNom("Jordi");
                obj1.setCognom("Solà Ceada");
                obj1.setGenere("M");
                obj1.setDataNaixement(new GregorianCalendar(1992 + 1900, 12, 30).getTime());

//                sona2 = realm.createObject(Persona.class,"585445");
//                persona2.setDni("5265358G");
//                persona2.setNom("Jordi");
//                persona2.setCognom("Solà Ceada");
//                persona2.setGenere("M");
//                persona2.setDataNaixement(new GregorianCalendar(1992 + 1900, 12, 30).getTime());
//
//                realm.copyToRealmOrUpdate(persona);
//                realm.copyToRealmOrUpdate(persona2);
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        if (id == R.id.action_settings) {
            return true;
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
