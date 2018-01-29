package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Registration extends AppCompatActivity {

    Button changeLayout;
    EditText userInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        changeLayout = (Button) findViewById(R.id.register);
        changeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get user input
                String value = userInput.getText().toString();

                // notify user of success
                Toast.makeText(Registration.this, "Registration complete! Welcome, " + value + "!", Toast.LENGTH_LONG).show();

                //go to homepage of app
                Intent i = new Intent(Registration.this, HomePage.class);
                startActivity(i);
                finish();
            }
        });
    }



}
