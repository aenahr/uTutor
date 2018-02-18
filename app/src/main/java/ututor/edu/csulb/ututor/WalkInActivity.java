package ututor.edu.csulb.ututor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


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

        // TODO set availability of walk-in to database
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

    protected void onStop(){

        // TODO alert database that user's walkIn is false now AND update all the walkIns tutor has completed
        currentUser.setWalkIn(false);
        Toast.makeText(getApplicationContext(),"HEHEHEHEHEH", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

}