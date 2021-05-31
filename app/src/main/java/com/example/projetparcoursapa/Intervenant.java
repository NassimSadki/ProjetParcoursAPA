package com.example.projetparcoursapa;

import java.util.ArrayList;

public class Intervenant extends User {

    protected static ArrayList<Intervenant> allIntervenant = new ArrayList<>();

    public Intervenant(String nom, String prenom, String email, String password) {
        super(nom, prenom, email, password);
        allIntervenant.add(this);
    }

}
