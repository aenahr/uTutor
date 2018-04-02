package ututor.edu.csulb.ututor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


public class ScheduleAppointment extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Button newSession;
    Button endSession;
    User currentUser;
    User otherUser;
    int hi= RESULT_OK;
    TextView dateOutput;
    int year;
    int monthOfYear;
    int dayOfMonth;
    int[] daysOfWeek;


    RadioGroup conver_Group;
    RadioButton sms;
    RadioButton email;
    Button set_appointment;
    EditText apoint_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_creation);

        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");

        dateOutput = findViewById(R.id.dateOutput);

        // the other User's stuff
        apoint_message = (EditText)findViewById(R.id.editSendMessage) ;
        conver_Group = (RadioGroup)findViewById(R.id.typeConversation);
        sms = (RadioButton)findViewById(R.id.apoint_SMS);
        email =(RadioButton)findViewById(R.id.apoint_Email);


        set_appointment = (Button)findViewById(R.id.setAppointmentButton);
        set_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sms.isChecked())
                {
                    sendSMS("800-233-5555",""+apoint_message.getText().toString()); //TODO this is temporary phone number and get text user enter

                }else if (email.isChecked())
                {
                    Log.i("Send email", "");
                    String[] TO = {"123@gmail.com"}; //TODO temporary email and replace by user email
                    String[] CC = {""};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                    emailIntent.putExtra(Intent.EXTRA_CC, CC);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, currentUser.getFirstName()+" " + currentUser.getLastName() + " is requesting to make appointment" );
                    emailIntent.putExtra(Intent.EXTRA_TEXT, " " + apoint_message.getText().toString());
                    startActivity(emailIntent);

                    try {
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        Log.i("Finished sending email", "");
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(ScheduleAppointment.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Toast.makeText(getApplicationContext(), result , Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    //TODO need to turn on the permission in the emulator

    public void sendSMS(String phoneNo, String msg) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),"SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void pickTime(View v){

        if(dateOutput.getText().toString().equals("Date output...")){// if you haven't changed a date yet
            Toast.makeText(this, "Please select a date first.", Toast.LENGTH_SHORT).show();

        }
        else{
            // TODO send the other User's info and the current User
            // some sample user that I will delete later
            otherUser = new User();
            otherUser.setEmail("theOtherUser@gmail.com");
            WorkHour monday = new WorkHour();
            monday.setMONDAY(true);
            monday.setStartTime("07:00"+":00");
            monday.setEndTime("10:30"+":00");
            otherUser.addNewHour(monday);

            // send currentUser and otherUser's data to TimePicker
            Intent toTime = new Intent(this, AppointmentTimePicker.class);
            toTime.putExtra("currentUser", currentUser);
            toTime.putExtra("otherUser", otherUser);
            startActivityForResult(toTime, 1);

        }

    }

    public void pickDate(View v){
        // set min date - user shouldn't be able to schedule an appointment past the current day...
        // YOU ARE ONLY ALLOWED TO SCHEDULE AN APPOINTMENT ONE DAY BEFORE
        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.DAY_OF_YEAR, 1);

        // create instance of the DatePickerDialog Class
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                ScheduleAppointment.this, minDate.get(Calendar.YEAR), minDate.get(Calendar.MONTH), minDate.get(Calendar.DAY_OF_MONTH));

        // App will only allow appointments during the YEAR, so disable all other years
        datePickerDialog.showYearPickerFirst(false);

        // IMPORTANT - single out the days the actual user tutors
        // TODO temporary other user - erase later
        // get other users available days
        WorkHour w = new WorkHour();
        w.setMONDAY(true);
        w.setWEDNESDAY(true);
        WorkHour h = new WorkHour();
        h.setFRIDAY(true);
        otherUser = new User();
        otherUser.addNewHour(w);
        otherUser.addNewHour(h);

        // gets other user's work hour
        initializeDayOfWeek();

        Calendar g1= Calendar.getInstance(); // gets tomorrow's day, month, year
        g1.set(Calendar.MONTH, minDate.get(Calendar.MONTH));
        g1.set(Calendar.DAY_OF_MONTH, minDate.get(Calendar.DAY_OF_MONTH));
        g1.set(Calendar.YEAR, minDate.get(Calendar.YEAR));

        Calendar gc = Calendar.getInstance(); //gets last day of year
        gc.set(Calendar.MONTH, 11);
        gc.set(Calendar.DAY_OF_MONTH, 31);
        gc.set(Calendar.YEAR, minDate.get(Calendar.YEAR));

        List<Calendar> dayslist= new LinkedList<Calendar>();
        Calendar[] daysArray;
        // iterate throughout the whole year
        while ( g1.compareTo(gc) == -1) {
            if (isWithinDays(g1.get(Calendar.DAY_OF_WEEK))) { // create boolean that checks multiple days of the week
                // create new instance with current day, month, year
                Calendar c = Calendar.getInstance();
                c.set(Calendar.MONTH, g1.get(Calendar.MONTH));
                c.set(Calendar.DAY_OF_MONTH, g1.get(Calendar.DAY_OF_MONTH));
                c.set(Calendar.YEAR, g1.get(Calendar.YEAR));
                dayslist.add(c);
            }
            g1.add(Calendar.DAY_OF_MONTH, 1); // increment day
        }

        // add days aquired to the selectable
        daysArray = new Calendar[dayslist.size()];
        for (int i = 0; i<daysArray.length;i++)
        {
            daysArray[i]=dayslist.get(i);
        }
        datePickerDialog.setSelectableDays(daysArray);

        // Title the Date Picker Dialog
        datePickerDialog.setTitle("Pick Appointment Date");
        datePickerDialog.show(getFragmentManager(), "Pick Appointment Date");

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        //set selected date in the Date Output for visual purposes
        dateOutput.setText(String.format("%d-%d-%d", monthOfYear+1, dayOfMonth, year));
//        Toast.makeText(this, String.format("You selected %d/%d, %d", monthOfYear, dayOfMonth, year), Toast.LENGTH_SHORT).show();
    }

    public boolean isWithinDays(int currentDayOfWeek){
        currentDayOfWeek = currentDayOfWeek -1;
        if(daysOfWeek[currentDayOfWeek] ==1 ){
            return true;
        }
        return false;
    }

    public void initializeDayOfWeek(){
        daysOfWeek = new int[7];
        for(int i = 0; i < otherUser.getWorkHours().size(); i++){
            if(otherUser.getWorkHours().get(i).getSUNDAY() == true){ daysOfWeek[0] = 1; }
            if(otherUser.getWorkHours().get(i).getMONDAY() == true){ daysOfWeek[1] = 1; }
            if(otherUser.getWorkHours().get(i).getTUESDAY() == true){ daysOfWeek[2] = 1; }
            if(otherUser.getWorkHours().get(i).getWEDNESDAY() == true){ daysOfWeek[3] = 1; }
            if(otherUser.getWorkHours().get(i).getTHURSDAY() == true){ daysOfWeek[4] = 1; }
            if(otherUser.getWorkHours().get(i).getFRIDAY() == true){ daysOfWeek[5] = 1; }
            if(otherUser.getWorkHours().get(i).getSATURDAY() == true){ daysOfWeek[6] = 1; }
        }
    }


    public void pickLocation(View v){
        Intent toLocation = new Intent(this, AppointmentTimePicker.class);
        startActivity(toLocation);
    }
}
