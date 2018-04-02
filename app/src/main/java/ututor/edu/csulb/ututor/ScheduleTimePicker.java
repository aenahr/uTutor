package ututor.edu.csulb.ututor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ScheduleTimePicker extends AppCompatActivity {

    Button newSession;
    Button endSession;
    User currentUser;
    User otherUser;
    Button sendResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_time_picker);

        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        otherUser = (User)i.getSerializableExtra("otherUser");

        ArrayList<WorkHour> otherUserHour = otherUser.getWorkHours();


        Toast.makeText(getApplicationContext(), otherUserHour.get(0).startTime, Toast.LENGTH_SHORT).show();

        sendResponse = (Button) findViewById(R.id.sendHello);

        sendResponse.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // return time
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "HelloWorld");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }


    public boolean isInTime(String userStartTime, String userEndTime){

        try {
            String string1 = "10:00:00";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            String string2 = "14:30:00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);

            String someRandomTime = "01:00:00";
            Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checks whether the current time is between 14:49:00 and 20:11:13.
                System.out.println("Good");
            }
            else{
                System.out.println("Bad");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }
}
