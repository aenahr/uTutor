package ututor.edu.csulb.ututor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by aenah on 5/5/18.
 */

public class Search_MapView extends AppCompatActivity {

    GoogleMap mGoogleMap;

    Button newSession;
    Button endSession;
    User currentUser;
    SeekBar seekBar;
    TextView seekBarValue;
    int mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkin);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.incrementProgressBy(10);
        seekBar.setProgress(50);
        seekBar.setMax(100);
        seekBarValue = (TextView)findViewById(R.id.seekBarValue);
        seekBarValue.setText(String.valueOf(seekBar.getProgress()) + " miles");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 10;
                progress = progress * 10;
                mProgress = progress;
                seekBarValue.setText(String.valueOf(progress) + " miles");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void drawCircle(LatLng point, int radius){

        Toast.makeText(this, "I'm here", Toast.LENGTH_SHORT).show();

        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle
        circleOptions.center(point);

        // Radius of the circle
        circleOptions.radius(radius);

        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK);

        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000);

        // Border width of the circle
        circleOptions.strokeWidth(2);
        // Adding the circle to the GoogleMap
        mGoogleMap.addCircle(circleOptions);
    }
}