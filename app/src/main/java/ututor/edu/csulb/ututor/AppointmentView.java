package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by aenah on 4/30/18.
 */

public class AppointmentView extends AppCompatActivity {

    User currentUser;
    boolean isTutoring;
    boolean isPast;
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
        isPast = (boolean)i.getSerializableExtra("appointmentPast");


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


        if(isPast == true){
            tutorMessage.setVisibility(View.INVISIBLE);
            bAccept.setVisibility(View.INVISIBLE);
            bDecline.setVisibility(View.INVISIBLE);
            bCancel.setVisibility(View.INVISIBLE);
        }
        else if(isTutoring == false){ // you are not the one tutoring
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
                setAppointmentStatus(1);
            }
        });

        bDecline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // remove appointment from database
                setAppointmentStatus(-1);
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // remove appointment from database
                setAppointmentStatus(-1);
            }
        });
    }

    /**
     * Remove appointment from database
     */
    public void setAppointmentStatus(int status){
        //TODO: database - remove appointment pls
        JSONObject response = null;
        try {
            response = new ServerRequester().execute("scheduleAppointment.php", "whatever"
                    ,"tutorEmail", currentAppointment.getTutorEmail()
                    ,"tuteeEmail", currentAppointment.getTuteeEmail()
                    ,"startAppDateTime", formatDateTime(currentAppointment.getDate(), currentAppointment.getStartTime())//Format: "YYYY-MM-DD HH:MM:SS", 1<=MM<=12
                    ,"endAppDateTime", formatDateTime(currentAppointment.getDate(), currentAppointment.getEndTime())    //Format is pretty lenient, So long as you use consistent delimiters, seconds not necessary
            ).get();
            if (response == null) {//Something went horribly wrong, JSON failed to be formed meaning something happened in the server requester

            } else if (!response.isNull("error")) {//Some incorrect information was sent, but the server and requester still processed it
                //TODO Handle Server Errors
                switch (response.get("error").toString()) {
                    default:    //Some Error Code was printed from the server that isn't handled above

                        break;
                }
            } else { //Everything Went Well

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
    public String formatDate(Calendar c){
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(c.getTime());
    }
    public String formatDateTime(Calendar date, Calendar time){
        return formatDate(date) + " " + time.get(Calendar.HOUR) + ":" + time.get(Calendar.MINUTE);
    }
}
