package ututor.edu.csulb.ututor;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;


public class WalkInSession extends AppCompatActivity{


    Button buttonTime;
    User currentUser; // from the previous activity
    boolean begin = false; // time tracking has not started yet
    Calendar start; // timestamp of when tutor presses start time
    Calendar end; // timestamp of when tutor presses end time
    long seconds;

    TextView userEmail; // auto-generated email given from user class
    TextView currentDate; // gets the date when tutor pressed new walk in
    TextView inputTutee; // tutee must type in his/her email
    TextView location;

    // objects needed for location
    Geocoder geocoder;
    private final LatLng LOCATION_UNIV = new LatLng(33.783768, -118.114336);
    private final LatLng LOCATION_CSUF = new LatLng(33.883121, -117.887634);
    private final LatLng LOCATION_PARK = new LatLng(33.803115, -118.096161);

    private LatLng currentPosition;
    private LocationManager locationManager;
    private String provider;
    private MyLocationListener mylistener;
    private Criteria criteria;





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
        location = (TextView) findViewById(R.id.generateLocation);

        // getting user's location and displaying it
        requestLocationPermission();
        String address = getAddressName(currentPosition);
        location.setText(address);

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

    public String getAddressName(LatLng currentPosition){

        geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = new ArrayList<>();
        try {
            addresses = geocoder.getFromLocation(currentPosition.latitude, currentPosition.longitude,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        android.location.Address address = addresses.get(0);
        if (address != null) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < address.getMaxAddressLineIndex(); x++){
                sb.append(address.getAddressLine(x) + "\n");
            }
            return sb.toString();
        }
        else{
            return "No Address Found";
        }
    }

    /**
     * Asks for permission AND if given, gets current user's position
     */
    public void requestLocationPermission(){
        // request permissions
        if (ActivityCompat.checkSelfPermission(WalkInSession.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WalkInSession.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WalkInSession.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            // Write you code here if permission already given.
//            Toast.makeText(WalkInSession.this, "Permission already granted!", Toast.LENGTH_LONG).show();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            // Define the criteria how to select the location provider
            criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);   //default

            // user defines the criteria

            criteria.setCostAllowed(false);
            // get the best provider depending on the criteria
            provider = locationManager.getBestProvider(criteria, false);

            // the last known location of this provider
            Location location = locationManager.getLastKnownLocation("gps");

            mylistener = new MyLocationListener();

            if (location != null) {
                mylistener.onLocationChanged(location);
                // location updates: at least 1 meter and 200millsecs change
                locationManager.requestLocationUpdates(provider, 200, 1, mylistener);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
//                currentPosition = new LatLng(latitude, longitude);
//                currentPosition = LOCATION_CSUF;
                currentPosition = LOCATION_PARK;
            } else {
                // leads to the settings because there is no last known location
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }


        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            // Initialize the location fields
//            Toast.makeText(WalkInSession.this,  ""+location.getLatitude()+location.getLongitude(), Toast.LENGTH_SHORT).show();
            currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(WalkInSession.this, provider + "'s status changed to "+status +"!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(WalkInSession.this, "Provider " + provider + " enabled!",
                    Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(WalkInSession.this, "Provider " + provider + " disabled!",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
