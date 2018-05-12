package ututor.edu.csulb.ututor;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class Favorites extends Fragment implements AdapterView.OnItemSelectedListener{

    public User currentUser;
    public Button edit_fav;
    ArrayAdapter<String> adapter;
    ListView listview;
    boolean editFavorites;

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
        editFavorites = false;

        // initialize list
        names = new ArrayList();
        emails = new ArrayList();

        // TODO: dummy data - delete after!
        names.add("Aenah Ramones");
        emails.add("aenah.ramones@gmail.com");

        names.add("Annah Ramones");
        emails.add("annah.ramones@gmail.com");

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
                while(keys.hasNext()){
                    JSONObject next = (JSONObject) response.get(keys.next());
                    names.add(next.getString("firstName") + " " + next.getString("lastName"));
                    emails.add(next.getString("favoriteeEmail"));
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
        listview.setAdapter(adapter);

        //EDITED Code
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(editFavorites == true){
                    // prompt to delete
                    AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
                    adb.setTitle("Delete");
                    adb.setMessage("Are you sure you want to delete " + names.get(position) + " from your favorites?");
                    final int positionToRemove = position;
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // TODO: database remove favorite relationship by email
                            JSONObject response = null;
                            try {
                                response = new ServerRequester().execute("unFavorite.php", "whatever"
                                        ,"favoritorEmail", currentUser.getEmail()
                                        ,"favoriteeEmail", emails.get(position)
                                ).get();
                                if (response == null) {//Something went horribly wrong, JSON failed to be formed meaning something happened in the server requester

                                } else if (!response.isNull("error")) {//Some incorrect information was sent, but the server and requester still processed it
                                    switch (response.get("error").toString()) {
                                        default:    //Some Error Code was printed from the server that isn't handled above

                                            break;
                                    }
                                } else { //Everything Went Well
                                    names.remove(position);
                                    emails.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }});
                    adb.show();

                }
                else{
                    // TODO: database fetch user by email
                    JSONObject response = null;
                    try {
                        response = new ServerRequester().execute("fetchUser.php", "whatever",
                                "email", emails.get(position)
                        ).get();
                        if (response == null) {//Something went horribly wrong, JSON failed to be formed meaning something happened in the server requester
                        } else if (!response.isNull("error")) {//Some incorrect information was sent, but the server and requester still processed it
                            //TODO Handle Server Errors
                            switch (response.get("error").toString()) {
                                case "-1": //Email Password Combo not in the Database
                                    break;
                                case "-2":  //Select Query failed due to something dumb
                                    // Print out response.get("errormessage"), it'll have the mysql error with it

                                    break;
                                case "-3": //Update Query Failed Due to New Email is already associated with another account
                                    break;
                                case "-4":  //Update Query Failed Due to Something Else Dumb that I haven't handled yet,
                                    // Print out response.get("errormessage"), it'll have the mysql error with it
                                    break;
                                default:    //Some Error Code was printed from the server that isn't handled above

                                    break;
                            }
                        } else { //Everything Went Well
                            User otherUser = new User();
                            otherUser.setFirstName(response.get("firstName").toString());
                            otherUser.setLastName(response.get("lastName").toString());
                            otherUser.setEmail(response.get("email").toString());
                            otherUser.setUniversity(response.get("university").toString());
                            otherUser.setRating(Float.parseFloat(response.get("averageRating").toString()));
                            otherUser.setDescription(response.get("userDescription").toString());
                            otherUser.setNumProfilePic(Integer.parseInt(response.get("profilePic").toString()));
                            otherUser.setPhoneNumber(response.get("phoneNumber").toString());
                            Intent i = new Intent(getActivity(), GenericProfile.class);
                            i.putExtra("currentUser", currentUser);
                            i.putExtra("otherUser", otherUser);
                            getActivity().startActivity(i);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
//                    Toast.makeText(getActivity(), names.get(position), Toast.LENGTH_SHORT).show();
                }
            });

        edit_fav = (Button) rootView.findViewById(R.id.editFav);
        edit_fav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if( editFavorites == false){
                    //set to true
                    edit_fav.setText("SAVE");
                    editFavorites = true;
                }
                else{
                    edit_fav.setText("EDIT");
                    editFavorites = false;
                }

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
