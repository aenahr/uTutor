package ututor.edu.csulb.ututor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


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

        newSession = (Button) findViewById(R.id.addWalkIn);
        endSession = (Button) findViewById(R.id.endWalkIn);

        newSession.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent i = new Intent(WalkInActivity.this, WalkInSession.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);

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
}