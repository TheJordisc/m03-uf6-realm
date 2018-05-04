package com.example.jordi.m03_uf6_realm.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jordi.m03_uf6_realm.R;
import com.example.jordi.m03_uf6_realm.adapters.MyRecyclerViewAdapter;
import com.example.jordi.m03_uf6_realm.database.MyMigration;
import com.example.jordi.m03_uf6_realm.model.Persona;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    private static final int CREATE_PERSON = 5236;
    RecyclerView rv;
    OrderedRealmCollection<Persona> dataset;
    MyRecyclerViewAdapter adapter;
    Realm realm;

    List<LinearLayout> filterLayouts;
    
    LinearLayout filterDuesEdatsLayout;
    ImageButton filterDuesEdatsApplyButton;
    TextInputEditText edatMinimaEdit;
    TextInputEditText edatMaximaEdit;

    LinearLayout filterMajorMenorLayout;
    RadioGroup radioGroupMajorMenor;
    RadioButton radioButtonMajor;
    RadioButton radioButtonMenor;
    TextInputEditText edatDeterminadaEdit;
    ImageButton filterMajorMenorApplyButton;

    LinearLayout filterGenereLayout;
    RadioGroup radioGroupGenere;
    RadioButton radioButtonDona;
    RadioButton radioButtonHome;
    ImageButton filterGenereApplyButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        filterLayouts = new ArrayList<>();
        
        filterDuesEdatsLayout = findViewById(R.id.filter_dues_edats_layout);
        filterMajorMenorLayout = findViewById(R.id.filter_major_menor_layout);
        filterGenereLayout = findViewById(R.id.filter_genere_layout);

        filterLayouts.add(filterDuesEdatsLayout);
        filterLayouts.add(filterMajorMenorLayout);
        filterLayouts.add(filterGenereLayout);

        filterDuesEdatsApplyButton = findViewById(R.id.filter_dues_edats_apply_button);
        edatMinimaEdit = findViewById(R.id.edat_minima);
        edatMaximaEdit = findViewById(R.id.edat_maxima);

        radioGroupMajorMenor = findViewById(R.id.radio_group_major_menor);
        radioButtonMajor = findViewById(R.id.radio_button_major);
        radioButtonMenor = findViewById(R.id.radio_button_menor);
        edatDeterminadaEdit = findViewById(R.id.edat_determinada);
        filterMajorMenorApplyButton = findViewById(R.id.filter_major_menor_apply_button);

        radioGroupGenere = findViewById(R.id.radio_group_genere);
        radioButtonDona = findViewById(R.id.radio_button_dona);
        radioButtonHome = findViewById(R.id.radio_button_home) ;
        filterGenereApplyButton = findViewById(R.id.filter_genere_apply_button);

        Realm.init(this);


        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(1) // Must be bumped when the schema changes
                .migration(new MyMigration()) // Migration to run instead of throwing an exception
                .build();

        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
                Persona p1 = realm.createObject(Persona.class,"4545485B");
                p1.setNom("Jordi");
                p1.setCognom("Solà");
                p1.setGenere("M");
                p1.setDataNaixement(new GregorianCalendar(1992, 12, 30).getTime());
                p1.setEdat();
                p1.setNumAstral();

                Persona p2 = realm.createObject(Persona.class,"6432545F");
                p2.setNom("Alejandro");
                p2.setCognom("Berdún");
                p2.setGenere("M");
                p2.setDataNaixement(new GregorianCalendar(1982, 1, 22).getTime());
                p2.setEdat();
                p2.setNumAstral();

                Persona p3 = realm.createObject(Persona.class,"5663454H");
                p3.setNom("María");
                p3.setCognom("Berdún");
                p3.setGenere("F");
                p3.setDataNaixement(new GregorianCalendar(1972, 1, 22).getTime());
                p3.setEdat();
                p3.setNumAstral();

                Persona p4 = realm.createObject(Persona.class,"2356258G");
                p4.setNom("Brian");
                p4.setCognom("Adalid");
                p4.setGenere("M");
                p4.setDataNaixement(new GregorianCalendar(2000, 3, 10).getTime());
                p4.setEdat();
                p4.setNumAstral();

                Persona p5 = realm.createObject(Persona.class,"2116844J");
                p5.setNom("Cristina");
                p5.setCognom("Adrián");
                p5.setGenere("F");
                p5.setDataNaixement(new GregorianCalendar(1992, 5, 36).getTime());
                p5.setEdat();
                p5.setNumAstral();
            }
        });

        rv= findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        dataset = realm.where(Persona.class).findAll();

        adapter = new MyRecyclerViewAdapter(dataset,MainActivity.this);
        adapter.setHasStableIds(false);
        rv.setAdapter(adapter);

//        realm.where(Persona.class).findAll().addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<Persona>>() {
//            @Override
//            public void onChange(RealmResults<Persona> personas, OrderedCollectionChangeSet changeSet) {
//                dataset.clear();
//                dataset.addAll(personas);
//                adapter.notifyDataSetChanged();
//            }
//        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityNewContact.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("new",true);
                intent.putExtras(bundle);
                startActivityForResult(intent,CREATE_PERSON);
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
            CharSequence colors[] = new CharSequence[] {"Entre 2 edats", "Major o menor que una edat", "Segons gènere", "Mostrar tots"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecciona una opció per a filtrar:");
            builder.setItems(colors, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case 0:
                            hideAllFilterLayouts();
                            filterDuesEdats();
                            break;
                        case 1:
                            hideAllFilterLayouts();
                            filterMajorMenor();
                            break;
                        case 2:
                            hideAllFilterLayouts();
                            filterGenere();
                            break;
                        case 3:
                            hideAllFilterLayouts();
                            filterShowAll();
                    }
                }
            });
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideAllFilterLayouts() {
        for (LinearLayout l :
                filterLayouts) {
            l.setVisibility(View.GONE);
        }
    }

    private void filterShowAll() {
        dataset = realm.where(Persona.class).findAll();
        adapter.updateData(dataset);
        adapter.notifyDataSetChanged();
    }

    private void filterGenere() {
        filterGenereLayout.setVisibility(View.VISIBLE);

        filterGenereApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioGroupGenere.getCheckedRadioButtonId() == -1) {
                    radioButtonDona.setError("Has de seleccionar una de les opcions");
                } else {
                    if (radioButtonDona.isChecked()) {
                        dataset = realm.where(Persona.class)
                                .equalTo("genere", "F")
                                .findAll();
                    } else if (radioButtonHome.isChecked()) {
                        dataset = realm.where(Persona.class)
                                .equalTo("genere", "M")
                                .findAll();
                    }

                    adapter.updateData(dataset);
                    adapter.notifyDataSetChanged();
                    filterGenereLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void filterMajorMenor() {
        filterMajorMenorLayout.setVisibility(View.VISIBLE);

        filterMajorMenorApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edatDeterminadaEdit.getText().toString().trim().equals("")) {
                    edatDeterminadaEdit.setError("El camp no pot ser buit.");
                } else if (radioGroupMajorMenor.getCheckedRadioButtonId() == -1) {
                    radioButtonMajor.setError("Has de seleccionar una de les opcions");
                } else {
                    if (radioButtonMajor.isChecked()) {
                        dataset = realm.where(Persona.class)
                                .greaterThan("edat", Integer.parseInt(edatDeterminadaEdit.getText().toString()))
                                .findAll();
                    } else if (radioButtonMenor.isChecked()) {
                        dataset = realm.where(Persona.class)
                                .lessThan("edat", Integer.parseInt(edatDeterminadaEdit.getText().toString()))
                                .findAll();
                    }

                    adapter.updateData(dataset);
                    adapter.notifyDataSetChanged();
                    filterMajorMenorLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void filterDuesEdats() {
        filterDuesEdatsLayout.setVisibility(View.VISIBLE);

        filterDuesEdatsApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edatMinimaEdit.getText().toString().trim().equals("")) {
                    edatMinimaEdit.setError("El camp no pot ser buit.");
                } else if (edatMaximaEdit.getText().toString().trim().equals("")) {
                    edatMaximaEdit.setError("El camp no pot ser buit.");
                } else {
                    dataset = realm.where(Persona.class)
                            .greaterThanOrEqualTo("edat", Integer.parseInt(edatMinimaEdit.getText().toString()))
                            .lessThanOrEqualTo("edat",Integer.parseInt(edatMaximaEdit.getText().toString()))
                            .findAll();
//                    adapter.getData().deleteAllFromRealm();
//                    adapter.getData().addAll(dataset);

//                    adapter = new MyRecyclerViewAdapter(dataset);
//                    rv.setAdapter(adapter);
                    adapter.updateData(dataset);
                    adapter.notifyDataSetChanged();
                    filterDuesEdatsLayout.setVisibility(View.GONE);
                }
            }
        });
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_PERSON && resultCode == Activity.RESULT_OK) {
            adapter.updateData(realm.where(Persona.class).findAll());
            adapter.notifyDataSetChanged();
            System.out.println("kshfojshogfhgfs");
        }
    }
}
