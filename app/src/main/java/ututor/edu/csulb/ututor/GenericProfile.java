package ututor.edu.csulb.ututor;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;


public class GenericProfile extends AppCompatActivity {


    TextView user_name;
    TextView biography;
    TextView favorite;
    TextView message;
    TextView readreview;
    RatingBar ratebar;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        user_name = (TextView) findViewById(R.id.userName);
        biography = (TextView) findViewById(R.id.Biography);
        favorite = (TextView) findViewById(R.id.Favorite);
        message = (TextView) findViewById(R.id.Message);
        readreview = (TextView) findViewById(R.id.ReadReview);


        user_name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        biography.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        readreview.setOnClickListener(new View.OnClickListener() {
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
