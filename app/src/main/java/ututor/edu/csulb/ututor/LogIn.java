package ututor.edu.csulb.ututor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LogIn extends AppCompatActivity {

    public EditText mEmail;
    public EditText mPassword;
    public Button mSignIn;
    public Button mSignUp;
    public String email;
    public String password;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mSignIn = (Button) findViewById(R.id.bSignInButton);
        mSignUp = (Button) findViewById(R.id.bSignUpButton);


        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // notify user of success
                Toast.makeText(LogIn.this, "Please Register", Toast.LENGTH_LONG).show();
                // Intent

                startActivity(new Intent(LogIn.this, Registration.class));
                finish();
            }
        });


        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // notify user of success
                Toast.makeText(LogIn.this, "Going to home page", Toast.LENGTH_LONG).show();

            }
        });


    }


    public void forgotPassword(View view){


    }
}

