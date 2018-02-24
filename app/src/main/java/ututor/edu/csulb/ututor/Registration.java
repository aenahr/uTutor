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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class Registration extends AppCompatActivity {

    Button changeLayout;
    EditText mEmail;
    EditText mFirst;
    EditText mLast;
    EditText mPassword;
    EditText mPasswordConfirm;
    EditText mUniversity;
    TextView mAlertPassWord;
    TextView mAlertInput;
    TextView mAlertEmail;
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
        mAlertInput = (TextView) findViewById(R.id.alertInput);
        mAlertEmail = (TextView) findViewById(R.id.alertEmail);


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

    public void registerUser(View view) {
        //to prevent overlap errors, set ALL errors invisible first
        mAlertPassWord.setVisibility(View.INVISIBLE);
        mAlertInput.setVisibility(View.INVISIBLE);
        mAlertEmail.setVisibility(View.INVISIBLE);

        boolean validRegistration = testForNull();

        if (mPassword.getText().toString().equals(mPasswordConfirm.getText().toString()) && validRegistration == true) {
            /////////////
            /// PROCESS OF CREATING NEW USER IN DATABASE
            ////////////
            JSONObject response = null;
            try {
                response = new ServerRequester().execute("register.php", "whatever",
                        "email", mEmail.getText().toString(),
                        "password", mPassword.getText().toString(),
                        "firstname", mFirst.getText().toString(),
                        "lastname", mLast.getText().toString(),
                        "university", mUniversity.getText().toString()).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }Toast.makeText(Registration.this, "Registration complete! Welcome, " + mFirst.getText().toString() + "!", Toast.LENGTH_LONG).show();

            try {
                if (response.get("success").equals("true")) {
                    currentUser = new User();
                    currentUser.setFirstName(mFirst.getText().toString());
                    currentUser.setLastName(mLast.getText().toString());
                    currentUser.setEmail(mEmail.getText().toString());
                    if (mUniversity.getText().toString().equals(null)) { //if user did not input anything
                        currentUser.setUniversity("None");
                    } else {
                        currentUser.setUniversity(mUniversity.getText().toString());
                    }
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ututorlogo); // drawable to bitmap
                    currentUser.setProfilePic(bitmap);
                    // notify user of success
                    // send user info to HomePage
                    Intent i = new Intent(Registration.this, HomePage.class);
                    i.putExtra("currentUser", currentUser);
                    startActivity(i);
                } else {
                    //Something Went Wrong in the DB side
                    mAlertEmail.setVisibility(View.VISIBLE);
                    Animation shake = AnimationUtils.loadAnimation(Registration.this, R.anim.shake);
                    mAlertEmail.startAnimation(shake);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (validRegistration == false) { //required fields not filled

            mAlertInput.setVisibility(View.VISIBLE);
            Animation shake = AnimationUtils.loadAnimation(Registration.this, R.anim.shake);
            mAlertInput.startAnimation(shake);
        } else { //password does not match
            mAlertPassWord.setVisibility(View.VISIBLE);
            Animation shake = AnimationUtils.loadAnimation(Registration.this, R.anim.shake);
            mAlertPassWord.startAnimation(shake);
        }

    }

    /**
     * Method that tests if all required fields are inputted
     *
     * @return
     */
    public boolean testForNull() {
        if (mEmail.getText().toString().equals("")) {
            return false;
        }
        if (mFirst.getText().toString().equals("")) {
            return false;
        }
        if (mLast.getText().toString().equals("")) {
            return false;
        }
        if (mPassword.getText().toString().equals("")) {
            return false;
        }
        if (mPasswordConfirm.getText().toString().equals("")) {
            return false;
        }

        //returns true if all requirements are met
        return true;
    }

    @Override
    public void onBackPressed() {
        // finish this activity and go back to log in
        startActivity(new Intent(Registration.this, LogIn.class));
        finish();

    }


}
