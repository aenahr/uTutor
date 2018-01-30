package ututor.edu.csulb.ututor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class Registration extends AppCompatActivity {

    Button changeLayout;
    EditText mEmail;
    EditText mPassword;
    EditText mPasswordConfirm;
    TextView mAlert;
    Button imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmail = (EditText) findViewById(R.id.etEmail);
        changeLayout = (Button) findViewById(R.id.register);
        mPassword = (EditText) findViewById(R.id.etPassword);
        mPasswordConfirm = (EditText) findViewById(R.id.etConfirmPassword);
        mAlert = (TextView) findViewById(R.id.alert);

        imgBack = (Button) findViewById(R.id.backButton);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // finish this activity and go back to log in
                startActivity(new Intent(Registration.this, LogIn.class));
                finish();


            }
        });

    }

    public void registerUser(View view){

        if( mPassword.equals(mPasswordConfirm)){
            // get user input
            String value = mEmail.getText().toString();

            // notify user of success
            Toast.makeText(Registration.this, "Registration complete! Welcome, " + value + "!", Toast.LENGTH_LONG).show();

            //go to homepage of app
            Intent i = new Intent(Registration.this, HomePage.class);
            startActivity(i);
            finish();
        }
        else{

            mAlert.setVisibility(View.VISIBLE);
            Animation shake = AnimationUtils.loadAnimation(Registration.this, R.anim.shake);
            mAlert.startAnimation(shake);


        }


    }



}
