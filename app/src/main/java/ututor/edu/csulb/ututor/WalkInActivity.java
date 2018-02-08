package ututor.edu.csulb.ututor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;


public class WalkInActivity extends AppCompatActivity {

    Button imgBack;
    Button apply;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_tutor);
    }
}