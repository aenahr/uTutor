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
                //TODO Implement Error Checks on a Server Response
                if (response.isNull("success")) { //If the server didn't return a success message
                    if (response.isNull("error")) { //If the server didn't return a success or error message
                        //Something went horribly wrong with the server, contact your local Lance for assistance

                        //Just threw this part in here from Aenah's original code
                        mAlertEmail.setVisibility(View.VISIBLE);
                        Animation shake = AnimationUtils.loadAnimation(Registration.this, R.anim.shake);
                        mAlertEmail.startAnimation(shake);
                    } else {//Server Returned an error Code, need to be handled here
                        switch (response.get("error").toString()) {
                            case "-1":
                                //Statement failed to execute, server runtime error
                                break;
                            case "-2":
                                //Statement Failed to Bind Parameters, server runtime error
                                break;
                            case "-3":
                                //Query Failed to Prepare, Server Error
                                break;
                            case "-4":
                                //Query was successful but no rows were affected,
                                break;
                            case "-5":
                                //Query Succeeded Execution but..affected a negative number of rows, Should Never get this error... Panic time if you do
                                break;
                            case "-6":
                                //Query Failed to Execute Due to Email Already Existing in the Database Should display something to the user saying to use a different email
                                break;
                            case "-7":
                                //Query Failed to Execute for some other uncaught reason, probably a server error
                                break;
                            default:
                                //Some error code that was unhandled was returned
                                break;
                        }
                    }
                } else {//Server returned a Success message, everything is A-OK
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
                    Toast.makeText(Registration.this, "Registration complete! Welcome, " + mFirst.getText().toString() + "!", Toast.LENGTH_LONG).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (validRegistration == false) { //required fields not filled
            //TODO Figure out if this part is still necessary, might have some use as front-end validation so we don't send a request if something is obviously with the user's input
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
