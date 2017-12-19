package ututor.edu.csulb.ututor;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class GeneralAppointment extends Fragment{

    Button changeLayout;
    EditText emailInput;

    public GeneralAppointment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.appointment_creation, container, false);

        emailInput = (EditText) rootView.findViewById(R.id.userInputTutorEmail);

        changeLayout = (Button) rootView.findViewById(R.id.setAppointmentButton);
        changeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value = emailInput.getText().toString();

                Toast.makeText(getActivity(), "Appointment Scheduled with " + value + ".", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), HomePage.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0,0);
            }
        });
        return rootView;
    }



}
