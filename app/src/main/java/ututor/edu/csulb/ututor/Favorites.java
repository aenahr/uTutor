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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Favorites extends Fragment implements AdapterView.OnItemSelectedListener{

    public User currentUser;
    public Button add_fav;
    public Button edit_fav;
    ArrayAdapter<String> adapter;
    ListView listview;

    ArrayList<String> names;
    ArrayList<String> emails;



    public Favorites() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        listview =(ListView) rootView .findViewById(R.id.listViewFavorite);
        Intent i = getActivity().getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");

        JSONObject response = null;
        try {
            response = new ServerRequester().execute("fetchFavorites.php", "whatever",
                    "email", currentUser.getEmail()
            ).get();
            if (response == null) {//Something went horribly wrong, JSON failed to be formed meaning something happened in the server requester

            } else if (!response.isNull("error")) {//Some incorrect information was sent, but the server and requester still processed it
                //TODO Handle Server Errors
                switch (response.get("error").toString()) {
                    default:    //Some Error Code was printed from the server that isn't handled above

                        break;
                }
            } else { //Everything Went Well
                Iterator<String> keys = response.keys();
                names = new ArrayList();
                emails = new ArrayList();
                while(keys.hasNext()){
                    JSONObject next = (JSONObject) response.get(keys.next());
                    names.add(next.getString("firstName") + " " + next.getString("lastName"));
                    emails.add(next.getString("favoriteeEmail"));
                }
                adapter =
                        new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
                listview.setAdapter(adapter);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        //EDITED Code
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Selected " + names.get(position) , Toast.LENGTH_SHORT).show();
            }
        });


        add_fav = (Button)rootView.findViewById(R.id.addFav);
        add_fav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        edit_fav = (Button) rootView.findViewById(R.id.editFav);
        edit_fav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        return rootView;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
