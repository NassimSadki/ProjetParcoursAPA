package com.example.projetparcoursapa;

import java.util.ArrayList;

public class Structure {

    private String nom;
    private String discipline;

    public Structure(String nom, String discipline) {
        this.nom = nom;
        this.discipline = discipline;
    }

    public Structure(){
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

}
