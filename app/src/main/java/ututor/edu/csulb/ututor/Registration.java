package ututor.edu.csulb.ututor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    EditText mFirst;
    EditText mLast;
    EditText mPassword;
    EditText mPasswordConfirm;
    EditText mUniversity;
    TextView mAlertPassWord;
    Button imgBack;
    public User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmail = (EditText) findViewById(R.id.etEmail);
        changeLayout = (Button) findViewById(R.id.register);
        mUniversity = (EditText) findViewById(R.id.etUniversity);
        mPassword = (EditText) findViewById(R.id.etPassword);
        mPasswordConfirm = (EditText) findViewById(R.id.etConfirmPassword);
        mFirst = (EditText) findViewById(R.id.etFirst);
        mLast = (EditText) findViewById(R.id.etLast);
        mAlertPassWord = (TextView) findViewById(R.id.alert);

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

        if( mPassword.getText().toString().equals(mPasswordConfirm.getText().toString()) ){

            //create new user in database and inout all values

            //create User
            currentUser = new User();
            currentUser.setFirstName(mFirst.getText().toString());
            currentUser.setLastName(mLast.getText().toString());
            currentUser.setEmail(mEmail.getText().toString());
            if( mUniversity.getText().toString().equals(null)){ //if user did not input anything
                currentUser.setUniversity("None");
            }else {
                currentUser.setUniversity(mUniversity.getText().toString());
            }
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ututorlogo); // drawable to bitmap
            currentUser.setProfilePic(bitmap);


            // notify user of success
            Toast.makeText(Registration.this, "Registration complete! Welcome, " + mFirst.getText().toString() + "!", Toast.LENGTH_LONG).show();

            // send user info to HomePage
            Intent i = new Intent(Registration.this, HomePage.class);
            i.putExtra("currentUser", currentUser);
            startActivity(i);
        }
        else{

            mAlertPassWord.setVisibility(View.VISIBLE);
            Animation shake = AnimationUtils.loadAnimation(Registration.this, R.anim.shake);
            mAlertPassWord.startAnimation(shake);
            Toast.makeText(Registration.this, "Pass: " + mPassword.getText().toString() + ", Confirm: " + mPasswordConfirm.getText().toString(), Toast.LENGTH_LONG).show();



        }
    }

    @Override
    public void onBackPressed() {

    }



}
