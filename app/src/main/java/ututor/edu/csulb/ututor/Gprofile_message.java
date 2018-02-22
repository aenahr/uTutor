package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Henry Tran on 2/12/2018.
 */

public class Gprofile_message extends AppCompatActivity {
    TextView to;
    TextView from;
    EditText msg;
    Button send_msg;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gprofile_message);

        to =(TextView)findViewById(R.id.To_ms);
        from =(TextView)findViewById(R.id.From_ms);
        msg =(EditText)findViewById(R.id.msg_txt);
        send_msg=(Button)findViewById(R.id.SendMsg);

        to.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        from.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                msg.getText().toString();

            }
        });

        send_msg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Gprofile_message.this, HomePage.class);
                startActivity(i);
                finish();
            }
        });
    }
}

