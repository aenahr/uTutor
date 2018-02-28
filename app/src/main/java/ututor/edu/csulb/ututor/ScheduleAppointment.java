package ututor.edu.csulb.ututor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class ScheduleAppointment extends AppCompatActivity {

    Button newSession;
    Button endSession;
    User currentUser;
    User otherUser;
    int hi= RESULT_OK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_creation);

        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");

        // the other User's stuff

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

    public void pickTime(View v){

        // TODO send the other User's info and the current User
        otherUser = new User();
        otherUser.setEmail("theOtherUser@gmail.com");
        WorkHour monday = new WorkHour();
        monday.setMONDAY(true);
        monday.setStartTime("07:00"+":00");
        monday.setEndTime("10:30"+":00");
        otherUser.addNewHour(monday);

        // send currentUser and otherUser's data to TimePicker
        Intent toTime = new Intent(this, ScheduleTimePicker.class);
        toTime.putExtra("currentUser", currentUser);
        toTime.putExtra("otherUser", otherUser);
        startActivityForResult(toTime, 1);

    }
}
