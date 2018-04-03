package ututor.edu.csulb.ututor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

public class SearchList extends Fragment {

    public SearchList() {
    }
    private ArrayList<NewItem> DataList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.search_list, container, false);

        CreateDataList();  // data


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager( this.getActivity() );

        ArrayList<NewItem> filteredList = new ArrayList<>();
        adapter = new RecyclerAdapter(filteredList);  //initialise adapter with empty array

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        EditText userinput = rootView.findViewById(R.id.userInputTutor);  //gets name from user
        EditText userinputsubject = rootView.findViewById(R.id.searchsubject);

        //gets subject from user

        userinput.addTextChangedListener(new TextWatcher() {  //does the stuff with the user input
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                // Lance
               // this is where filtering happens
                filter(editable.toString());
            }
        });

        return rootView;
    }

    /** This method filters entries */
    private void filter(String text) {
        if(text != null && !text.isEmpty()) {
            ArrayList<NewItem> filteredList = new ArrayList<>();

//            String[] name = text.split(" ");
//            String firstname = name[0];
//            String lastname = name[1];
//
//            for (NewItem item : DataList) {
//                if (item.getfirstname().toLowerCase().contains(firstname.toLowerCase()) && item.getlastname().toLowerCase().contains(lastname.toLowerCase())) {
//                    filteredList.add(item);
//                }
//            }
            adapter.filterList(filteredList);
        } else {
            ArrayList<NewItem> filteredList = new ArrayList<>();
            adapter.filterList(filteredList);
        }
    }

    private void CreateDataList() {
        DataList = new ArrayList<>();
        DataList.add(new NewItem(R.drawable.search_icon, "Aenah Ramones", "Computer Science"));
        DataList.add(new NewItem(R.drawable.search_icon, "Nishant Saxena", "Computer Science"));
        DataList.add(new NewItem(R.drawable.search_icon, "Shikha Saxena", "Speech Pathology"));
        DataList.add(new NewItem(R.drawable.search_icon, "Lance McVicar", "Computer Science"));
        DataList.add(new NewItem(R.drawable.search_icon, "Henry Tran", "Line 2"));
        DataList.add(new NewItem(R.drawable.search_icon, "Shahar Janjua", "Line 2"));
        DataList.add(new NewItem(R.drawable.bacon, "Chris P. Bacon", "Line 2"));
        DataList.add(new NewItem(R.drawable.search_icon, "Ella Vader", "Line 2"));
        DataList.add(new NewItem(R.drawable.search_icon, "Bend Dover", "Line 2"));
        DataList.add(new NewItem(R.drawable.search_icon, "Al Bino", "Line 2"));
    }
}
