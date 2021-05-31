package com.example.projetparcoursapa;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;

public class RegisterFragment extends Fragment implements AdapterView.OnItemSelectedListener, TextWatcher {

    private String profil = "";
    private EditText inputNom;
    private EditText inputPrenom;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputRePassword;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button btnRegister = view.findViewById(R.id.btn_register);
        Spinner spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        inputNom = view.findViewById(R.id.nom);
        inputPrenom = view.findViewById(R.id.prenom);
        inputEmail = view.findViewById(R.id.email);
        inputPassword = view.findViewById(R.id.password);
        inputRePassword = view.findViewById(R.id.repassword);

        inputNom.addTextChangedListener(this);
        inputPrenom.addTextChangedListener(this);
        inputEmail.addTextChangedListener(this);
        inputPassword.addTextChangedListener(this);
        inputRePassword.addTextChangedListener(this);

        btnRegister.setOnClickListener(v -> {
            if (isValid()) {
                switch (this.profil) {
                    case "Patient":
                        new Patient(
                                inputNom.getText().toString(),
                                inputPrenom.getText().toString(),
                                inputEmail.getText().toString(),
                                DigestUtils.sha256Hex(inputPassword.getText().toString())
                        );
                        break;
                    case "Médecin":
                        new Medecin(
                                inputNom.getText().toString(),
                                inputPrenom.getText().toString(),
                                inputEmail.getText().toString(),
                                DigestUtils.sha256Hex(inputPassword.getText().toString())
                        );
                        break;
                    case "Intervenant":
                        new Intervenant(
                                inputNom.getText().toString(),
                                inputPrenom.getText().toString(),
                                inputEmail.getText().toString(),
                                DigestUtils.sha256Hex(inputPassword.getText().toString())
                        );
                        break;
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s == inputNom.getEditableText()){
            if (inputNom.getText().toString().isEmpty()) {
                inputNom.setError("Le champ est requis");
            } else {
                inputNom.setError(null);
            }
        }
        else if(s == inputPrenom.getEditableText()){
            if (inputPrenom.getText().toString().isEmpty()) {
                inputPrenom.setError("Le champ est requis");
            } else {
                inputPrenom.setError(null);
            }
        }
        else if(s == inputEmail.getEditableText()){
            if (inputEmail.getText().toString().isEmpty()) {
                inputEmail.setError("Le champ est requis");
            } else {
                inputEmail.setError(null);
            }
        }
        else if(s == inputPassword.getEditableText()){
            if (inputPassword.getText().toString().isEmpty()) {
                inputPassword.setError("Le champ est requis");
            } else {
                inputPassword.setError(null);
            }
        }
        else if(s == inputRePassword.getEditableText()){
            if (inputRePassword.getText().toString().isEmpty()) {
                inputRePassword.setError("Le champ est requis");
            } else {
                inputRePassword.setError(null);
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        this.profil = (String) parent.getItemAtPosition(pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean isValid() {
        String email = inputEmail.getText().toString().trim();
        String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)" +
                "*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        MainActivity context = (MainActivity) getContext();
        if (inputNom.getText().toString().isEmpty()) {
            inputNom.setError("Le champ est requis");
            return false;
        }
        if (inputPrenom.getText().toString().isEmpty()) {
            inputPrenom.setError("Le champ est requis");
            return false;
        }
        if (inputEmail.getText().toString().isEmpty()) {
            inputEmail.setError("Le champ est requis");
            return false;
        }
        if (inputPassword.getText().toString().isEmpty()) {
            inputPassword.setError("Le champ est requis");
            return false;
        }
        if (inputRePassword.getText().toString().isEmpty()) {
            inputRePassword.setError("Le champ est requis");
            return false;
        }
        if (!inputPassword.getText().toString().equals(inputRePassword.getText().toString())) {
            // error both passwords must be equal
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
            dlgAlert.setMessage("Les deux mots de passe doivent être identiques");
            dlgAlert.setTitle("Erreur");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            dlgAlert.setPositiveButton("Ok",
                    (dialog, which) -> dialog.dismiss()
            );
            return false;
        }
        if (!email.matches(emailPattern)) {
            // error invalid email
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
            dlgAlert.setMessage("L'email doit avoir un format valide");
            dlgAlert.setTitle("Erreur");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            dlgAlert.setPositiveButton("Ok",
                    (dialog, which) -> dialog.dismiss()
            );
            return false;
        }
        return true;
    }
}