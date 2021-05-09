package com.example.projetparcoursapa;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AjouterParcoursFragment extends Fragment {

    LinearLayout listActivite;

    public AjouterParcoursFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ajouter_parcours, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button boutonAnnuler = view.findViewById(R.id.btn_annuler);
        boutonAnnuler.setOnClickListener(v -> {
            // on ferme le Fragment
            getFragmentManager().beginTransaction().remove(AjouterParcoursFragment.this).commit();
            // on réaffiche le bouton pour créer un parcours
            EspaceMedecin.setButtonVisible();
        });
        listActivite = view.findViewById(R.id.list_activite);
        Button boutonAjouterActivite = view.findViewById(R.id.btn_ajouter_activite);
        boutonAjouterActivite.setOnClickListener(v -> {
            // on ajoute une activité
            addView();
        });
    }

    private void addView(){
        @SuppressLint("InflateParams")
        final View activiteView = getLayoutInflater().inflate(R.layout.row_add_activite,null, false);
        ImageView imageClose = (ImageView)activiteView.findViewById(R.id.image_remove);
        imageClose.setOnClickListener(v -> {
            // on retire l'activité
            removeView(activiteView);
        });
        listActivite.addView(activiteView);
    }

    private void removeView(View view){
        listActivite.removeView(view);
    }
}