package ututor.edu.csulb.ututor;

import android.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class WalkInActivity extends AppCompatActivity {

    Button newSession;
    Button endSession;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkin);

        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        //confirm new appointment added in user
        if( i.getExtras().getInt("session") == 1){
            Toast.makeText(getApplicationContext(), "New Walk-In Session Added!" , Toast.LENGTH_SHORT).show();
        }

        newSession = (Button) findViewById(R.id.addWalkIn);
        endSession = (Button) findViewById(R.id.endWalkIn);

        requestLocationPermission();
        currentUser.setWalkIn(true); // is available for walk-in

        // get new number of Appointments

        newSession.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent i = new Intent(WalkInActivity.this, WalkInSession.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);

            }
        });


        endSession.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                // exiting walk-in availability section
                currentUser.setWalkIn(false);

                // TODO update database with all new appointments made


                // redirect to homepage
                Intent i = new Intent(WalkInActivity.this, HomePage.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);
                finish();

            }
        });

    }


    @Override
    public void onBackPressed() {
        // cannot go back by pressing back

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Invalid End Sesson. Please press 'END WALK-IN SESSION' to exit.")
                .setIcon(R.drawable.warning_icon)
                .setTitle("Error Ending Session")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void requestLocationPermission(){
        // request permissions
        if (ActivityCompat.checkSelfPermission(WalkInActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WalkInActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WalkInActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            // Write you code here if permission already given.
        }
    }

}