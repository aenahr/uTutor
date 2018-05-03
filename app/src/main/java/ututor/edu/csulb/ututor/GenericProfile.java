package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class GenericProfile extends AppCompatActivity {

    TextView user_name;
    ImageView user_image;
    TextView favorite;
    LinearLayout cbiography;
    LinearLayout cfavorite;
    LinearLayout cmessage;
    LinearLayout creadreview;
    LinearLayout crate;
    LinearLayout schedule_appointment;
    ImageView starImage;
    User currentUser;
    User otherUser;
    boolean isFavorite;
    RatingBar ratingBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.genericprofile);

        user_name = (TextView) findViewById(R.id.userName);
        cbiography = (LinearLayout) findViewById(R.id.gprofile_cardBio);
        cfavorite = (LinearLayout)findViewById(R.id.gprofile_cardFavorite);
        crate = (LinearLayout) findViewById(R.id.gprofile_cardRate);
        cmessage = (LinearLayout) findViewById(R.id.gprofile_cardMessage);
        creadreview = (LinearLayout) findViewById(R.id.gprofile_cardReadReview);
        schedule_appointment = (LinearLayout) findViewById(R.id.gprofile_cardScheduleAppoint);
        user_image = (ImageView)findViewById(R.id.profile_userImage);
        favorite = (TextView) findViewById(R.id.wordFavorite);
        starImage = (ImageView)findViewById(R.id.starFavorite);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar2);

        // get user's data
        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        otherUser = (User)i.getSerializableExtra("otherUser");
        otherUser.setTutor(true);

        JSONObject response = null;
        try {
            response = new ServerRequester().execute("determineFavorite.php", "whatever"
                    ,"favoritorEmail", currentUser.getEmail()
                    ,"favoriteeEmail", otherUser.getEmail()
            ).get();
            if (response == null) {//Something went horribly wrong, JSON failed to be formed meaning something happened in the server requester

            } else if (!response.isNull("error")) {//Some incorrect information was sent, but the server and requester still processed it
                //TODO Handle Server Errors
                switch (response.get("error").toString()) {
                    case "-2": //TODO if the user is not a favorite, the flow will land here... so handle this
                        isFavorite=false;
                        break;
                    default:    //Some Error Code was printed from the server that isn't handled above

                        break;
                }
            } else { //Everything Went Well, User is a favorite
                isFavorite=true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        // set rating
        ratingBar.setRating(otherUser.getRating());

        // set full name
        String fullName = otherUser.getFirstName()+" " + otherUser.getLastName();
        user_name.setText(fullName);

        //set display image
        ProfilePicture p = new ProfilePicture(this);
        p.setColor(otherUser.getuNumProfilePic());
        user_image.setImageBitmap(p.getBitmapColor());

        // set favorite color/word
        if(isFavorite){
            starImage.setBackgroundResource(R.drawable.ic_star_colored);
            favorite.setText("Unfavorite User");

        }else{
            starImage.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
            favorite.setText("Favorite User");
        }



        cbiography.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(GenericProfile.this, GProfile_Bio.class);
                i.putExtra("otherUser", otherUser);
                startActivity(i);

            }
        });

        cfavorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (currentUser.getEmail().equals(otherUser.getEmail())) { Toast.makeText(GenericProfile.this, "You cannot favorite yourself",Toast.LENGTH_LONG).show();}
                else {
                    if(!isFavorite){
                        JSONObject response = null;
                        try {
                            response = new ServerRequester().execute("favorite.php", "whatever"
                                    ,"favoritorEmail", currentUser.getEmail()
                                    ,"favoriteeEmail", otherUser.getEmail()
                            ).get();
                            if (response == null) {//Something went horribly wrong, JSON failed to be formed meaning something happened in the server requester

                            } else if (!response.isNull("error")) {//Some incorrect information was sent, but the server and requester still processed it
                                //TODO Handle Server Errors
                                switch (response.get("error").toString()) {
                                    default:    //Some Error Code was printed from the server that isn't handled above

                                        break;
                                }
                            } else { //Everything Went Well
                                starImage.setBackgroundResource(R.drawable.ic_star_colored);
                                favorite.setText("Unfavorite User");
                                isFavorite = true;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }else{
                        JSONObject response = null;
                        try {
                            response = new ServerRequester().execute("unFavorite.php", "whatever"
                                    ,"favoritorEmail", currentUser.getEmail()
                                    ,"favoriteeEmail", otherUser.getEmail()
                            ).get();
                            if (response == null) {//Something went horribly wrong, JSON failed to be formed meaning something happened in the server requester

                            } else if (!response.isNull("error")) {//Some incorrect information was sent, but the server and requester still processed it
                                //TODO Handle Server Errors
                                switch (response.get("error").toString()) {
                                    default:    //Some Error Code was printed from the server that isn't handled above

                                        break;
                                }
                            } else { //Everything Went Well
                                starImage.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                                favorite.setText("Favorite User");
                                isFavorite = false;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        crate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser.getEmail().equals(otherUser.getEmail())) {Toast.makeText(GenericProfile.this,"You cannot rate yourself.",Toast.LENGTH_LONG).show();}
                else {
                    // go to rate tutor page
                    Intent i = new Intent(GenericProfile.this,Gprofile_rateTutor.class);
                    i.putExtra("currentUser",currentUser);
                    i.putExtra("otherUser",otherUser);
                    startActivity(i);
                }
            }
        });

        creadreview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(GenericProfile.this, Profile_readreview.class);
                i.putExtra("otherUser", otherUser);
                i.putExtra("from", "GenericProfile");
                startActivity(i);
            }
        });



        schedule_appointment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (currentUser.getEmail().equals(otherUser.getEmail()))
                {
                    Toast.makeText(GenericProfile.this,"You cannot make an appointment with yourself.",Toast.LENGTH_LONG).show();
                }else
                {
                    JSONObject response = null;
                    try {
                        response = new ServerRequester().execute("fetchWorkHours.php", "whatever",
                                "email", otherUser.getEmail()
                        ).get();
                        if (response == null) {//Something went horribly wrong, JSON failed to be formed meaning something happened in the server requester

                        } else if (!response.isNull("error")) {//Some incorrect information was sent, but the server and requester still processed it
                            //TODO Handle Server Errors
                            switch (response.get("error").toString()) {
                                default:    //Some Error Code was printed from the server that isn't handled above

                                    break;
                            }
                        } else { //Everything Went Well
                            Gson gson = new Gson();
                            TypeToken<List<WorkHour>> token = new TypeToken<List<WorkHour>>() {};
                            System.out.println("JSON to be SET: " + response.get("workHours").toString());
                            otherUser.setWorkHours( gson.fromJson(response.get("workHours").toString(), token.getType()));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                    if(otherUser.workHours.isEmpty()){
                        Toast.makeText(GenericProfile.this,"This user has no work hours to make an appointment with.",Toast.LENGTH_LONG).show();
                    }else{
                        Intent i = new Intent(GenericProfile.this, ScheduleAppointment.class);
                        i.putExtra("currentUser",currentUser);
                        i.putExtra("otherUser",otherUser);
                        startActivity(i);
                    }
                }
            }
        });

    }
}
