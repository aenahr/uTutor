package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Aenah Ramones with related XML.
 * External sources:
 * Creating Expandable List Views: https://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class FAQ extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    EditText iSearch;
    String empty = "";
    int totalSize;

    public FAQ() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_faq, container, false);

        // get listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

        // search feature
        iSearch = (EditText) rootView.findViewById(R.id.searchInput);

        // preparing list data
        initializeFAQ();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        listAdapter.setFinalValues();


        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                for(int i = 0; i < totalSize; i++){
                    if(listDataHeader.get(i).equals("3. Where can I find more info on uTutor?")){
                        Intent devIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/aenahr/uTutor"));
                        startActivity(devIntent);
                    }
                }
                return false;
            }
        });

        TextView.OnEditorActionListener searchListener = new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {

                    if(v.getText().toString().equals(empty)){
                        listAdapter.refreshValues();


                    }
                    else{
                        boolean qFound = listAdapter.filterData(v.getText().toString());

                        if(qFound == false) {
                            Toast.makeText(getActivity(), v.getText().toString() + " not found.", Toast.LENGTH_SHORT).show();
                        }


                    }

                    return true;
                }
                return false;
            }
        };

        iSearch.setOnEditorActionListener(searchListener);

        iSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iSearch.setText("");
                listAdapter.refreshValues();
            }
        });

        return rootView;
    }

    private void initializeFAQ() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding head data
        listDataHeader.add("1. How can I schedule an appointment with a Tutor?");
        listDataHeader.add("2. How do I report an issue within the app?");
        listDataHeader.add("3. Where can I find more info on uTutor?");
        listDataHeader.add("4. Can I become a tutor?");
        listDataHeader.add("5. Can you search by subject and/or location?");
        listDataHeader.add("6. When was this app created?");
        listDataHeader.add("7. How can I see my list of upcoming appointments?");
        listDataHeader.add("8. How can I favorite a tutor?");
        listDataHeader.add("9. Do you have an iOS version of uTutor?");
        listDataHeader.add("10. Who are the creators of uTutor?");
        listDataHeader.add("11. Can we refer friends to this app?");
        listDataHeader.add("12. Do I need to log in to access uTutor?");


        // Adding child data
        List<String> qOneAns = new ArrayList<String>();
        qOneAns.add("A: Navigate to the Search section of our app and enter your tutor's name and/or subject. Access their profile and you will have the ability to toggle the Schedule an Appointment Button.");

        List<String> qTwoAns = new ArrayList<String>();
        qTwoAns.add("A: Navigate to the About Us section of our app and contact us via email or phone. We will get to you as soon as possible!");

        List<String> qThreeAns = new ArrayList<String>();
        qThreeAns.add("A: Click here to be directed to our GitHub repo, User Manual and Developer Guide.");



        List<String> qFourAns = new ArrayList<String>();
        qFourAns.add("A: Yes! On our application's navigation menu, select Become a Tutor and follow the steps.");

        List<String> qFiveAns = new ArrayList<String>();
        qFiveAns.add("A: Yes, type the keyword in our search bar.");

        List<String> qSixAns = new ArrayList<String>();
        qSixAns.add("A: This application was created on December 8, 2017.");

        List<String> qSevenAns = new ArrayList<String>();
        qSevenAns.add("A: Navigate to the Appointment Manager of our app and you can see a list of past and upcoming appointments.");

        List<String> qEightAns = new ArrayList<String>();
        qEightAns.add("A: Navigate to a user's profile page and select Favorite. Find a list of favorite tutors in our Favorite Tutors section of our app.");

        List<String> qNineAns = new ArrayList<String>();
        qNineAns.add("A: As of now, we currently do not support iOS devices. Please check back in a few months for updates.");

        List<String> qTenAns = new ArrayList<String>();
        qTenAns.add("A: Henry, Nishant, Lance and Aenah!");

        List<String> qElevenAns = new ArrayList<String>();
        qElevenAns.add("A: We currently do not support this feature but check back in a couple of months!");

        List<String> qTwelveAns = new ArrayList<String>();
        qTwelveAns.add("A: Yes, you must create an account before accessing uTutor.");


        listDataChild.put(listDataHeader.get(0), qOneAns);
        listDataChild.put(listDataHeader.get(1), qTwoAns);
        listDataChild.put(listDataHeader.get(2), qThreeAns);
        listDataChild.put(listDataHeader.get(3), qFourAns);
        listDataChild.put(listDataHeader.get(4), qFiveAns);
        listDataChild.put(listDataHeader.get(5), qSixAns);
        listDataChild.put(listDataHeader.get(6), qSevenAns);
        listDataChild.put(listDataHeader.get(7), qEightAns);
        listDataChild.put(listDataHeader.get(8), qNineAns);
        listDataChild.put(listDataHeader.get(9), qTenAns);
        listDataChild.put(listDataHeader.get(10), qElevenAns);
        listDataChild.put(listDataHeader.get(11), qTwelveAns);

        totalSize = listDataHeader.size();
    }

}
