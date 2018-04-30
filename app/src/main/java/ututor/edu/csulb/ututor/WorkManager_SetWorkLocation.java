package ututor.edu.csulb.ututor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by aenah on 4/30/18.
 */

public class WorkManager_SetWorkLocation extends AppCompatActivity {

    User currentUser;

    LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkin);


        // send back to
        Intent returnIntent = new Intent();
        returnIntent.putExtra("locCoord",location);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
