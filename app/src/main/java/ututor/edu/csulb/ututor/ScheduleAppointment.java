package ututor.edu.csulb.ututor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class ScheduleAppointment extends AppCompatActivity {

    Button newSession;
    Button endSession;
    User currentUser;
    User otherUser;
    int hi= RESULT_OK;
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
        Intent toTime = new Intent(this, ScheduleTimePicker.class);
        toTime.putExtra("currentUser", currentUser);
        toTime.putExtra("otherUser", otherUser);
        startActivityForResult(toTime, 1);

    }
}
