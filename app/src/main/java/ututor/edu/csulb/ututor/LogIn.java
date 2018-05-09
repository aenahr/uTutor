package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;


public class LogIn extends AppCompatActivity {

    public EditText mEmail;
    public EditText mPassword;
    public Button mSignIn;
    public Button mSignUp;
    public String email;
    public String password;
    public CheckBox mRemember;
    public ImageView mLogo;
    public TextView mTitle;
    public TextView mForgot;
    public TextView mAlert;
    TextView mAlertDatabase;

    //temp
    String tempPassword = "yes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mSignIn = (Button) findViewById(R.id.bSignInButton);
        mSignUp = (Button) findViewById(R.id.bSignUpButton);
        mRemember = (CheckBox) findViewById(R.id.rememberMe);
        mLogo = (ImageView) findViewById(R.id.logo);
        mTitle = (TextView) findViewById(R.id.titleLogo);
        mAlert = (TextView) findViewById(R.id.alertPassword);
        mAlertDatabase = (TextView) findViewById(R.id.alertDatabase);
        //mForgot = (TextView) findViewById(R.id.forgotPassword);

        Animation expandIn = AnimationUtils.loadAnimation(this, R.anim.expand);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1500);

        // start loading animation
        mLogo.startAnimation(expandIn);
        mEmail.setAnimation(fadeIn);
        mPassword.setAnimation(fadeIn);
        mSignIn.setAnimation(fadeIn);
        mSignUp.setAnimation(fadeIn);
        mTitle.setAnimation(fadeIn);
        mRemember.setAnimation(fadeIn);
        //mForgot.setAnimation(fadeIn);


        //load shared preferences to check if Remember Me was clicked
        SharedPreferences sp = getSharedPreferences("TEAM_ANDROID", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        boolean checkSaved = sp.getBoolean("isSaved", false);

        if (checkSaved == true) {
            String insertEmail = sp.getString("Email", null);
            String insertPassword = sp.getString("Password", null);

            mEmail.setText(insertEmail);
            mPassword.setText(insertPassword);
            mRemember.setChecked(true);
        }


        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to Registration Activity
                startActivity(new Intent(LogIn.this, Registration.class));
                finish();
            }
        });


        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set alerts invisible to avoid clashing
                mAlert.setVisibility(View.INVISIBLE);
                mAlertDatabase.setVisibility(View.INVISIBLE);

                if (mEmail.getText().toString().equals("")) { // if email is blank
                    mAlert.setVisibility(View.VISIBLE);
                    Animation shake = AnimationUtils.loadAnimation(LogIn.this, R.anim.shake);
                    mAlert.startAnimation(shake);

                } else {
                    try {
                        Animation shake = AnimationUtils.loadAnimation(LogIn.this, R.anim.shake);
                        System.out.println("Sending Login Request");
                        //response.get("error") will give you the error code
                        //response.get("errormessage") will print out what exactly happened, should be used for debugging,
                        //user probably doesn't need to know this
                        // -1: Query Failed, -2: Invalid Email, -3: Invalid Password
                        JSONObject response = new ServerRequester().execute("login.php", "whatever", "email", mEmail.getText().toString(), "password", mPassword.getText().toString()).get();
                        //if (response.isNull("success")) { //Interchangable with response.isNull("isTutor")), use whichever is better
                        if(response.isNull("isTutor")){    //This is what the last query returns, if this is null the Server didn't go through all the queries therefore there is some error
                            if (!response.isNull("error")) {//If the server returned an error code (So the request was at least processed)
                                switch (response.get("error").toString()) {
                                    case "-1": //Some query failed, Server error

                                        break;
                                    case "-2"://Invalid Email, Email wasn't in the database
                                        mAlertDatabase.setVisibility(View.VISIBLE);
                                        mAlertDatabase.startAnimation(shake);
                                        break;
                                    case "-3"://Email was in the database but Password was wrong
                                        mAlertDatabase.setVisibility(View.VISIBLE);
                                        mAlertDatabase.startAnimation(shake);
                                        break;
                                    default: //Some unhandled error code was returned

                                        break;
                                }
                            } else {//The server had a problem but didn't return an error code, something went horribly wrong with the connection to the server or the server itself

                            }
                        } else {//Server Responded with a "Success", So everything is hunky dory

                            //TODO Delete These Next 2 Lines when you are done debugging
                            System.out.println("Received Login Response");
                            System.out.println(response.toString());
                            // if remember me is selected
                            if (mRemember.isChecked()) {
                                String sEmail = mEmail.getText().toString();
                                String sPass = mPassword.getText().toString();

                                SharedPreferences pref = getApplicationContext().getSharedPreferences("TEAM_ANDROID", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putBoolean("isSaved", true);
                                editor.putString("Email", sEmail);
                                editor.putString("Password", sPass);
                                editor.commit();
                            } else {
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("TEAM_ANDROID", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.clear();
                                editor.apply();
                            }

                            //upload server info about User to the User class
                            // get info from Database and set them to the User class
                            // for now, I've just set random values
                            User cUser = new User();
                            cUser.setEmail(response.get("email").toString());
                            if (response.get("isTutor").toString().equals("true")) {
                                cUser.setTutor(true);
                            }
                            if (response.get("isTutor").toString().equals("false")) {
                                cUser.setTutor(false);
                            }
                            cUser.setFirstName(response.get("firstName").toString());
                            cUser.setLastName(response.get("lastName").toString());
                            cUser.setDescription(response.get("userDescription").toString());
                            if(response.isNull("profilePic")) {
                                cUser.setNumProfilePic(0);
                            }else{
                                cUser.setNumProfilePic(response.getInt("profilePic"));
                            }
//                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ututorlogo); // drawable to bitmap
//                            cUser.setProfilePic(bitmap);

                            // send user info to HomePage
                            Intent i = new Intent(LogIn.this, HomePage.class);
                            i.putExtra("currentUser", cUser);
                            startActivity(i);
                            finish();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    /**
     * Temporary Skip button until database is created
     *
     * @param view
     */
    public void byPassLogIn(View view) {

        //validate username and email in the database


        // TEMPORARY - set random variables
        mEmail.setText("test@test.com");
        mPassword.setText("yes");

        // if remember me is selected
        if (mRemember.isChecked()) {
            String sEmail = mEmail.getText().toString();
            String sPass = mPassword.getText().toString();

            SharedPreferences pref = getApplicationContext().getSharedPreferences("TEAM_ANDROID", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isSaved", true);
            editor.putString("Email", sEmail);
            editor.putString("Password", sPass);
            editor.commit();
        } else {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("TEAM_ANDROID", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.apply();

        }

        //upload server info about User to the User class
        User cUser = new User();
        cUser.setEmail(mEmail.getText().toString());
        cUser.setTutor(false);
        cUser.setFirstName("Testy");
        cUser.setLastName("Test");
        double num = 3.5;
        cUser.setRating((float) num);

        ProfilePicture p = new ProfilePicture(LogIn.this);
        // from datbase
        cUser.setNumProfilePic(2);
        p.setColor(2);

//        Bitmap bitmap = p.getBitmapColor();
//        cUser.setProfilePic(bitmap);

        // user description
        cUser.setDescription("Hello my name is Testy. I love testing things cause it's part of my name. Did you know what my last name is? It's test!");

        // send user info to HomePage
        Intent i = new Intent(LogIn.this, HomePage.class);
        i.putExtra("currentUser", cUser);
        startActivity(i);
        finish();
    }
}

