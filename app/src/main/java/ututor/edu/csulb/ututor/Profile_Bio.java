package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Henry Tran on 2/12/2018.
 */

public class Profile_Bio extends AppCompatActivity {

    EditText firstname;
    EditText lastname;
    EditText email;
    EditText education;
    Button submit;
    Button cancel;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        firstname =(EditText)findViewById(R.id.bio_fname);
        lastname = (EditText)findViewById(R.id.bio_lname);
        email = (EditText)findViewById(R.id.bio_email);
        education =(EditText)findViewById(R.id.text_edu);
        submit =(Button)findViewById(R.id.saveChanges);
        cancel=(Button)findViewById(R.id.Cancel_bio);

        firstname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("First Name", firstname.getText().toString());
            }
        });
        lastname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Last Name", lastname.getText().toString());
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Email", email.getText().toString());
            }
        });

        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Education", education.getText().toString());
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstname.getText().toString().isEmpty()) {
                    firstname.setError("What is your first name?");
                }
                if (lastname.getText().toString().isEmpty()) {
                    firstname.setError("What is your last name?");
                }
                if (email.getText().toString().isEmpty()) {
                    email.setError("What is your email?");
                }
                if(education.getText().toString().isEmpty())
                {
                    education.setError("What is your Education?");
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile_Bio.this, MyProfile.class);
                finish();
            }
        });


    }
}
