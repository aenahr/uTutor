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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class WorkManager extends Fragment implements AdapterView.OnItemSelectedListener {

    User currentUser;
    TextView mTitle;
    final String subjects[] = {"Art", "Biology", "Computer Science", "Computer Engineering",
        "Biomedical Engineering", "Civil Engineering", "Aerospace Engineering", "English", "Japanese","Chinese", "German",
        "Arabic", "Kinesiology", "Math", "Asian Studies", "Business Administration", "Astronomy", "Art History", "Business Law",
        "Classics", "Chemistry", "Biochemistry", "Communication Studies", "Criminology", "Design", "Dance", "Economics",
        "Engineering Technology", "Filipino", "Food Science", "Fashion", "Geography", "Geology", "Greek", "Health & Human Services",
        "Hebrew", "History", "Health Science", "Hosptality Management", "Human Development", "Information Systems",
        "Italian", "Journalism", "Korean", "Latin", "Liberal Arts", "Management", "Marketing", "Mechanical Engineering",
        "Military Science", "Music", "Natural Sciences", "Nursing", "Philosophy", "Physical Science", "Physical Therapy"};
    Spinner mSpinner;

    /////
    // List View variables for Work Hours
    /////
    ListView mWorkListView;
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> mWorkItems;
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> mWorkAdapter;
    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    ArrayList<WorkHour> tempWorkItems;


    /////
    // List View variables for subjects
    /////
    ListView mSubjectListView;
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> mSubjectItems;
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> mSubjectAdapter;
    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    Button addSubjectItem;
    Button addWorkHour;


    // TODO find out when to refresh database to update Work Times


    public WorkManager() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_work_manager, container, false);

        Intent i = getActivity().getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        //Toast.makeText(getActivity(), currentUser.getFirstName(), Toast.LENGTH_SHORT).show();

        // work hours from current User
        tempWorkItems = currentUser.getWorkHours();
        mWorkItems = new ArrayList<String>();

        if(tempWorkItems.isEmpty()){
        }
        else{
            for(int k = 0; k < tempWorkItems.size(); k++){ // add all the work hours to array that will be inserted into adapter
                mWorkItems.add(tempWorkItems.get(k).toString());
            }
        }
        // initialize subjects objects
        mSubjectListView = (ListView) rootView.findViewById(R.id.wList);
        mSpinner = (Spinner) rootView.findViewById(R.id.wSpinner);
        mTitle = (TextView) rootView.findViewById(R.id.titleWork);
        addSubjectItem = (Button) rootView.findViewById(R.id.addBtn);


        // initialize work objects
        addWorkHour = (Button) rootView.findViewById(R.id.wAddWorkHour);
        mWorkListView = (ListView) rootView.findViewById(R.id.wWorkList);
        mWorkAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, mWorkItems);
        mWorkListView.setAdapter(mWorkAdapter);

        // link local arraylist to currentUser's arraylist
        mSubjectItems = currentUser.getSubjectsTaught();

        // sort subjects array
        Arrays.sort(subjects);


//      String uniqueTitle = currentUser.getFirstName().toUpperCase() + "'s WORK MANAGER";
//      mTitle.setText(uniqueTitle);

        //spinner for subjects taught
        mSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, subjects);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mSpinner.setAdapter(dataAdapter);

        // add list view
        mSubjectAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, mSubjectItems);
        mSubjectListView.setAdapter(mSubjectAdapter);

        addSubjectItem.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                String inputSubject = mSpinner.getSelectedItem().toString();

                if( contains(mSubjectItems, inputSubject) ){ //it is already located in the ArrayList
                    Toast.makeText(getActivity(), inputSubject + " already added to subjects.", Toast.LENGTH_SHORT).show();
                }
                else{ // not located, add to subjects list
                    Toast.makeText(getActivity(), inputSubject + " added to subjects!", Toast.LENGTH_SHORT).show();
                    mSubjectItems.add(inputSubject);
                    mSubjectAdapter.notifyDataSetChanged();
                }
            }
        });

        mSubjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                // remove from list

                Toast.makeText(getActivity(), mSubjectItems.get(position) + " deleted.", Toast.LENGTH_SHORT).show();
                mSubjectItems.remove(position);
                mSubjectAdapter.notifyDataSetChanged();
            }
        });

        mWorkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                // remove from list
                mWorkItems.remove(position);
                tempWorkItems.remove(position);
                mWorkAdapter.notifyDataSetChanged();

                Toast.makeText(getActivity(), "Work Hour successfully deleted.", Toast.LENGTH_SHORT).show();
            }
        });

        ///////////
        // Work Hours
        ///////////

        addWorkHour.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

//                Intent intent = new Intent(getActivity(), WorkHourPicker.class);
//                startActivity(intent);

                Intent i = new Intent(getActivity(), WorkHourPicker.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);
            }
        });


        return rootView;
    }

    public static <T> boolean contains(final ArrayList<T> array, final T v) {
        for (final T e : array)
            if (e == v || v != null && v.equals(e))
                return true;

        return false;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
