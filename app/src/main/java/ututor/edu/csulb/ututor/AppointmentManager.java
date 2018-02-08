package ututor.edu.csulb.ututor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.List;


public class AppointmentManager extends Fragment {


    public AppointmentManager() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_am, container, false);
        return rootView;
    }


}
