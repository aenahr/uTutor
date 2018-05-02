package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class Searchlist_filter extends AppCompatActivity{

    EditText searchUniversity, searchFname, searchLname;
    Button bRating, bUniversity, bSearch, bclear;
    EditText searchSubject, searchEmail;
    RatingBar rating;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlist_filter);

        Intent i = getIntent();
        currentUser = (User) i.getSerializableExtra("currentUser");


        //Getting subject and university from user in adv search
        searchFname = (EditText) findViewById(R.id.firstname);
        searchLname = (EditText) findViewById(R.id.lastname);
        searchSubject = (EditText) findViewById(R.id.searchsubject);
        searchUniversity = (EditText) findViewById(R.id.searchUni);
        rating = (RatingBar) findViewById(R.id.ratingBar3);

        //place where user types name and the search button that starts the search
        searchEmail = (EditText)findViewById(R.id.searchEmail); //email
        String email = (String)i.getSerializableExtra("searchtext");
        if (email == null){

        }else{
            searchEmail.setText(email);
        }
        bSearch = (Button) findViewById(R.id.search);

        //clear all
        bclear = (Button) findViewById(R.id.clearall);

        //sortby buttons
        bRating = (Button) findViewById(R.id.sRating);
        bUniversity = (Button) findViewById(R.id.sUniversity);

        bUniversity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                Toast.makeText(Searchlist_filter.this,searchUniversity.getText().toString() ,Toast.LENGTH_LONG).show();
            }
        });

        bSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // learn how to transfer data from one activity to another
                // when user clicks search on adv panel, it should go back to the search page and
                // display the results (no need to click search again there)
                // use Intent to load search_list
                //Intent i = ...
                //       .putExtra("university,..");
                Intent i = new Intent(Searchlist_filter.this,HomePage.class);
                Bundle data = new Bundle();
                data.putString("First name",searchFname.getText().toString());
                data.putString("Last name",searchLname.getText().toString());
                data.putString("University",searchUniversity.getText().toString());
                data.putString("email",searchEmail.getText().toString());
                data.putString("Subject",searchSubject.getText().toString());
                data.putFloat("rating",rating.getRating());
                i.putExtras(data);
                i.putExtra("uploadPage", "searchPage");
                i.putExtra("currentUser", currentUser);
                startActivity(i);
                //
                /*Toast.makeText(Searchlist_filter.this,"Searching " +
                        searchEmail.getText().toString() + " with parameter " +
                        searchUniversity.getText().toString() ,Toast.LENGTH_LONG).show();*/

            }
        });

        //clear all buttons clears the text fields and rating to empty
        bclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchUniversity.setText("");
                searchSubject.setText("");
                rating.setRating(0);
            }
        });
    }

    }
