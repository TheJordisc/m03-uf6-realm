package com.example.jordi.m03_uf6_realm.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Persona extends RealmObject {

    @PrimaryKey
    private String dni;
    private String nom;
    private String cognom;
    private String genere;
    private Date dataNaixement;

    public Persona() {

    }

    public Persona(String nom, String cognom, String genere, Date dataNaixement) {
        this.nom = nom;
        this.cognom = cognom;
        this.genere = genere;
        this.dataNaixement = dataNaixement;
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

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public Date getDataNaixement() {
        return dataNaixement;
    }

    public void setDataNaixement(Date dataNaixement) {
        this.dataNaixement = dataNaixement;
    }

    public int getEdat() {
        Date date1 = dataNaixement;
        Date date2 = Calendar.getInstance().getTime();
        Calendar cal1 = new GregorianCalendar(),cal2 = new GregorianCalendar();
        cal1.setTime(date1);
        cal2.setTime(date2);

        int factor=0;
        if(cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
            factor = -1;
        }

        return cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
