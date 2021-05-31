package com.example.projetparcoursapa;

import java.util.ArrayList;

public class Medecin extends User {

    protected static ArrayList<Medecin> allMedecin = new ArrayList<>();

    public Medecin(String nom, String prenom, String email, String password) {
        super(nom, prenom, email, password);
        allMedecin.add(this);
    }
}
