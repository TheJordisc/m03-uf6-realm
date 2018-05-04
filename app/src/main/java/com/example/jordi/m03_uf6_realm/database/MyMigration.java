package com.example.jordi.m03_uf6_realm.database;

import com.example.jordi.m03_uf6_realm.model.Persona;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.annotations.Required;

public class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            schema.get("Persona")
                    .addField("numAstral", Integer.class,FieldAttribute.REQUIRED)
                    .addIndex("edat")
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.setInt("numAstral",Persona.calculateNumAstral(obj.getDate("dataNaixement")));
                        }
                    });
            oldVersion++;
        }
    }
}