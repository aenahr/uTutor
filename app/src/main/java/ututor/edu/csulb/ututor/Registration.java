package ututor.edu.csulb.ututor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by aenah on 12/18/17.
 */

public class Registration extends Fragment {

    Button changeLayout;
    EditText userInput;

    public Registration() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_sign_up, container, false);

        userInput = (EditText) rootView.findViewById(R.id.etEmail);

        changeLayout = (Button) rootView.findViewById(R.id.register);
        changeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get user input
                String value = userInput.getText().toString();

                // notify user of success
                Toast.makeText(getActivity(), "Registration complete! Welcome, " + value + "!", Toast.LENGTH_LONG).show();

                //go to homepage of app
                Intent i = new Intent(getActivity(), HomePage.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0,0);
            }
        });


        return rootView;
    }
}
