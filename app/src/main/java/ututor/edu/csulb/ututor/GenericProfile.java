package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;


public class GenericProfile extends AppCompatActivity {


    TextView user_name;
    TextView favorite;
    LinearLayout cbiography;
    LinearLayout cfavorite;
    LinearLayout cmessage;
    LinearLayout creadreview;
    LinearLayout crate;
    LinearLayout schedule_appointment;
    boolean click = true;




    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        user_name = (TextView) findViewById(R.id.userName);
        cbiography = (LinearLayout) findViewById(R.id.gprofile_cardBio);
        crate = (LinearLayout) findViewById(R.id.gprofile_cardRate);
        cmessage = (LinearLayout) findViewById(R.id.gprofile_cardMessage);
        creadreview = (LinearLayout) findViewById(R.id.gprofile_cardReadReview);
        schedule_appointment = (LinearLayout) findViewById(R.id.gprofile_cardScheduleAppoint);



        user_name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        cbiography.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(GenericProfile.this, Profile_Bio.class);

            }
        });

        crate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (click) {
                    favorite = (TextView) findViewById(R.id.Favorite);
                    favorite.setTextColor(Color.parseColor("#FFC107"));
                    click=false;
                }else
                {
                    favorite.setTextColor(Color.parseColor("#CFD8DC"));
                    click =true;
                }

            }
        });

        creadreview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(GenericProfile.this, Profile_readreview.class);

            }
        });



        schedule_appointment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });




    }
/*
    public void addListenerOnRatingBar() {

        ratebar = (RatingBar) findViewById(R.id.ratingBar2);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratebar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {


            }
        });
    }
    */
}
