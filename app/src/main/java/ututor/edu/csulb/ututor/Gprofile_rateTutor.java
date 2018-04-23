package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

/**
 * Created by Henry Tran on 2/12/2018.
 */

public class Gprofile_rateTutor extends AppCompatActivity {

    User currentUser;
    User otherUser;
    EditText userFeedback;
    Button submit;
    RatingBar userRate;
    ImageView profilePic;
    TextView userName;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gprofile_ratetutor);

        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        otherUser = (User)i.getSerializableExtra("otherUser");

        userFeedback = (EditText) findViewById(R.id.text_feedback);
        userRate = (RatingBar) findViewById(R.id.rateUser);
        submit =(Button)findViewById(R.id.submit_feedback);
        profilePic = (ImageView) findViewById(R.id.profilePicture);
        userName = (TextView) findViewById(R.id.firstAndLast);

        // initialize profile pic
        ProfilePicture p = new ProfilePicture(this);
        p.setColor(otherUser.getuNumProfilePic());
        profilePic.setImageBitmap(p.getBitmapColor());

        // initialize name
        userName.setText(otherUser.getFirstName() + " " + otherUser.getLastName());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO Lance send rating and review feedback
                JSONObject response = null;
                try {
//            String[] name =  userinput.getText().toString().split(" ");
                    //          String firstname = name[0];
                    //         String lastname = name[1];
                    response = new ServerRequester().execute("rate.php", "whatever"
                            ,"recipientEmail", otherUser.getEmail()
                            ,"raterEmail", currentUser.getEmail()
                            ,"rating", Float.toString(userRate.getRating())
                            ,"feedback", userFeedback.getText().toString()
                    ).get();
                    if (response == null) {//Something went horribly wrong, JSON failed to be formed meaning something happened in the server requester
                    } else if (!response.isNull("error")) {//Some incorrect information was sent, but the server and requester still processed it
                        //TODO Handle Server Errors
                        switch (response.get("error").toString()) {
                            default:    //Some Error Code was printed from the server that isn't handled above

                                break;
                        }
                    } else {//Everything went well
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // for getting rating: userRate.getRating();
                // for getting feedback: userFeedback.getText().toString();

                Toast.makeText(getApplicationContext(), "Feedback to " + otherUser.getFirstName() + "  sent!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
