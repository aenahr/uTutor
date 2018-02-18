package ututor.edu.csulb.ututor;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;


public class GenericProfile extends AppCompatActivity {


    TextView user_name;
    TextView biography;
    TextView favorite;
    TextView message;
    TextView readreview;
    ImageButton star_button;
    TextView schedule_appointment;
    TextView rate_user;
    boolean click = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        user_name = (TextView) findViewById(R.id.userName);
        biography = (TextView) findViewById(R.id.Biography);
        star_button = (ImageButton) findViewById(R.id.star);
        message = (TextView) findViewById(R.id.Message);
        readreview = (TextView) findViewById(R.id.ReadReview);
        schedule_appointment = (TextView)findViewById(R.id.rate_user);
        rate_user = (TextView)findViewById(R.id.rate_user);


        user_name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        biography.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        star_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (click) {
                    favorite = (TextView) findViewById(R.id.Favorite);
                    favorite.setTextColor(0xffffff00);
                    click=false;
                }else
                {
                    favorite.setTextColor(212121);
                    click =true;
                }

            }
        });

        readreview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        rate_user.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

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
