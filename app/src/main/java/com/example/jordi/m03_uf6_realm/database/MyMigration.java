package com.example.jordi.m03_uf6_realm.database;

import com.example.jordi.m03_uf6_realm.model.Persona;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.annotations.Required;

//Classe que el seu mètode s'executarà en efectuar la migració
public class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        //Agafar esquema actual
        RealmSchema schema = realm.getSchema();

        //Si estem migrant des de la versió 0
        if (oldVersion == 0) {

            //A partir de l'esquema, agafa la classe (entitat) Persona
            schema.get("Persona")
                    .addField("numAstral", Integer.class,FieldAttribute.REQUIRED) //Afegir camp
                    .addIndex("edat") //Afegir nou índex
                    .transform(new RealmObjectSchema.Function() { //Actualitzar instàncies anteriors de la BBDD
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            //A cada objecte de Realm, establir un nou camp Int per a numAstral
                            obj.setInt("numAstral",Persona.calculateNumAstral(obj.getDate("dataNaixement")));
                        }
                    });

            //Sumar 1 a la versió actual del Realm
            oldVersion++;
        }
    }
}