package com.example.projetparcoursapa;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class LoginFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private String profil = "";

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        Button boutonConnecter = view.findViewById(R.id.btn_login);
        MainActivity activity = (MainActivity) getActivity();
        Spinner spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        boutonConnecter.setOnClickListener(v -> {

            // skip login for test purpose
            // TODO: make email and password required and process the inputs

            if(this.profil.equals("Médecin")){
                Intent espaceMedecinIntent = new Intent(activity.getApplicationContext(), EspaceMedecin.class);
                startActivity(espaceMedecinIntent);
            }
            else if(this.profil.equals("Intervenant")){
                Intent espaceIntervenantIntent = new Intent(activity.getApplicationContext(), EspaceIntervenant.class);
                startActivity(espaceIntervenantIntent);
            }
            else{
                // profil = Patient
                Intent espacePatientIntent = new Intent(activity.getApplicationContext(), EspacePatient.class);
                startActivity(espacePatientIntent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        this.profil = (String) parent.getItemAtPosition(pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}