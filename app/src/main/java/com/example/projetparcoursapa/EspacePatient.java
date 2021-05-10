package com.example.projetparcoursapa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EspacePatient extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private int day, month, year;
    private int myday, myMonth, myYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_patient);

        // Get a reference for the week view in the layout.
        WeekView mWeekView = findViewById(R.id.weekView);
        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(mMonthChangeListener);


        mWeekView.setOnEventClickListener(onEventClick);
    }

    WeekView.EventClickListener onEventClick = (event, eventRect) -> {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(event.getStartTime().getTime());
        String heureDebut = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minuteDebut;
        if(calendar.get(Calendar.MINUTE) == 0){
            minuteDebut = "00";
        }
        else{
            minuteDebut = String.valueOf(calendar.get(Calendar.MINUTE));
        }
        calendar.setTime(event.getEndTime().getTime());
        String heureFin = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minuteFin;
        if(calendar.get(Calendar.MINUTE) == 0){
            minuteFin = "00";
        }
        else{
            minuteFin = String.valueOf(calendar.get(Calendar.MINUTE));
        }

        // only for test purpose
        // display info of the clicked event
        Toast.makeText(this, "Clicked " + event.getName()
                + "\nHeure début " + heureDebut + "h" + minuteDebut
                + "\nHeure fin " + heureFin + "h" + minuteFin
                , Toast.LENGTH_LONG).show();

        // TODO: display info of the clicked event in the custom dialog

        // display custom dialog
        showCustomDialog(R.layout.dialog_afficher_seance, event.getColor());
    };

    private void showCustomDialog(int layout, int color){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EspacePatient.this);
        View layoutView = getLayoutInflater().inflate(layout, null);
        LinearLayout linearLayout = layoutView.findViewById(R.id.linear_layout);
        linearLayout.setBackgroundColor(color);
        Button dialogButton = layoutView.findViewById(R.id.btn_ok);
        dialogBuilder.setView(layoutView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        dialogButton.setOnClickListener(view -> alertDialog.dismiss());
        Button reprogrammerButton = layoutView.findViewById(R.id.btn_reprogrammer);
        reprogrammerButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(EspacePatient.this, EspacePatient.this,year, month,day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = day;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(EspacePatient.this,
                EspacePatient.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        // only for test purpose
        // display info of the selected date time
        Toast.makeText(this, "Year: " + myYear + "\n" +
                "Month: " + myMonth + "\n" +
                "Day: " + myday + "\n" +
                "Hour: " + hourOfDay + "\n" +
                "Minute: " + minute, Toast.LENGTH_LONG).show();

        // TODO: process selected date time
    }


    // Populate the week view with some events.
    MonthLoader.MonthChangeListener mMonthChangeListener = this::getEvents;

    private List<WeekViewEvent> getEvents(int newYear, int newMonth){
        List<WeekViewEvent> events = new ArrayList<>();

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 8);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 2);
        endTime.set(Calendar.MONTH, newMonth-1);
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 14);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 2);
        endTime.set(Calendar.MONTH, newMonth-1);
        event = new WeekViewEvent(2, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 18);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, newMonth-1);
        event = new WeekViewEvent(3, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 11);
        startTime.set(Calendar.HOUR_OF_DAY, 8);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 2);
        endTime.set(Calendar.MONTH, newMonth-1);
        event = new WeekViewEvent(4, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_04));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 12);
        startTime.set(Calendar.HOUR_OF_DAY, 16);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, newMonth-1);
        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_05));
        events.add(event);

        return events;
    }

    @SuppressLint("DefaultLocale")
    private String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }
}