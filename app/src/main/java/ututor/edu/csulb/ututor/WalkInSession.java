package ututor.edu.csulb.ututor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class WalkInSession extends AppCompatActivity {


    Button buttonTime;
    User currentUser;
    boolean begin = false;
    String startTime;
    String endTime;
    Calendar start;
    Calendar end;

    TextView userEmail;
    TextView currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_in);

        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");

        buttonTime = (Button) findViewById(R.id.buttonWalk);
        userEmail = (TextView) findViewById(R.id.generateTutorEmail);
        currentDate = (TextView) findViewById(R.id.generateDate);


        // set to user email
        userEmail.setText(currentUser.getEmail());

        //set to current date
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);


        String formatDate = String.format("%d-%02d-%02d", year, month, day);

        // get current date and present it to user
        currentDate.setText(formatDate);

        buttonTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                if(begin == false){

                    //set boolean to true now
                    begin = true;

                    //set text of button to "end time"
                    buttonTime.setText("End Time");

                    // start tutoring time
                    startTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    Toast.makeText(getApplicationContext(), startTime , Toast.LENGTH_SHORT).show();

                }
                else{ //this is our end time

                    endTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    Toast.makeText(getApplicationContext(), endTime , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(WalkInSession.this, WalkInActivity.class);
                    i.putExtra("currentUser", currentUser);
                    startActivity(i);

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
