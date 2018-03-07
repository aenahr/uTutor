package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class BecomeATutor extends AppCompatActivity {

    Button imgBack;
    Button apply;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_tutor);

        imgBack = (Button) findViewById(R.id.backButton);
        apply = (Button) findViewById(R.id.workButton);

        Intent i = getIntent();
        currentUser = (User) i.getSerializableExtra("currentUser");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // finish this activity and go back to log in
                Intent i = new Intent(BecomeATutor.this, HomePage.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);
                finish();


            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: this user is now a tutor tehee
                try {
                    ServerRequester request = new ServerRequester();

                    JSONObject response = request.execute("becomeATutor.php", "whatever", "email", currentUser.getEmail()).get();
                    //TODO: Handle Server Errors teehee
                    if (response.isNull("success")) { //If the server didn't return a success message
                        if (response.isNull("error")) { //If the server didn't return a success or error message
                            //Something went horribly wrong with the server, contact your local Lance for assistance

                            //Just threw this part in here from Aenah's original code
                        } else {//Server Returned an error Code, need to be handled here
                            switch (response.get("error").toString()) {
                                case "-1":
                                    //Statement Failed To Execute
                                    break;
                                case "-2":
                                    //Statement Failed to Bind Parameters, server runtime error
                                    break;
                                case "-3":
                                    //Query Failed to Prepare, Server Error
                                    break;
                                case "-4":
                                    //Query was successful but no rows were affected
                                    break;
                                case "-5":
                                    //Query Succeeded Execution but..affected a negative number of rows, Should Never get this error... Panic time if you do
                                    break;
                                default:
                                    //Some error code that was unhandled was returned
                                    break;
                            }
                        }
                    } else {//Server returned a Success message, everything is A-OK
                        currentUser.setTutor(true);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // finish this activity and go back to log in
                Intent i = new Intent(BecomeATutor.this, HomePage.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

        // finish this activity and go back to log in
        Intent i = new Intent(BecomeATutor.this, HomePage.class);
        i.putExtra("currentUser", currentUser);
        startActivity(i);
        finish();

    }
}

