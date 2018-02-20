package ututor.edu.csulb.ututor;

import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;

public class WorkHourPicker extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener{

    // user buttons
    Button saveChanges;
    Button cancelChanges;

    // initialize all buttons
    Button bMonday;
    Button bTuesday;
    Button bWednesday;
    Button bThursday;
    Button bFriday;
    Button bSaturday;
    Button bSunday;
    int sMonday = 0;
    int sTuesday = 0;
    int sWednesday = 0;
    int sThursday = 0;
    int sFriday = 0;
    int sSaturday = 0;
    int sSunday = 0;

    // initialize all ToggleButton Times
    Button bAddStartTime;
    Button bAddEndTime;
    TextView displayStart;
    TextView displayEnd;
    String startString;
    String endString;

    Calendar startTime;
    Calendar endTime;
    long seconds;

    int startOrEnd;
    boolean acceptable = false; // is time a valid

    // Work Hour
    private WorkHour newTime;
    public User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_work_picker);

        // getting user info
        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");

        cancelChanges = (Button) findViewById(R.id.backButton);
        saveChanges = (Button) findViewById(R.id.finishButton);
        bAddStartTime = (Button) findViewById(R.id.bAddStartTime);
        bAddEndTime = (Button) findViewById(R.id.bAddEndTime);
        displayStart = (TextView) findViewById(R.id.displayStart);
        displayEnd = (TextView) findViewById(R.id.displayEnd);

        // initialize Buttons
        initializeButtons();
        buttonListeners();

        //initialize start and end times
        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY,0);
        startTime.set(Calendar.MINUTE,0);
        startTime.set(Calendar.SECOND,0);
        startTime.set(Calendar.MILLISECOND,0);
        startString = String.format("%02d:%02d", startTime.get(Calendar.HOUR_OF_DAY), startTime.get(Calendar.MINUTE));
        displayStart.setText(startString);

        endTime = Calendar.getInstance();
        endTime.set(Calendar.HOUR_OF_DAY,0);
        endTime.set(Calendar.MINUTE,0);
        endTime.set(Calendar.SECOND,0);
        endTime.set(Calendar.MILLISECOND,0);
        endString = String.format("%02d:%02d", endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE));
        displayEnd.setText(endString);

//
//        // alert dialog to delete/add new session
//        String timeElapsed = "Time Elapsed: " + (seconds/3600) + " hrs " + ((seconds/60)%60) + " mins " + (seconds%60) + " secs";


        bAddStartTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                com.wdullaer.materialdatetimepicker.time.TimePickerDialog timePickerDialog = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(WorkHourPicker.this,
                        startTime.get(startTime.HOUR_OF_DAY),
                        startTime.get(startTime.MINUTE),
                        true);
                timePickerDialog.setTitle("TimePicker");
                timePickerDialog.show(getFragmentManager(), "TimePicker");
                startOrEnd = 0;

            }
        });

        bAddEndTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                com.wdullaer.materialdatetimepicker.time.TimePickerDialog timePickerDialog = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(WorkHourPicker.this,
                        endTime.get(endTime.HOUR_OF_DAY),
                        endTime.get(endTime.MINUTE),
                        true);
                timePickerDialog.setTitle("TimePicker");
                timePickerDialog.show(getFragmentManager(), "TimePicker");
                startOrEnd = 1;
            }
        });


        cancelChanges.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                onBackPressed();
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                // get seconds
                seconds = (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000;

                if(seconds <= 0){
                    // try again
                    Toast.makeText(getApplicationContext(), "Invalid time. Please try again." , Toast.LENGTH_SHORT).show();
                }
                else if(checkForNullDays() == false){
                    Toast.makeText(getApplicationContext(), "Invalid days. Please try again." , Toast.LENGTH_SHORT).show();
                }else{

                    //checks for days
                    newTime = new WorkHour();

                    if(sMonday == 1){
                        newTime.setMONDAY(true);
                    }
                    if(sTuesday == 1){
                        newTime.setTUESDAY(true);
                    }
                    if(sWednesday == 1){
                        newTime.setWEDNESDAY(true);
                    }
                    if(sThursday == 1){
                        newTime.setTHURSDAY(true);
                    }
                    if(sFriday == 1){
                        newTime.setFRIDAY(true);
                    }
                    if(sSaturday == 1){
                        newTime.setSATURDAY(true);
                    }
                    if(sSunday == 1){
                        newTime.setSUNDAY(true);
                    }

                    newTime.setStartTime(startString);
                    newTime.setEndTime(endString);

                    currentUser.addNewHour(newTime);
                    Intent i = new Intent(WorkHourPicker.this, HomePage.class);
                    i.putExtra("currentUser", currentUser);
                    i.putExtra("uploadPage", "workManager");
                    startActivity(i);
                }


            }
        });

    }

    public void initializeButtons() {
        bMonday = (Button) findViewById(R.id.wMonday);
        bTuesday = (Button) findViewById(R.id.wTuesday);
        bWednesday = (Button) findViewById(R.id.wWednesday);
        bThursday = (Button) findViewById(R.id.wThursday);
        bFriday = (Button) findViewById(R.id.wFriday);
        bSaturday = (Button) findViewById(R.id.wSaturday);
        bSunday = (Button) findViewById(R.id.wSunday);
    }

    public void buttonListeners(){
        bMonday.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(sMonday == 0){
                    sMonday = 1;
                    bMonday.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                else{
                    sMonday = 0;
                    bMonday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });

        bTuesday.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(sTuesday == 0){
                    sTuesday = 1;
                    bTuesday.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                else{
                    sTuesday = 0;
                    bTuesday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });

        bWednesday.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(sWednesday == 0){
                    sWednesday = 1;
                    bWednesday.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                else{
                    sWednesday = 0;
                    bWednesday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });

        bThursday.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(sThursday == 0){
                    sThursday = 1;
                    bThursday.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                else{
                    sThursday = 0;
                    bThursday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });

        bFriday.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(sFriday == 0){
                    sFriday = 1;
                    bFriday.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                else{
                    sFriday = 0;
                    bFriday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });

        bSaturday.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(sSaturday == 0){
                    sSaturday = 1;
                    bSaturday.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                else{
                    sSaturday = 0;
                    bSaturday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });

        bSunday.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(sSunday == 0){
                    sSunday = 1;
                    bSunday.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                else{
                    sSunday = 0;
                    bSunday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });

    }


    @Override
    public void onTimeSet(com.wdullaer.materialdatetimepicker.time.TimePickerDialog view, int hourOfDay, int minute, int second) {

        if (startOrEnd == 0){
            startString = String.format("%02d:%02d", hourOfDay, minute);
            displayStart.setText(startString);

            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
            startTime.set(Calendar.MINUTE,minute);
            startTime.set(Calendar.SECOND,0);
            startTime.set(Calendar.MILLISECOND,0);
        }else{
            endString = String.format("%02d:%02d", hourOfDay, minute);
            displayEnd.setText(endString);

            endTime = Calendar.getInstance();
            endTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
            endTime.set(Calendar.MINUTE,minute);
            endTime.set(Calendar.SECOND,0);
            endTime.set(Calendar.MILLISECOND,0);
        }
    }


    @Override
    public void onBackPressed() {
        // cannot go back by pressing back

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you wish to continue? By pressing no, unsaved changes will be deleted.")
                .setIcon(R.drawable.warning_icon)
                .setTitle("Unsaved Changes")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean checkForNullDays(){
        if(sMonday == 0 && sTuesday == 0 && sTuesday == 0 && sThursday == 0 && sFriday == 0 && sSaturday == 0 && sSunday == 0){
            return false;
        }
        else{
            return true;
        }
    }
}
