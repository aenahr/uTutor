package ututor.edu.csulb.ututor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class LogIn extends AppCompatActivity {

    public EditText mEmail;
    public EditText mPassword;
    public Button mSignIn;
    public Button mSignUp;
    public String email;
    public String password;
    public CheckBox mRemember;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mSignIn = (Button) findViewById(R.id.bSignInButton);
        mSignUp = (Button) findViewById(R.id.bSignUpButton);
        mRemember = (CheckBox) findViewById(R.id.rememberMe);

        //load shared preferences
        SharedPreferences sp = getSharedPreferences("TEAM_ANDROID", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        boolean checkSaved = sp.getBoolean("isSaved", false);

        if(checkSaved == true){

            Toast.makeText(LogIn.this, "Loading Saved Info", Toast.LENGTH_LONG).show();
            String insertEmail = sp.getString("Email", null);
            String insertPassword = sp.getString("Password", null);

            mEmail.setText(insertEmail);
            mPassword.setText(insertPassword);

            mRemember.setChecked(true);
        }




        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // notify user of success
                // Intent
                startActivity(new Intent(LogIn.this, Registration.class));
                finish();
            }
        });


        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // validate email & password, so access database

                // store to shared preferences if remember me is checked


                // notify user of success
                Toast.makeText(LogIn.this, "Going to home page", Toast.LENGTH_LONG).show();

            }
        });


    }


    public void forgotPassword(View view){


    }


    public void byPassLogIn(View view){

        //validate username and email

        // check if remember me is selected
        if(mRemember.isChecked()){
            Toast.makeText(LogIn.this, "Saving log-in info", Toast.LENGTH_LONG).show();
            String sEmail = mEmail.getText().toString();
            String sPass = mPassword.getText().toString();

            SharedPreferences pref = getApplicationContext().getSharedPreferences("TEAM_ANDROID", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            editor.putBoolean("isSaved", true);

            editor.putString("Email", sEmail);
            editor.putString("Password", sPass);

            editor.commit();
        }
        else{

            SharedPreferences pref = getApplicationContext().getSharedPreferences("TEAM_ANDROID", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.apply();

        }

        // load homepage
        startActivity(new Intent(LogIn.this, HomePage.class));
        finish();


    }
}

