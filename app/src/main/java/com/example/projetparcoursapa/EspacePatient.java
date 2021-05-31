package com.example.projetparcoursapa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EspacePatient extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private int day, month, year;
    private int myDay, myMonth, myYear;
    private Patient patient;
    private String heureDebut;
    private String minuteDebut;
    private String heureFin;
    private String minuteFin;
    private Calendar startTime;
    private WeekViewEvent event;
    private WeekView mWeekView;

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_patient);

        // test avec le premier patient, à modifier selon le patient connecté
        patient = Patient.allPatient.get(0);


        // Get a reference for the week view in the layout.
        mWeekView = findViewById(R.id.weekView);
        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(mMonthChangeListener);


        mWeekView.setOnEventClickListener(onEventClick);
    }

    WeekView.EventClickListener onEventClick = (event, eventRect) -> {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(event.getStartTime().getTime());
        heureDebut = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        if(calendar.get(Calendar.MINUTE) == 0){
            minuteDebut = "00";
        }
        else{
            minuteDebut = String.valueOf(calendar.get(Calendar.MINUTE));
        }
        calendar.setTime(event.getEndTime().getTime());
        heureFin = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        if(calendar.get(Calendar.MINUTE) == 0){
            minuteFin = "00";
        }
        else{
            minuteFin = String.valueOf(calendar.get(Calendar.MINUTE));
        }

        this.event = event;
        // display custom dialog
        showEventDialog();
    };

    private void showEventDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EspacePatient.this);
        View layoutView = getLayoutInflater().inflate(R.layout.dialog_afficher_seance, null);
        LinearLayout linearLayout = layoutView.findViewById(R.id.linear_layout);
        linearLayout.setBackgroundColor(event.getColor());
        Button dialogButton = layoutView.findViewById(R.id.btn_ok);
        dialogBuilder.setView(layoutView);

        startTime = event.getStartTime();
        String jour = String.valueOf(startTime.get(Calendar.DAY_OF_MONTH));
        String mois = String.valueOf(startTime.get(Calendar.MONTH)+1);

        TextView dateSeance = layoutView.findViewById(R.id.date_seance);
        dateSeance.setText(getString(R.string.seance_du, jour, mois));

        ArrayList<Activite> activiteAssignee = patient.getActiviteAssignee();
        Activite activite = activiteAssignee.get(Integer.parseInt(event.getIdentifier()));
        TextView titreActivite = layoutView.findViewById(R.id.titre_activite);
        titreActivite.setText(activite.getTitre());
        TextView descriptionActivite = layoutView.findViewById(R.id.description_activite);
        descriptionActivite.setText(activite.getDescription());
        TextView heureDebutFin = layoutView.findViewById(R.id.heure_debut_fin);
        heureDebutFin.setText(getString(R.string.heure_debut_fin, heureDebut, minuteDebut, heureFin, minuteFin));

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
        myDay = dayOfMonth;
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
        startTime = Calendar.getInstance();
        startTime.set(Calendar.YEAR, myYear);
        startTime.set(Calendar.MONTH, myMonth);
        startTime.set(Calendar.DAY_OF_MONTH, myDay);
        startTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        startTime.set(Calendar.MINUTE, minute);
        // reprogrammer la séance à la date et à l'heure sélectionnée
        patient.getAgenda().get(Integer.parseInt(event.getIdentifier())).setStartTime(startTime);
        // mettre à jour l'affichage de l'agenda
        mWeekView.notifyDatasetChanged();
    }

    // remplir l'agenda du patient
    MonthLoader.MonthChangeListener mMonthChangeListener = this::getEvents;

    // retourne les évenements du patient
    private List<WeekViewEvent> getEvents(int newYear, int newMonth){
        ArrayList<Seance> agenda = patient.getAgenda();
        List<WeekViewEvent> events = new ArrayList<>();
        for(int i=0; i<agenda.size(); i++){
            WeekViewEvent event = new WeekViewEvent(
                    i,
                    agenda.get(i).getActivite().getTitre(),
                    agenda.get(i).getStartTime(),
                    agenda.get(i).getEndTime()
            );
            event.setColor(getResources().getColor(R.color.event_color_01));
            events.add(event);
            Calendar calendar = Calendar.getInstance();
            // si la séance n'est pas encore passée, planifier une notification 15 minutes avant
            if(calendar.before(agenda.get(i).getStartTime())){
                long delay = agenda.get(i).getStartTime().getTimeInMillis() - calendar.getTimeInMillis() - 900000;
                System.out.println("sysout : "+delay);
                scheduleNotification(getNotification(), delay);
            }

        }
        return events;
    }

    private void scheduleNotification (Notification notification , long delay) {
        Intent notificationIntent = new Intent(this, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime () + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE );
        assert alarmManager != null;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_channel_id);
        builder.setContentTitle("Notification Parcours APA");
        builder.setContentText("Une de vos séances va commencer dans 15 minutes !");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        return builder.build();
    }

}