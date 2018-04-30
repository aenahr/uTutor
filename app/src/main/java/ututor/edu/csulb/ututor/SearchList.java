package ututor.edu.csulb.ututor;


import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class SearchList extends Fragment {

    public SearchList() {
    }

    private ArrayList<NewItem> DataList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;

    private Button search;
    private TextView searchText;

    private User currentUser;
    private String searchEmail, Email, university, subject, firstName, lastName;
    private Float rating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.search_list, container, false);

        search = (Button) rootView.findViewById(R.id.search);
        searchText = (TextView) rootView.findViewById(R.id.searchEmail);

        // get user information
        Intent i = getActivity().getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");

        Button advsearch = (Button) rootView.findViewById(R.id.advsearch);
        advsearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), Searchlist_filter.class);
                Bundle data = new Bundle();
                data.putString("searchtext",searchText.getText().toString());
                startActivity(a);
            }
        });

        /*CreateDataList();  // data*/

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this.getActivity());

        ArrayList<NewItem> filteredList = new ArrayList<>();
        adapter = new RecyclerAdapter(filteredList, currentUser, getActivity());  //initialise adapter with empty array

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

//        //objects from adv search
        firstName = (String) i.getSerializableExtra("First name");
        lastName = (String) i.getSerializableExtra("Last name");
        subject = (String) i.getSerializableExtra("Subject");
        university = (String) i.getSerializableExtra("University");
        rating = (Float) i.getSerializableExtra("rating");

        Email = (String) i.getSerializableExtra("email");

        searchEmail = searchText.getText().toString();

        if(firstName == null ) {
            firstName = "";
            lastName = "";
            Email = "";
            subject = "";
            university = "";
            rating = 0f;
        } else if(firstName!= null) {
            filter();
        }

        searchText.addTextChangedListener(new TextWatcher() {  //does the stuff with the user input
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
                search.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        filter();

                    }
                });

            }
        });

        return rootView;
    }

    public TextView searchname;

    /**
     * This method filters entries
     */
    private void filter() {
        ArrayList<NewItem> filteredList = new ArrayList<>();
        JSONObject response = null;
        try {

            response = new ServerRequester().execute("search.php", "whatever"
                    ,"email", Email
                    ,"firstName", firstName
                    ,"lastName",  lastName
                    ,"subject", subject
                    ,"university", university
                    ,"rating",  rating.toString()
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
                        Toast.makeText(getActivity(), "'"+Email+"' not found.", Toast.LENGTH_SHORT).show();
                        break;
                    default:    //Some Error Code was printed from the server that isn't handled above

                        break;
                }
            } else {//Everything went well
                Iterator<String> keys = response.keys();
                while (keys.hasNext()) {
                    JSONObject next = (JSONObject) response.get(keys.next());
                    filteredList.add(new NewItem(Integer.parseInt(next.get("profilePic").toString()),
                            next.get("firstName").toString(),
                            next.get("lastName").toString(),
                            next.get("email").toString(),
                            next.get("walkinStatus").toString(),
                            next.get("subject").toString(),
                            next.get("university").toString(),
                            Float.parseFloat(next.get("averageRating").toString())));

                    System.out.println(next.toString());
                }
                for(NewItem e : filteredList){
                    System.out.println(e.getemail());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter.filterList(filteredList);

    }

/*    private void CreateDataList() {
        DataList = new ArrayList<>();
        DataList.add(new NewItem(R.drawable.search_icon, "Aenah", "Ramones", "Computer Science", "Available", "CSULB", 3.50f));
        DataList.add(new NewItem(R.drawable.search_icon, "Nishant", "Saxena", "Computer Science", "Available", "CSULB", 3.00f));
        DataList.add(new NewItem(R.drawable.search_icon, "Shikha", "Saxena", "Speech Pathology", "Available", "FSU", 4.60f));
        DataList.add(new NewItem(R.drawable.search_icon, "Lance", " McVicar", "Computer Science", "Available", "CSULB", 2.00f));
        DataList.add(new NewItem(R.drawable.search_icon, "Henry", " Tran", "Line 2", "Available", "CSULB", 5f));
        DataList.add(new NewItem(R.drawable.search_icon, "Shahar", " Janjua", "Line 2", "Available", "CSULB", 1.5f));
        DataList.add(new NewItem(R.drawable.bacon, "Chris", " P. Bacon", "Line 2", "Available", "CSULB", 5f));
        DataList.add(new NewItem(R.drawable.search_icon, "Ella", " Vader", "Line 2", "Available", "CSULB", 3.4f));
        DataList.add(new NewItem(R.drawable.search_icon, "Bend", " Dover", "Line 2", "Available", "CSULB", 2.4f));
        DataList.add(new NewItem(R.drawable.search_icon, "Al", " Bino", "Line 2", "Available", "CSULB", 3f));
    }*/
}
