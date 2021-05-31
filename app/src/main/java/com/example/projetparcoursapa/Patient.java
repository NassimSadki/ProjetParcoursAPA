package com.example.projetparcoursapa;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Patient extends User {

    private ArrayList<Seance> agenda;
    private final ArrayList<Activite> activiteEnAttente;
    private final ArrayList<Activite> activiteAssignee;
    protected static ArrayList<Patient> allPatient = new ArrayList<>();

    public Patient(String nom, String prenom, String email, String password) {
        super(nom,prenom,email,password);
        this.agenda = new ArrayList<>();
        this.activiteEnAttente = new ArrayList<>();
        this.activiteAssignee = new ArrayList<>();
        allPatient.add(this);
    }

    public String getNom() {
        return super.getNom();
    }

    public void setNom(String nom) {
        super.setNom(nom);
    }

    public String getPrenom() {
        return super.getPrenom();
    }

    public void setPrenom(String prenom) {
        super.setPrenom(prenom);
    }

    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    public String getPassword() {
        return super.getPassword();
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    public ArrayList<Seance> getAgenda() {
        return agenda;
    }

    public void setAgenda(ArrayList<Seance> agenda) {
        this.agenda = agenda;
    }

    public void addSeance(Seance seance){
        this.agenda.add(seance);
    }

    public void addActivite(Activite activite){
        this.activiteEnAttente.add(activite);
    }

    public void removeActivite(Activite activite){
        this.activiteEnAttente.remove(activite);
        this.activiteAssignee.add(activite);
    }

    public ArrayList<Activite> getActiviteEnAttente(){
        return this.activiteEnAttente;
    }

    public ArrayList<Activite> getActiviteAssignee(){
        return this.activiteAssignee;
    }

    @NonNull
    public String toString(){
        return super.getPrenom() + " " + super.getNom();
    }

}
