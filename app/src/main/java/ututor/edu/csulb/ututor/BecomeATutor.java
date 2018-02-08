package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class BecomeATutor extends AppCompatActivity {

    Button imgBack;
    Button apply;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_tutor);

        imgBack = (Button) findViewById(R.id.backButton);
        apply = (Button) findViewById(R.id.workButton);

        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // finish this activity and go back to log in
                Intent i = new Intent(BecomeATutor.this, HomePage.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);
                finish();


            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentUser.setTutor(true);

                // finish this activity and go back to log in
                Intent i = new Intent(BecomeATutor.this, HomePage.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

        // finish this activity and go back to log in
        Intent i = new Intent(BecomeATutor.this, HomePage.class);
        i.putExtra("currentUser", currentUser);
        startActivity(i);
        finish();

    }
}