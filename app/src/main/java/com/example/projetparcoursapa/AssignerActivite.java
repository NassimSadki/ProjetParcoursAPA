package com.example.projetparcoursapa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AssignerActivite extends AppCompatActivity {

    private int mYear, mMonth, mDay, mHour, mMinute;
    private int heure, minutes;
    private ArrayList<Date> listeDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigner_activite);

        heure = -1;
        minutes = -1;
        listeDates = new ArrayList<>();

        final CalendarPickerView calendarView = findViewById(R.id.calendar_view);
        //getting current
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        Date today = new Date();

        //add one year to calendar from todays date
        calendarView.init(today, nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.MULTIPLE);

        calendarView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateUnselected(Date date) {
                listeDates.remove(date);
            }
            @Override
            public void onDateSelected(Date date) {
                listeDates.add(date);
            }
        });


        Button btnHeure = findViewById(R.id.btn_choisir_heure);
        btnHeure.setOnClickListener(v -> {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, hourOfDay, minute) -> {
                        // récupérer l'horaire selectionné
                        heure = hourOfDay;
                        minutes = minute;
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        });

        Button btnValider = findViewById(R.id.btn_valider);
        btnValider.setOnClickListener(this::onClick);

        Button btnAnnuler = findViewById(R.id.btn_annuler);
        btnAnnuler.setOnClickListener(v -> {
            // fermer l'activité
            finish();
        });
    }


    private void onClick(View v) {
        if (listeDates.size() == 0) {
            // on affiche une erreur si l'utilisateur n'a pas sélectionné de date
            new AlertDialog.Builder(this)
                    .setTitle("Erreur")
                    .setMessage("Veuillez sélectionner au moins une date.")
                    .setPositiveButton(android.R.string.yes, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else if (heure == -1 || minutes == -1) {
            // on affiche une erreur si l'utilisateur n'a pas sélectionné l'heure
            new AlertDialog.Builder(this)
                    .setTitle("Erreur")
                    .setMessage("Veuillez choisir l'heure avant de valider.")
                    .setPositiveButton(android.R.string.yes, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            for (int i = 0; i < listeDates.size(); i++) {
                // créer la séance
                Calendar startTime = Calendar.getInstance();
                startTime.setTime(listeDates.get(i));
                startTime.set(Calendar.HOUR_OF_DAY, heure);
                startTime.set(Calendar.MINUTE, minutes);
                Seance seance = new Seance(EspaceIntervenant.selectedActivite, startTime);
                // ajouter la séance à l'agenda du patient
                EspaceIntervenant.patient.addSeance(seance);
            }
            // actualiser l'affichage de l'agenda
            EspaceIntervenant.mWeekView.notifyDatasetChanged();
            // retirer l'activité des activités en attente d'être assignées
            EspaceIntervenant.patient.removeActivite(EspaceIntervenant.selectedActivite);
            EspaceIntervenant.listActivite.removeView(EspaceIntervenant.addedActiviteViews.get(EspaceIntervenant.index));

            // fermer l'activité
            finish();
        }
    }
}