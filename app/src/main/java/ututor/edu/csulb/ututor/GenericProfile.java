package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


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
    boolean click = true;

    User currentUser;
    User otherUser;




    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        user_name = (TextView) findViewById(R.id.userName);
        cbiography = (LinearLayout) findViewById(R.id.gprofile_cardBio);
        cfavorite = (LinearLayout)findViewById(R.id.gprofile_cardFavorite);
        crate = (LinearLayout) findViewById(R.id.gprofile_cardRate);
        cmessage = (LinearLayout) findViewById(R.id.gprofile_cardMessage);
        creadreview = (LinearLayout) findViewById(R.id.gprofile_cardReadReview);
        schedule_appointment = (LinearLayout) findViewById(R.id.gprofile_cardScheduleAppoint);
        user_image = (ImageView)findViewById(R.id.profile_userImage);



        // get user's data
        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        otherUser = (User)i.getSerializableExtra("otherUser"); // TODO temporary keyword it will replace by keyword with Nissant keyword

        user_name.setText(otherUser.getFirstName()+" " + otherUser.getLastName());
        //get other user image
        ProfilePicture p = new ProfilePicture(this);
        p.setColor(otherUser.getuNumProfilePic());
        user_image.setImageBitmap(p.getBitmapColor());



        cbiography.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(GenericProfile.this, GProfile_Bio.class);
                i.putExtra("otherUser", otherUser);
                startActivity(i);

            }
        });

        cfavorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (currentUser.getEmail().equals(otherUser.getEmail()))
                {
                    Toast.makeText(GenericProfile.this, "You cannot favorite yourself",Toast.LENGTH_LONG).show();
                }else
                {
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
            }
        });
        crate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser.getEmail().equals(otherUser.getEmail()))
                {
                    Toast.makeText(GenericProfile.this,"You can not rate yourself",Toast.LENGTH_LONG).show();
                }
                else
                {
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
                startActivity(i);
            }
        });



        schedule_appointment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(GenericProfile.this, ScheduleAppointment.class));
                if (currentUser.getEmail().equals(otherUser.getEmail()))
                {
                    Toast.makeText(GenericProfile.this,"You can not make an appointment yourself",Toast.LENGTH_LONG).show();
                }else
                {
                    Intent i = new Intent(GenericProfile.this,ScheduleAppointment.class);
                    i.putExtra("currentUser",currentUser);
                    i.putExtra("otherUser",otherUser);
                    startActivity(i);
                }
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
