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
    ImageView starImage;

    User currentUser;
    User otherUser;

    boolean isFavorite;




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


        // TODO database: find if favorite relationship
        // dummy variable
        isFavorite = false;

        // get user's data
        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        otherUser = (User)i.getSerializableExtra("otherUser");


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
                        //TODO database : create favorite relationship
                        starImage.setBackgroundResource(R.drawable.ic_star_colored);
                        favorite.setText("Unfavorite User");
                        isFavorite = true;
                    }else{
                        // TODO database : delete favorite relationship
                        starImage.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                        favorite.setText("Favorite User");
                        isFavorite = false;
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
                    Intent i = new Intent(GenericProfile.this, ScheduleAppointment.class);
                    i.putExtra("currentUser",currentUser);
                    i.putExtra("otherUser",otherUser);
                    startActivity(i);
                }
            }
        });

    }
}
