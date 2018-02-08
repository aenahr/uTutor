package ututor.edu.csulb.ututor;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class WorkManager extends Fragment {

    User currentUser;


    public WorkManager() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_am, container, false);

        Intent i = getActivity().getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");

        String title = currentUser.getFirstName() + "'s Work Manager";


        //Toast.makeText(getActivity(), currentUser.getFirstName(), Toast.LENGTH_SHORT).show();






        return rootView;
    }


}
