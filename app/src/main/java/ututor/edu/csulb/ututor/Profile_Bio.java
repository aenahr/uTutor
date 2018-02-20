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
    EditText major;
    EditText email;
    EditText teach_exp;
    EditText education;
    Button submit;
    Button cancel;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        firstname =(EditText)findViewById(R.id.bio_fname);
        lastname = (EditText)findViewById(R.id.bio_lname);
        major = (EditText)findViewById(R.id.bio_major);
        email = (EditText)findViewById(R.id.bio_email);
        teach_exp =(EditText)findViewById(R.id.tex_exp);
        education =(EditText)findViewById(R.id.text_edu);
        submit =(Button)findViewById(R.id.Submit_bio);
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
        major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Major", major.getText().toString());
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Email", email.getText().toString());
            }
        });
        teach_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Teaching experience", teach_exp.getText().toString());
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
                if (major.getText().toString().isEmpty()) {
                    major.setError("What is your major ?");
                }
                if (email.getText().toString().isEmpty()) {
                    email.setError("What is your email?");
                }
                if (teach_exp.getText().toString().isEmpty()) {
                    teach_exp.setError("What is your teaching experience?");
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
