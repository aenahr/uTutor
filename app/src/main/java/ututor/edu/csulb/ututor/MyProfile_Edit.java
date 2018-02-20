package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MyProfile_Edit extends AppCompatActivity {

    User currentUser;
    EditText firstName;
    EditText lastName;
    EditText eEmail;
    EditText collegeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_edit);

        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");

        firstName = (EditText) findViewById(R.id.bio_fname);
        lastName = (EditText) findViewById(R.id.bio_lname);
        eEmail = (EditText) findViewById(R.id.bio_email);
        collegeName = (EditText) findViewById(R.id.text_edu);

        // set from User's values
        firstName.setHint(currentUser.getFirstName());
        lastName.setHint(currentUser.getLastName());
        eEmail.setHint(currentUser.getEmail());
        collegeName.setHint(currentUser.getUniversity());



    }
}