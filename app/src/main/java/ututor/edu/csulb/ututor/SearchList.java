package ututor.edu.csulb.ututor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchList extends Fragment {

    public SearchList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.search_list, container, false);

        ListView listview =(ListView) rootView .findViewById(R.id.listViewTutor);

        //EDITED Code
        String[] items = new String[] {"Aenah Ramones", "Nishant Saxena", "Lance McVicar", "Henry Tran", "Shahar Janjua", "Chris P. Bacon", "Al Bino", "Bill Board", "Brock Lee", "Crystal Ball", "Ella Vader", "Donald Duck", "Filet Minyon", "Bend Dover", "Elmo's World"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        listview.setAdapter(adapter);

        return rootView;
    }

}
