package com.example.jordi.m03_uf6_realm.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Persona extends RealmObject {

    public enum Genere {
        M,
        F
    }

    @PrimaryKey
    private String dni;
    private String nom;
    private String cognom;
    private Genere genere;
    private Calendar dataNaixement;
    private Locale nacionalitat;


    public Persona() {

    }

    public Persona(String nom, String cognom, Genere genere, Calendar dataNaixement, Locale nacionalitat) {
        this.nom = nom;
        this.cognom = cognom;
        this.genere = genere;
        this.dataNaixement = dataNaixement;
        this.nacionalitat = nacionalitat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public Genere getGenere() {
        return genere;
    }

    public void setGenere(Genere genere) {
        this.genere = genere;
    }

    public Calendar getDataNaixement() {
        return dataNaixement;
    }

    public void setDataNaixement(Calendar dataNaixement) {
        this.dataNaixement = dataNaixement;
    }

    public int getEdat() {
        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dataNaixement.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dataNaixement.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        return age;
    }

    public Locale getNacionalitat() {
        return nacionalitat;
    }

    public void setNacionalitat(Locale nacionalitat) {
        this.nacionalitat = nacionalitat;
    }
}
