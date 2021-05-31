package com.example.projetparcoursapa;

public class Activite {

    private String titre;
    private String description;
    private int duree; // en minutes
    private Structure structure;

    public Activite(String titre, String description, int duree){
        this.titre = titre;
        this.description = description;
        this.duree = duree;
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

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }
}
