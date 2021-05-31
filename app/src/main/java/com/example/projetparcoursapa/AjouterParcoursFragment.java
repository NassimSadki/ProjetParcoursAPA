package com.example.projetparcoursapa;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class AjouterParcoursFragment extends Fragment implements AdapterView.OnItemSelectedListener, TextWatcher {

    private LinearLayout listActivite;
    private Patient patient;
    private EditText inputTitreParcours;
    private EditText inputDescriptionParcours;
    private EditText inputCategorie;
    private EditText inputTitreActivite;
    private EditText inputDescriptionActivite;
    private EditText inputDuree;
    private ArrayList<Activite> arraylistActivite;

    public AjouterParcoursFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ajouter_parcours, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Spinner spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<Patient> ad = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, Patient.allPatient);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        arraylistActivite = new ArrayList<>();

        inputTitreParcours = view.findViewById(R.id.titre_parcours);
        inputTitreParcours.addTextChangedListener(this);
        inputDescriptionParcours = view.findViewById(R.id.description_parcours);
        inputDescriptionParcours.addTextChangedListener(this);
        inputCategorie = view.findViewById(R.id.categorie);
        inputCategorie.addTextChangedListener(this);
        inputTitreActivite = view.findViewById(R.id.titre_activite);
        inputTitreActivite.addTextChangedListener(this);
        inputDescriptionActivite = view.findViewById(R.id.description_activite);
        inputDescriptionActivite.addTextChangedListener(this);
        inputDuree = view.findViewById(R.id.duree);
        inputDuree.addTextChangedListener(this);

        Button boutonAnnuler = view.findViewById(R.id.btn_annuler);
        boutonAnnuler.setOnClickListener(v -> {
            // on ferme le Fragment
            assert getFragmentManager() != null;
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
        Button boutonValider = view.findViewById(R.id.btn_valider);
        boutonValider.setOnClickListener(v -> {
           if(isValid()){
               //créer Parcours APA
               new ParcoursAPA(
                   patient,
                   inputTitreParcours.getText().toString(),
                   inputDescriptionParcours.getText().toString(),
                   inputCategorie.getText().toString(),
                   arraylistActivite
               );
               // on ferme le Fragment
               assert getFragmentManager() != null;
               getFragmentManager().beginTransaction().remove(AjouterParcoursFragment.this).commit();
               // on réaffiche le bouton pour créer un parcours
               EspaceMedecin.setButtonVisible();
           }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        patient = Patient.allPatient.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s == inputTitreParcours.getEditableText()){
            if (inputTitreParcours.getText().toString().isEmpty()) {
                inputTitreParcours.setError("Le champ est requis");
            } else {
                inputTitreParcours.setError(null);
            }
        }
        else if(s == inputDescriptionParcours.getEditableText()){
            if (inputDescriptionParcours.getText().toString().isEmpty()) {
                inputDescriptionParcours.setError("Le champ est requis");
            } else {
                inputDescriptionParcours.setError(null);
            }
        }
        else if(s == inputCategorie.getEditableText()){
            if (inputCategorie.getText().toString().isEmpty()) {
                inputCategorie.setError("Le champ est requis");
            } else {
                inputCategorie.setError(null);
            }
        }
        else if(s == inputTitreActivite.getEditableText()){
            if (inputTitreActivite.getText().toString().isEmpty()) {
                inputTitreActivite.setError("Le champ est requis");
            } else {
                inputTitreActivite.setError(null);
            }
        }
        else if(s == inputDuree.getEditableText()){
            if (inputDuree.getText().toString().isEmpty()) {
                inputDuree.setError("Le champ est requis");
            } else {
                inputDuree.setError(null);
            }
        }

    }

    private void addView(){
        @SuppressLint("InflateParams")
        final View activiteView = getLayoutInflater().inflate(R.layout.row_add_activite,null, false);
        ImageView imageClose = activiteView.findViewById(R.id.image_remove);
        imageClose.setOnClickListener(v -> {
            // on retire l'activité
            removeView(activiteView);
        });
        listActivite.addView(activiteView);
    }

    private void removeView(View view){
        listActivite.removeView(view);
    }

    private boolean isValid(){
        if(inputTitreParcours.getText().toString().isEmpty()){
            inputTitreParcours.setError("Le champ est requis");
            return false;
        }
        if(inputDescriptionParcours.getText().toString().isEmpty()){
            inputDescriptionParcours.setError("Le champ est requis");
            return false;
        }
        if(inputCategorie.getText().toString().isEmpty()){
            inputCategorie.setError("Le champ est requis");
            return false;
        }
        String titreActivite = inputTitreActivite.getText().toString();
        String descriptionActivite = inputDescriptionActivite.getText().toString();
        String duree = inputDuree.getText().toString().trim();
        if(titreActivite.isEmpty()){
            inputTitreActivite.setError("Le champ est requis");
            return false;
        }
        if(descriptionActivite.isEmpty()){
            inputDescriptionActivite.setError("Le champ est requis");
            return false;
        }
        if(duree.isEmpty()){
            inputDuree.setError("Le champ est requis");
            return false;
        }
        else{
            String pattern = "^[0-9]*$";
            if(!duree.matches(pattern)){
                inputDuree.setError("Seuls les nombres entiers sont autorisés");
                return false;
            }
            Activite activite = new Activite(titreActivite,descriptionActivite,Integer.parseInt(duree));
            arraylistActivite.add(activite);
        }
        int count = listActivite.getChildCount();
        for (int i = 5; i < count; i++) {
            View row = listActivite.getChildAt(i);
            inputTitreActivite = row.findViewById(R.id.titre_activite);
            inputDescriptionActivite = row.findViewById(R.id.description_activite);
            inputDuree = row.findViewById(R.id.duree);
            titreActivite = inputTitreActivite.getText().toString();
            descriptionActivite = inputDescriptionActivite.getText().toString();
            duree = inputDuree.getText().toString().trim();
            if(titreActivite.isEmpty()){
                inputTitreActivite.setError("Le champ est requis");
                return false;
            }
            if(descriptionActivite.isEmpty()){
                inputDescriptionActivite.setError("Le champ est requis");
                return false;
            }
            if(duree.isEmpty()){
                inputDuree.setError("Le champ est requis");
                return false;
            }
            else{
                String pattern = "^[0-9]*$";
                if(!duree.matches(pattern)){
                    inputDuree.setError("Seuls les nombres entiers sont autorisés");
                    return false;
                }
                Activite activite = new Activite(titreActivite,descriptionActivite,Integer.parseInt(duree));
                arraylistActivite.add(activite);
            }
        }
        return true;
    }

}