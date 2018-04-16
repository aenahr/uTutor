package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Henry Tran on 2/12/2018.
 */

public class Gprofile_rateTutor extends AppCompatActivity {

    User currentUser;
    User otherUser;
    EditText feed;
    String feedback;
    Button submit;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gprofile_ratetutor);

        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        otherUser = (User)i.getSerializableExtra("otherUser"); // TODO temporary keyword it will replace by keyword with Nissant keyword

        feed = (EditText) findViewById(R.id.text_feedback);
        submit =(Button)findViewById(R.id.submit_feedback);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO Lance send rating and review feedback
                Intent i = new Intent(Gprofile_rateTutor.this,GenericProfile.class);
                i.putExtra("currentUser",currentUser);
                i.putExtra("otherUser",otherUser);
                startActivity(i);
            }
        });
    }

}
