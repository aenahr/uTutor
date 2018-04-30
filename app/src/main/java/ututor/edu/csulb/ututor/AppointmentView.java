package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by aenah on 4/30/18.
 */

public class AppointmentView extends AppCompatActivity {

    User currentUser;
    boolean isTutoring;
    Appointment currentAppointment;

    // objects
    TextView tutorEmail;
    TextView tuteeEmail;
    TextView date;
    TextView startTime;
    TextView endTime;
    TextView tutorMessage;
    Button bAccept;
    Button bDecline;
    Button bCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_view);

        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        isTutoring = (boolean)i.getSerializableExtra("isTutoring");
        currentAppointment = (Appointment)i.getSerializableExtra("appointmentInfo");

        //initialize variables
        tutorEmail = (TextView)findViewById(R.id.tutorInput);
        tuteeEmail = (TextView)findViewById(R.id.tuteeInput);
        date = (TextView)findViewById(R.id.dateInput);
        startTime = (TextView)findViewById(R.id.sTimeInput);
        endTime = (TextView)findViewById(R.id.eTimeInput);
        tutorMessage = (TextView)findViewById(R.id.tutorQuestion);
        bAccept = (Button)findViewById(R.id.bAccept);
        bDecline = (Button)findViewById(R.id.bDecline);
        bCancel = (Button)findViewById(R.id.bCancel);

        if(isTutoring == false){ // you are not the one tutoring
            tutorMessage.setVisibility(View.INVISIBLE);
            bAccept.setVisibility(View.INVISIBLE);
            bDecline.setVisibility(View.INVISIBLE);
        }
        else{ // you are the tutor, so you have to either accept or reject appointment
            bCancel.setVisibility(View.INVISIBLE);
        }

        // set values from fragment
        tutorEmail.setText(currentAppointment.getTutorEmail());
        tuteeEmail.setText(currentAppointment.getTuteeEmail());
        String dateString = String.format("%2s-%2s-%2s", currentAppointment.getDate().get(Calendar.MONTH)+1, currentAppointment.getDate().get(Calendar.DAY_OF_MONTH), currentAppointment.getDate().get(Calendar.YEAR)).replace(' ', '0');
        String start = String.format("%2s:%2s", currentAppointment.getStartTime().get(Calendar.HOUR_OF_DAY), currentAppointment.getStartTime().get(Calendar.MINUTE)).replace(' ', '0');
        String end = String.format("%2s:%2s", currentAppointment.getEndTime().get(Calendar.HOUR_OF_DAY), currentAppointment.getEndTime().get(Calendar.MINUTE)).replace(' ', '0');
        date.setText(dateString);
        startTime.setText(start);
        endTime.setText(end);

        bAccept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //TODO: database - set to 1 in database
            }
        });

        bDecline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // remove appointment from database
                removeAppointment();
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // remove appointment from database
                removeAppointment();
            }
        });
    }

    /**
     * Remove appointment from database
     */
    public void removeAppointment(){
        //TODO: database - remove appointment pls
    }
}
