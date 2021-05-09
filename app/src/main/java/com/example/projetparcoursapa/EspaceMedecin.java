package com.example.projetparcoursapa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EspaceMedecin extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static Button boutonCreerParcours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_medecin);
        FragmentManager fragmentManager = getSupportFragmentManager();
        boutonCreerParcours = findViewById(R.id.btn_creer_parcours);
        boutonCreerParcours.setOnClickListener(v -> {
            AjouterParcoursFragment fragment = new AjouterParcoursFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // on affiche le formulaire d'ajout
            fragmentTransaction.add(R.id.conteneur_fragment,fragment).commit();
            // on cache le bouton créer
            boutonCreerParcours.setVisibility(View.GONE);
        });
    }

    public static void setButtonVisible(){
        // on affiche le bouton créer
        boutonCreerParcours.setVisibility(View.VISIBLE);
    }
}