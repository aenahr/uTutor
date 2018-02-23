package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MyProfile_Edit extends AppCompatActivity {

    User currentUser;
    EditText firstName;
    EditText lastName;
    EditText eEmail;
    EditText collegeName;
    Button saveChanges;
    Button cancelEdit;
    EditText currentPassword;
    EditText newPassword;
    boolean emailAlreadyInDatabase = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_edit);

        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");

        // initialize all objects
        firstName = (EditText) findViewById(R.id.bio_fname);
        lastName = (EditText) findViewById(R.id.bio_lname);
        eEmail = (EditText) findViewById(R.id.bio_email);
        collegeName = (EditText) findViewById(R.id.text_edu);
        saveChanges = (Button) findViewById(R.id.saveChanges);
        cancelEdit = (Button) findViewById(R.id.Cancel_bio);
        currentPassword = (EditText) findViewById(R.id.bio_currentPass);
        newPassword = (EditText)findViewById(R.id.bio_newPass);


        // set from User's values
        firstName.setHint(currentUser.getFirstName());
        lastName.setHint(currentUser.getLastName());
        eEmail.setHint(currentUser.getEmail());
        collegeName.setHint(currentUser.getUniversity());


        saveChanges.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // TODO do checks on email if it is already in the database

                if(emailAlreadyInDatabase == true){ // send error message

                } else{ // set new values in database

                    // TODO set changes to user in database
                    if(!firstName.getText().toString().matches("")){ currentUser.setFirstName(firstName.getText().toString());}
                    if(!lastName.getText().toString().matches("")){ currentUser.setLastName(lastName.getText().toString());}
                    if(!eEmail.getText().toString().matches("")){
                        String oldEmail = currentUser.getEmail(); // NEEDED TO SEARCH THROUGH DATABASE FOR CURRENT USER
                        currentUser.setEmail(eEmail.getText().toString());
                    }
                    if(!collegeName.getText().toString().matches("")){ currentUser.setUniversity(collegeName.getText().toString());}

                    // this is the current Password
                    String cPassword = currentPassword.getText().toString();
                    String nPassword = newPassword.getText().toString();

                    if(!currentPassword.getText().toString().matches("") || !newPassword.getText().toString().matches("")){
                        // IF THEY DID INPUT SOMETHING TO CHANGE THE PASSWORD
                    }

                    // go back to profile
                    Intent i = new Intent(MyProfile_Edit.this, HomePage.class);
                    i.putExtra("currentUser", currentUser);
                    i.putExtra("uploadPage", "myProfile");
                    startActivity(i);
                }
            }
        });

        cancelEdit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                MyProfile_Edit.super.onBackPressed();;
            }
        });


    }
}