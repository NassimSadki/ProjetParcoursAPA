package com.example.projetparcoursapa;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LoginFragment extends Fragment {


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
        boutonConnecter.setOnClickListener(v -> {
            Intent espacePatientIntent = new Intent(activity.getApplicationContext(), EspacePatient.class);
            startActivity(espacePatientIntent);
        });
    }

}