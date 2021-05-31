package com.example.projetparcoursapa;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class Seance {

    private Activite activite;
    private Calendar startTime;
    private Calendar endTime;

    public Seance(Activite activite, Calendar startTime){
        this.activite = activite;
        this.startTime = startTime;
        // convertir la durée de l'activé (minutes) en heures et minutes
        int hours = this.activite.getDuree() / 60;
        int minutes = this.activite.getDuree() % 60;
        // ajouter la durée à l'heure de début pour obtenir l'heure de fin
        this.endTime = (Calendar) startTime.clone();
        this.endTime.add(Calendar.HOUR, hours);
        this.endTime.add(Calendar.MINUTE, minutes);
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
        // convertir la durée de l'activé (minutes) en heures et minutes
        int hours = this.activite.getDuree() / 60;
        int minutes = this.activite.getDuree() % 60;
        // ajouter la durée à l'heure de début pour obtenir l'heure de fin
        this.endTime = (Calendar) startTime.clone();
        this.endTime.add(Calendar.HOUR, hours);
        this.endTime.add(Calendar.MINUTE, minutes);
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    @NonNull
    public String toString(){
        return activite+", "+startTime+", "+endTime;
    }
}
