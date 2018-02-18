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
    private ArrayList<newItem> DataList;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.search_list, container, false);

        CreateDataList();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager( this.getActivity() );
        adapter = new RecyclerAdapter(DataList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        EditText userinput = rootView.findViewById(R.id.userInputTutor);
        userinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               //this is where filtering happens
                filter(editable.toString());
            }
        });

        return rootView;
    }

    private void filter(String text) {
        ArrayList<newItem> filteredList = new ArrayList<>();

        for (newItem item : DataList) {
            if (item.getText1().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }

    private void CreateDataList() {
        DataList = new ArrayList<>();
        DataList.add(new newItem(R.drawable.search_icon, "Aenah Ramones", "Computer Science"));
        DataList.add(new newItem(R.drawable.search_icon, "Nishant Saxena", "Computer Science"));
        DataList.add(new newItem(R.drawable.search_icon, "Lance McVicar", "Computer Science"));
        DataList.add(new newItem(R.drawable.search_icon, "Henry Tran", "Line 2"));
        DataList.add(new newItem(R.drawable.search_icon, "Shahar Janjua", "Line 2"));
        DataList.add(new newItem(R.drawable.search_icon, "Chris P. Bacon", "Line 2"));
        DataList.add(new newItem(R.drawable.search_icon, "Ella Vader", "Line 2"));
        DataList.add(new newItem(R.drawable.search_icon, "Bend Dover", "Line 2"));
        DataList.add(new newItem(R.drawable.search_icon, "Al Bino", "Line 2"));
    }
}
