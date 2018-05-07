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

    private EditText searchUniversity, searchFname, searchLname;
    private Button  bSearch, bclear;
    private EditText searchSubject, searchEmail;
    private RatingBar rating;
    private User currentUser;

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

        if ((String)i.getSerializableExtra("searchtext") == null){}
        else{ searchEmail.setText((String)i.getSerializableExtra("searchtext")); }
        bSearch = (Button) findViewById(R.id.search);

        //clear all
        bclear = (Button) findViewById(R.id.clearall);


        bSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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
            }
        });

        //clear all buttons clears the text fields and rating to empty
        bclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFname.setText("");
                searchLname.setText("");
                searchUniversity.setText("");
                searchSubject.setText("");
                rating.setRating(0);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("rating", rating.getRating());
        outState.putString("searchFname", searchFname.getText().toString());
        outState.putString("searchLname", searchLname.getText().toString());
        outState.putString("searchSubject", searchSubject.getText().toString());
        outState.putString("searchUniversity", searchUniversity.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        rating.setRating(savedInstanceState.getFloat("rating"));
        searchFname.setText(savedInstanceState.getString("searchFname"));
        searchLname.setText(savedInstanceState.getString("searchLname"));
        searchSubject.setText(savedInstanceState.getString("searchSubject"));
        searchUniversity.setText(savedInstanceState.getString("searchUniversity"));
    }

    }
