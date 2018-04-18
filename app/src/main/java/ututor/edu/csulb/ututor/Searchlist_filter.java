package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Searchlist_filter extends AppCompatActivity{

    EditText searchUniversity;
    Button bUniversity, bSearch;
    EditText userInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlist_filter);

        searchUniversity = (EditText) findViewById(R.id.searchUni);
        bUniversity = (Button) findViewById(R.id.sUniversity);
        userInput = (EditText)findViewById(R.id.userInputTutor);
        bSearch = (Button) findViewById(R.id.search);

        bUniversity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                Toast.makeText(Searchlist_filter.this,searchUniversity.getText().toString() ,Toast.LENGTH_LONG).show();
            }
        });

        bSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(Searchlist_filter.this,"Searching " + userInput.getText().toString() + " with parameter " + searchUniversity.getText().toString() ,Toast.LENGTH_LONG).show();
            }
        });

    }

    }
