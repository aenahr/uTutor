package ututor.edu.csulb.ututor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class WorkHourPicker extends AppCompatActivity {

    // initialize all buttons
    Button bMonday;
    Button bTuesday;
    Button bWednesday;
    Button bThursday;
    Button bFriday;
    Button bSaturday;
    Button bSunday;

    // initialize all ToggleButton Times
    ToggleButton twelveAM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_work_picker);

        // initialize Buttons
        initializeButtons();

        // initialize ToggleButtons
        initializeToggleButtons();

        twelveAM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) { twelveAM.setBackgroundColor(getResources().getColor(R.color.colorAccent)); }
                else if(!isChecked){ twelveAM.setBackgroundColor(getResources().getColor(R.color.greyish)); }
            }
        });
    }


    public void initializeButtons(){
        bMonday = (Button) findViewById(R.id.wMonday);
        bTuesday = (Button) findViewById(R.id.wTuesday);
        bWednesday = (Button) findViewById(R.id.wWednesday);
        bThursday = (Button) findViewById(R.id.wThursday);
        bFriday = (Button) findViewById(R.id.wFriday);
        bSaturday = (Button) findViewById(R.id.wSaturday);
        bSunday = (Button) findViewById(R.id.wSunday);
    }

    public void initializeToggleButtons(){
        //twelveAM = (ToggleButton) findViewById(R.id.a12);
    }
}
