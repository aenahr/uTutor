package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class MyProfile_Bio extends AppCompatActivity {

    User currentUser;
    User otherUser;

    TextView mFName;
    TextView mLName;
    TextView mEmail;
    TextView mDescription;
    TextView mEducation;
    TextView mSubjects;
    TextView mSubjectsTitle;
    Button imgBack;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gprofile_bio);


        // get user's data
        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        mFName = (TextView) findViewById(R.id.gbio_firstname);
        mLName = (TextView) findViewById(R.id.gbio_lastname);
        mEmail = (TextView) findViewById(R.id.gbio_email);
        mDescription = (TextView) findViewById(R.id.gdes);
        mEducation = (TextView) findViewById(R.id.text_ed);
        mSubjects = (TextView) findViewById(R.id.gSubjects);
        mSubjectsTitle = (TextView) findViewById(R.id.gSubjects_Title);
        imgBack = (Button) findViewById(R.id.backButton);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // check if tutor or not
        if(currentUser.isTutor() == true){
            ArrayList<String> subjects = currentUser.getSubjectsTaught();
            if(subjects.isEmpty()){ mSubjects.setText("None"); }
            else{
                StringBuilder stringBuilder = new StringBuilder();
                for(int x = 0; x < subjects.size()-1; x++){
                    stringBuilder.append(subjects.get(x) + ", ");
                }
                stringBuilder.append(subjects.get(subjects.size()-1));
                mSubjects.setText(stringBuilder.toString());
            }
        }
        else{ // not a tutor - don't show subjects taught
            mSubjects.setVisibility(View.INVISIBLE);
            mSubjectsTitle.setVisibility(View.INVISIBLE);
        }

        // set data for current user
        mFName.setText(currentUser.getFirstName());
        mLName.setText(currentUser.getLastName());
        mEmail.setText(currentUser.getEmail());
        mDescription.setText(currentUser.getDescription());
        mEducation.setText(currentUser.getUniversity());



    }
}
