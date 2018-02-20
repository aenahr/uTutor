package ututor.edu.csulb.ututor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;


public class WalkInSession extends AppCompatActivity {


    Button buttonTime;
    User currentUser; // from the previous activity
    boolean begin = false; // time tracking has not started yet
    Calendar start; // timestamp of when tutor presses start time
    Calendar end; // timestamp of when tutor presses end time
    long seconds;

    TextView userEmail; // auto-generated email given from user class
    TextView currentDate; // gets the date when tutor pressed new walk in
    TextView inputTutee; // tutee must type in his/her email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_in);

        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        //confirm new appointment added in user
        if( i.getExtras().getInt("session") == 2){
            Toast.makeText(getApplicationContext(), "New Walk-In Session Created." , Toast.LENGTH_SHORT).show();
        }

        buttonTime = (Button) findViewById(R.id.buttonWalk);
        userEmail = (TextView) findViewById(R.id.generateTutorEmail);
        currentDate = (TextView) findViewById(R.id.generateDate);

        inputTutee = (TextView) findViewById(R.id.inputTuteeEmail);


        // set to user email
        userEmail.setText(currentUser.getEmail());

        // set to current date
        final Calendar dayDate = Calendar.getInstance();
        int year = dayDate.get(Calendar.YEAR);
        int month = dayDate.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = dayDate.get(Calendar.DAY_OF_MONTH);

        // get current date and present it to user
        final String formatDate = String.format("%d-%02d-%02d", year, month, day);
        currentDate.setText(formatDate);

        // TODO get user's location

        buttonTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                if(begin == false){ // walk in session started

                    //set boolean to true now
                    begin = true;

                    //set text of button to "end time"
                    buttonTime.setText("End Time");

                    // start tutoring time
                    start = Calendar.getInstance();
                    int year = start.get(Calendar.YEAR);
                    int month = start.get(Calendar.MONTH) + 1; // Note: zero based!
                    int day = start.get(Calendar.DAY_OF_MONTH);
                    int hour = start.get(Calendar.HOUR_OF_DAY);
                    int minute = start.get(Calendar.MINUTE);
                    int second = start.get(Calendar.SECOND);

                }
                else{ // walk in session ended
                    // end tutoring time
                    end = Calendar.getInstance();
                    int year = end.get(Calendar.YEAR);
                    int month = end.get(Calendar.MONTH) + 1; // Note: zero based!
                    int day = end.get(Calendar.DAY_OF_MONTH);
                    int hour = end.get(Calendar.HOUR_OF_DAY);
                    int minute = end.get(Calendar.MINUTE);
                    int second = end.get(Calendar.SECOND);

                    // length of time that has passed
                    seconds = (end.getTimeInMillis() - start.getTimeInMillis()) / 1000;

                    // alert dialog to delete/add new session
                    String timeElapsed = "Time Elapsed: " + (seconds/3600) + " hrs " + ((seconds/60)%60) + " mins " + (seconds%60) + " secs";
                    AlertDialog.Builder confirmAdd = new AlertDialog.Builder(new ContextThemeWrapper(WalkInSession.this, R.style.DialogPadding));

                    TextView myMsg = new TextView(WalkInSession.this);
                    myMsg.setText("Are you sure you would like to add new session?\n\n" +
                            "Date: " + formatDate + "\n" +
                            "Tutor: " + currentUser.getEmail() + "\n" +
                            "Tutee: " + inputTutee.getText().toString() + "\n" +
                            timeElapsed + "\n\n" +
                            "Pressing no will create a new session and delete the current one.");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    confirmAdd.setView(myMsg);
                    confirmAdd.setCancelable(true);
                    confirmAdd.setTitle("Confirm new Walk-In Session:");
                    confirmAdd.setIcon(R.drawable.warning_icon);
                    confirmAdd.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // create new Appointment to store in User
                                    Appointment wiAppointment = new Appointment();
                                    wiAppointment.setDateOfAppointment(dayDate);
                                    wiAppointment.setStartTime(start);
                                    wiAppointment.setEndTime(end);
                                    wiAppointment.setLengthOfAppointment(seconds);
                                    wiAppointment.setTutee(inputTutee.getText().toString());
                                    wiAppointment.setTutor(currentUser.getEmail());
                                    wiAppointment.setTypeOfAppointment("WALKIN");
                                    //wiAppointment.setLocation("herp");
                                    // TODO get user's lat long and somehow convert to address then convert to string

                                    // adding appointment to user class
                                    currentUser.addNewAppointment(wiAppointment);


                                    // Go back to main activity
                                    Intent i = new Intent(WalkInSession.this, WalkInActivity.class);
                                    i.putExtra("currentUser", currentUser);
                                    i.putExtra("session", 1);
                                    startActivity(i);
                                    finish();
                                }
                            });

                    confirmAdd.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(WalkInSession.this, WalkInSession.class);
                                    i.putExtra("currentUser", currentUser);
                                    i.putExtra("session", 2);
                                    startActivity(i);
                                    finish();
                                }
                            });

                    AlertDialog alert11 = confirmAdd.create();
                    alert11.show();

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        // cannot go back by pressing back

        if(begin== false){
            super.onBackPressed();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please press 'End Time' to end walk-in session.")
                    .setIcon(R.drawable.warning_icon)
                    .setTitle("Error Ending Walk-In Session")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }


}
