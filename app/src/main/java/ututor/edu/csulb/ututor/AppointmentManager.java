package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class AppointmentManager extends Fragment {

    User currentUser;
    Button bPending;
    Button bUpcoming;
    Button bPast;

    ArrayList<Appointment> pendingAppointments;
    ArrayList<Appointment> upcomingAppointments;
    ArrayList<Appointment> pastAppointments;

    private ListView appointmentListView;
    private ArrayList<HashMap<String,String>> appointmentItems;

    private ArrayAdapter<String> upcomingAdapter;
    private ArrayAdapter<String> pendingAdapter;
    private ArrayAdapter<String> pastAdapter;

    View root;


    public AppointmentManager() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_am, container, false);

        root = rootView;

        Intent i = getActivity().getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");

        currentUser.addNewHour(new WorkHour());
        currentUser.addNewHour(new WorkHour());
        currentUser.addNewHour(new WorkHour());

        bPending = rootView.findViewById(R.id.pending);
        bUpcoming = rootView.findViewById(R.id.upcoming);
        bPast = rootView.findViewById(R.id.past);
        appointmentListView = (ListView) rootView.findViewById(R.id.aList);


        pendingAppointments = new ArrayList<Appointment>();
        upcomingAppointments = new ArrayList<Appointment>();
        pastAppointments = new ArrayList<Appointment>();

        JSONObject response = null;
        try {
            response = new ServerRequester().execute("getAppointments.php", "whatever"
                    ,"email", currentUser.getEmail()
            ).get();
            if (response == null) {//Something went horribly wrong, JSON failed to be formed meaning something happened in the server requester

            } else if (!response.isNull("error")) {//Some incorrect information was sent, but the server and requester still processed it
                //TODO Handle Server Errors
                switch (response.get("error").toString()) {
                    default:    //Some Error Code was printed from the server that isn't handled above

                        break;
                }
            } else {//Everything went like cream gravy (good)
                Iterator<String> keys = response.keys(); //Iterator to go through each appointment returned
                while(keys.hasNext()){ //For each key in the entire response
                    JSONObject next = (JSONObject) response.get(keys.next()); //next now has a single appointment
                    Appointment newAppointment = new Appointment();

                    String fullString = next.getString("startAppDateTime"); //Gets the Start Time and Date in format "YYYY-MM-DD HH:MM:SS"
                    String[] datetime = fullString.split(" ");
                    String[] actualDate = datetime[0].split("-");
                    String[] actualTime = datetime[1].split(":");
                    Calendar startTime = Calendar.getInstance();
                    startTime.set(Calendar.YEAR, Integer.parseInt(actualDate[0]));
                    startTime.set(Calendar.MONTH, Integer.parseInt(actualDate[1])-1); // it's a 0-11 month
                    startTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(actualDate[2]));
                    startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(actualTime[0]));
                    startTime.set(Calendar.MINUTE, Integer.parseInt(actualTime[1]));
                    startTime.set(Calendar.SECOND, 0);
                    fullString = next.getString("endAppDateTime");
                    datetime = fullString.split(" ");
                    actualDate = datetime[0].split("-");
                    actualTime = datetime[1].split(":");
                    Calendar endTime = Calendar.getInstance();
                    endTime.set(Calendar.YEAR, Integer.parseInt(actualDate[0]));
                    endTime.set(Calendar.MONTH, Integer.parseInt(actualDate[1])-1);// it's a 0-11 month
                    endTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(actualDate[2]));
                    endTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(actualTime[0]));
                    endTime.set(Calendar.MINUTE, Integer.parseInt(actualTime[1]));
                    endTime.set(Calendar.SECOND, 0);
//                    Toast.makeText(getActivity(), "Adding: " + startTime.get(Calendar.MONTH) + "/" + startTime.get(Calendar.DAY_OF_MONTH) + "/" + startTime.get(Calendar.YEAR), Toast.LENGTH_SHORT).show();


                    // set Calendar classes
                    newAppointment.setStartTime(startTime);
                    newAppointment.setDateOfAppointment(startTime);
                    newAppointment.setEndTime(endTime);

                    // set if accepted or not by tutor
                    if(Integer.parseInt(next.getString("is_accepted")) == 0){ newAppointment.setAccepted(0); }
                    else if(Integer.parseInt(next.getString("is_accepted")) == -1){newAppointment.setAccepted(-1); }
                    else{ newAppointment.setAccepted(1); }

                    newAppointment.setTutorEmail(next.getString("tutorEmail"));
                    newAppointment.setTuteeEmail(next.getString("tuteeEmail"));

                    // Setting the first and last names of the tutor and tutee
                    newAppointment.setTutorFName(next.getString("tutorFirstName"));
                    newAppointment.setTutorLName(next.getString("tutorLastName"));

                    newAppointment.setTuteeFName(next.getString("tuteeFirstName"));
                    newAppointment.setTuteeLName(next.getString("tuteeLastName"));


                    // add apointment to the user class
                    currentUser.addNewAppointment(newAppointment);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // SORTING ALL THE APPOINTMENTS
        Calendar today = Calendar.getInstance();
        for(int x =0; x < currentUser.getAppointments().size(); x++){
            if(currentUser.getAppointments().get(x).isAccepted == 0){ //either upcoming or past
                if(currentUser.getAppointments().get(x).getStartTime().compareTo(today) > 0){ //upcoming
                    upcomingAppointments.add(currentUser.getAppointments().get(x));
                }else{ // past
                    pastAppointments.add(currentUser.getAppointments().get(x));
                }
            }
            else{ // pending
                if(currentUser.getAppointments().get(x).getStartTime().compareTo(today) > 0){
                    pendingAppointments.add(currentUser.getAppointments().get(x));
                }
                // else start date has passed and we can assume the the tutor has not accepted the appointment request
            }
        }

        // starting with upcoming
        changeUpcomingListView(rootView);
        // initialize colors
        changeButtonOption(2);

        // button listeners
        bPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changePendingListView(root);
                changeButtonOption(1);
            }
        });

        bUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUpcomingListView(root);
                changeButtonOption(2);
            }
        });

        bPast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonOption(3);
                changePastListView(root);
            }
        });

        return rootView;
    }

    /**
     * Changes the color of the text depending on what the user has selected
     * @param choice
     */
    public void changeButtonOption(int choice){
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

    /**
     * Changing the List View to show Upcoming Appointments
     * @param rootView
     */
    public void changeUpcomingListView(View rootView){
        appointmentItems = new ArrayList<HashMap<String, String>>();
        for(int i =0;i<upcomingAppointments.size();i++)
        {
            HashMap<String,String> mHashMap = new HashMap<>();
            if(currentUser.getEmail().equals(upcomingAppointments.get(i).getTuteeEmail())){
                mHashMap.put("typeTitleKey", "Tutored By:");
                mHashMap.put("nameKey",upcomingAppointments.get(i).getTutorFName() + " " + upcomingAppointments.get(i).getTutorLName());
                mHashMap.put("backgroundKey", "normal");
                mHashMap.put("isTutoring", "F");
            }else{
                mHashMap.put("typeTitleKey", "Tutoring:");
                mHashMap.put("nameKey",upcomingAppointments.get(i).getTuteeFName() + " " + upcomingAppointments.get(i).getTuteeLName());
                mHashMap.put("backgroundKey", "yellow");
                mHashMap.put("isTutoring", "T");
            }
            String date = String.format("%2s-%2s-%2s", upcomingAppointments.get(i).getDate().get(Calendar.MONTH)+1, upcomingAppointments.get(i).getDate().get(Calendar.DAY_OF_MONTH), upcomingAppointments.get(i).getDate().get(Calendar.YEAR)).replace(' ', '0');
            String time = String.format("%2s:%2s-%2s:%2s", upcomingAppointments.get(i).getStartTime().get(Calendar.HOUR_OF_DAY), upcomingAppointments.get(i).getStartTime().get(Calendar.MINUTE), upcomingAppointments.get(i).getEndTime().get(Calendar.HOUR_OF_DAY), upcomingAppointments.get(i).getEndTime().get(Calendar.MINUTE)).replace(' ', '0');
            mHashMap.put("dateKey",date);
            mHashMap.put("timeKey",time);

            appointmentItems.add(mHashMap);
        }

        AppointmentListView customListView = new AppointmentListView(appointmentItems,getActivity());
        appointmentListView.setAdapter(customListView);
        appointmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent i = new Intent(getActivity(), AppointmentView.class);
                i.putExtra("currentUser", currentUser);
                i.putExtra("appointmentPast", false);
                if(appointmentItems.get(position).get("isTutoring").equals("F")){ i.putExtra("isTutoring", false); }
                else{ i.putExtra("isTutoring", true); }
                i.putExtra("appointmentInfo", upcomingAppointments.get(position));
                startActivity(i);
            }
        });
    }

    /**
     * Changing the List View to show Pending Appointments
     * @param rootView
     */
    public void changePendingListView(View rootView){
        appointmentItems = new ArrayList<HashMap<String, String>>();
        for(int i =0;i<pendingAppointments.size();i++)
        {
            HashMap<String,String> mHashMap = new HashMap<>();
            if(currentUser.getEmail().equals(pendingAppointments.get(i).getTuteeEmail())){
                mHashMap.put("typeTitleKey", "Tutored By:");
                mHashMap.put("nameKey",pendingAppointments.get(i).getTutorFName() + " " + pendingAppointments.get(i).getTutorLName());
                mHashMap.put("backgroundKey", "normal");
                mHashMap.put("isTutoring", "F");
            }else{
                mHashMap.put("typeTitleKey", "Tutoring:");
                mHashMap.put("nameKey",pendingAppointments.get(i).getTuteeFName() + " " + pendingAppointments.get(i).getTuteeLName());
                mHashMap.put("backgroundKey", "yellow");
                mHashMap.put("isTutoring", "T");

            }
            String date = String.format("%2s-%2s-%2s", pendingAppointments.get(i).getDate().get(Calendar.MONTH)+1, pendingAppointments.get(i).getDate().get(Calendar.DAY_OF_MONTH), pendingAppointments.get(i).getDate().get(Calendar.YEAR)).replace(' ', '0');
            String time = String.format("%2s:%2s-%2s:%2s", pendingAppointments.get(i).getStartTime().get(Calendar.HOUR_OF_DAY), pendingAppointments.get(i).getStartTime().get(Calendar.MINUTE), pendingAppointments.get(i).getEndTime().get(Calendar.HOUR_OF_DAY), pendingAppointments.get(i).getEndTime().get(Calendar.MINUTE)).replace(' ', '0');
            mHashMap.put("dateKey",date);
            mHashMap.put("timeKey",time);

            appointmentItems.add(mHashMap);
        }

        AppointmentListView customListView = new AppointmentListView(appointmentItems,getActivity());
        appointmentListView.setAdapter(customListView);
        appointmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent i = new Intent(getActivity(), AppointmentView.class);
                i.putExtra("currentUser", currentUser);
                i.putExtra("appointmentPast", false);
                if(appointmentItems.get(position).get("isTutoring").equals("F")){ i.putExtra("isTutoring", false); }
                else{ i.putExtra("isTutoring", true); }
                i.putExtra("appointmentInfo", pendingAppointments.get(position));
                startActivity(i);
            }
        });
    }

    /**
     * Changing the List View to show Past Appointments
     * @param rootView
     */
    public void changePastListView(View rootView){
        appointmentItems = new ArrayList<HashMap<String, String>>();
        for(int i =0;i<pastAppointments.size();i++)
        {
            HashMap<String,String> mHashMap = new HashMap<>();
            if(currentUser.getEmail().equals(pastAppointments.get(i).getTuteeEmail())){
                mHashMap.put("typeTitleKey", "Tutored By:");
                mHashMap.put("nameKey",pastAppointments.get(i).getTutorFName() + " " + pastAppointments.get(i).getTutorLName());
                mHashMap.put("backgroundKey", "normal");
                mHashMap.put("isTutoring", "F");
            }else{
                mHashMap.put("typeTitleKey", "Tutoring:");
                mHashMap.put("nameKey",pastAppointments.get(i).getTuteeFName() + " " + pastAppointments.get(i).getTuteeLName());
                mHashMap.put("backgroundKey", "yellow");
                mHashMap.put("isTutoring", "T");
            }
            String date = String.format("%2s-%2s-%2s", pastAppointments.get(i).getDate().get(Calendar.MONTH)+1, pastAppointments.get(i).getDate().get(Calendar.DAY_OF_MONTH), pastAppointments.get(i).getDate().get(Calendar.YEAR)).replace(' ', '0');
            String time = String.format("%2s:%2s-%2s:%2s", pastAppointments.get(i).getStartTime().get(Calendar.HOUR_OF_DAY), pastAppointments.get(i).getStartTime().get(Calendar.MINUTE), pastAppointments.get(i).getEndTime().get(Calendar.HOUR_OF_DAY), pastAppointments.get(i).getEndTime().get(Calendar.MINUTE)).replace(' ', '0');
            mHashMap.put("dateKey",date);
            mHashMap.put("timeKey",time);
            appointmentItems.add(mHashMap);
        }

        AppointmentListView customListView = new AppointmentListView(appointmentItems,getActivity());
        appointmentListView.setAdapter(customListView);
        appointmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent i = new Intent(getActivity(), AppointmentView.class);
                i.putExtra("currentUser", currentUser);
                i.putExtra("appointmentPast", true);
                if(appointmentItems.get(position).get("isTutoring").equals("F")){ i.putExtra("isTutoring", false); }
                else{ i.putExtra("isTutoring", true); }
                i.putExtra("appointmentInfo", pastAppointments.get(position));
                startActivity(i);
            }
        });
    }

}
