package com.example.projetparcoursapa;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ParcoursAPA {

    private Patient patient;
    private String titre;
    private String description;
    private String categorie;
    private ArrayList<Activite> listeActivite;

    public ParcoursAPA(Patient patient, String titre, String description, String categorie, ArrayList<Activite> listeActivite) {
        this.patient = patient;
        this.titre = titre;
        this.description = description;
        this.categorie = categorie;
        this.listeActivite = listeActivite;
        for(int i = 0; i<this.listeActivite.size(); i++){
            this.patient.addActivite(this.listeActivite.get(i));
        }
    }

    public Patient getPatient(){
        return patient;
    }

    public void setPatient(Patient patient){
        this.patient = patient;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public ArrayList<Activite> getListeActivite() {
        return listeActivite;
    }

    public void setListeActivite(ArrayList<Activite> listeActivite) {
        this.listeActivite = listeActivite;
    }

    @NonNull
    public String toString(){
        return patient+", "+titre+", "+description+", "+categorie+", "+listeActivite;
    }
}
