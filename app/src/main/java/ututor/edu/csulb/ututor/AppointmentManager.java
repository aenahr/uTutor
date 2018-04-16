package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class AppointmentManager extends Fragment {

    User currentUser;
    Button bPending;
    Button bUpcoming;
    Button bPast;

    ArrayList<Appointment> pendingAppointments;
    ArrayList<Appointment> upcomingAppointments;
    ArrayList<Appointment> pastAppointments;

    public AppointmentManager() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_am, container, false);

        Intent i = getActivity().getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");

        bPending = rootView.findViewById(R.id.pending);
        bUpcoming = rootView.findViewById(R.id.upcoming);
        bPast = rootView.findViewById(R.id.past);

        pendingAppointments = new ArrayList<Appointment>();
        upcomingAppointments = new ArrayList<Appointment>();
        pastAppointments = new ArrayList<Appointment>();

        // TODO - LANCE: DATABASE FETCH! GET ALL APPOINTMENTS ASSOCIATED WITH USER
        // IT WILL BE MY JOB TO SORT THEM :)
        // phony appointments...will delete later
        Calendar past = Calendar.getInstance();
        past.set(Calendar.MONTH, 11);
        past.set(Calendar.DAY_OF_MONTH, 15);
        past.set(Calendar.YEAR, 1995);
        past.set(Calendar.HOUR_OF_DAY, 7);
        past.set(Calendar.MINUTE, 30);
        Calendar pastEnd = Calendar.getInstance();
        pastEnd.set(Calendar.MONTH, 11);
        pastEnd.set(Calendar.DAY_OF_MONTH, 15);
        pastEnd.set(Calendar.YEAR, 1995);
        pastEnd.set(Calendar.HOUR_OF_DAY, 11);
        pastEnd.set(Calendar.MINUTE, 30);

        Appointment one = new Appointment();
        one.setDateOfAppointment(past);
        one.setStartTime(past);
        one.setEndTime(pastEnd);
        one.setAccepted(true);
        one.setTutee("annah.ramones@gmail.com");
        one.setTuteeFName("Annah");
        one.setTuteeLName("Ramones");
        one.setTutorEmail(currentUser.getEmail());

        Calendar up = Calendar.getInstance();
        up.set(Calendar.MONTH, 4);
        up.set(Calendar.DAY_OF_MONTH, 11);
        up.set(Calendar.YEAR, 2018);
        up.set(Calendar.HOUR_OF_DAY, 13);
        up.set(Calendar.MINUTE, 15);
        Calendar upEnd = Calendar.getInstance();
        upEnd.set(Calendar.MONTH, 4);
        upEnd.set(Calendar.DAY_OF_MONTH, 11);
        upEnd.set(Calendar.YEAR, 2018);
        upEnd.set(Calendar.HOUR_OF_DAY, 15);
        upEnd.set(Calendar.MINUTE, 30);
        Appointment two = new Appointment();
        two.setDateOfAppointment(up);
        two.setStartTime(up);
        two.setEndTime(upEnd);
        two.setAccepted(true);
        two.setTutee("grace@gmail.com");
        two.setTuteeFName("Grace");
        two.setTuteeLName("Ji");
        two.setTutorEmail(currentUser.getEmail());

        Calendar pend = Calendar.getInstance();
        pend.set(Calendar.MONTH, 7);
        pend.set(Calendar.DAY_OF_MONTH, 11);
        pend.set(Calendar.YEAR, 2018);
        pend.set(Calendar.HOUR_OF_DAY, 20);
        pend.set(Calendar.MINUTE, 30);
        Calendar pendEnd = Calendar.getInstance();
        pendEnd.set(Calendar.MONTH, 7);
        pendEnd.set(Calendar.DAY_OF_MONTH, 11);
        pendEnd.set(Calendar.YEAR, 2018);
        pendEnd.set(Calendar.HOUR_OF_DAY, 23);
        pendEnd.set(Calendar.MINUTE, 30);
        Appointment three = new Appointment();
        three.setDateOfAppointment(pend);
        three.setStartTime(pend);
        three.setEndTime(pendEnd);
        three.setAccepted(false);
        three.setTutee("maria@gmail.com");
        three.setTuteeFName("Maria");
        three.setTuteeLName("Etcheverry");
        three.setTutorEmail(currentUser.getEmail());


        currentUser.addNewAppointment(one);
        currentUser.addNewAppointment(two);
        currentUser.addNewAppointment(three);

        // delete till here

        // TODO - SORTING!
        Calendar today = Calendar.getInstance();
        for(int x =0; x < currentUser.getAppointments().size(); x++){
            if(currentUser.getAppointments().get(x).isAccepted == true){ //either upcoming or past
                if(currentUser.getAppointments().get(x).getStartTime().compareTo(today) < 0){ //upcoming
                    upcomingAppointments.add(currentUser.getAppointments().get(x));
                }else{
                    pastAppointments.add(currentUser.getAppointments().get(x));
                }
            }
            else{ // pending
                pendingAppointments.add(currentUser.getAppointments().get(x));
            }
        }


        // initialize colors
        changeOption(2);

        // button listeners
        bPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOption(1);
            }
        });

        bUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOption(2);
            }
        });

        bPast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOption(3);
            }
        });



        return rootView;
    }

    /**
     * Changes the color of the text depending on what the user has selected
     * @param choice
     */
    public void changeOption(int choice){
        if( choice == 1){ // pending
            bPending.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            bUpcoming.setTextColor(getResources().getColor(R.color.white));
            bPast.setTextColor(getResources().getColor(R.color.white));
        }else if( choice == 2){ // upcoming
            bPending.setTextColor(getResources().getColor(R.color.white));
            bUpcoming.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            bPast.setTextColor(getResources().getColor(R.color.white));
        } else if( choice == 3){ //past
            bPending.setTextColor(getResources().getColor(R.color.white));
            bUpcoming.setTextColor(getResources().getColor(R.color.white));
            bPast.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

}
