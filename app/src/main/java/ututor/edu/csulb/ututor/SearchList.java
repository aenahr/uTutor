package ututor.edu.csulb.ututor;


import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Rating;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.Context.LOCATION_SERVICE;

public class SearchList extends Fragment {

    public SearchList() {
        filteredList = new ArrayList();
    }

    private ArrayList<NewItem> DataList;
    private ArrayList<NewItem> filteredList;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;

    private Button bRating, bUniversity, search, mapView;
    private EditText searchText;


    private User currentUser;
    private String Email, university, subject, firstName, lastName;
    private Float rating;
    // variables for current location and distane
    private int miles;
    private boolean fromBasic;
    private LatLng currentLocation;
    private LocationManager locationManager;
    private String provider;
    private SearchList.MyLocationListener mylistener;
    private Criteria criteria;
    public ArrayList<String> emailList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.search_list, container, false);

        mapView = (Button)rootView.findViewById(R.id.view);
        search = (Button) rootView.findViewById(R.id.search);
        searchText = (EditText) rootView.findViewById(R.id.searchEmail);
        searchText.setMaxWidth(searchText.getWidth()); //stops the search box from moving

        // get user's location
        requestLocationPermission();



        // get user information
        Intent i = getActivity().getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        fromBasic = true;

        Button advsearch = (Button) rootView.findViewById(R.id.advsearch);
        advsearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), Searchlist_filter.class);
                a.putExtra("searchtext", searchText.getText().toString());
                a.putExtra("currentUser", currentUser);
                startActivity(a);
            }
        });

        /*CreateDataList();  // data*/

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this.getActivity());

        //ArrayList<NewItem> filteredList = new ArrayList<>();
        adapter = new RecyclerAdapter(filteredList, currentUser, getActivity());  //initialise adapter with empty array

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        firstName = (String) i.getSerializableExtra("First name");
        lastName = (String) i.getSerializableExtra("Last name");
        subject = (String) i.getSerializableExtra("Subject");
        university = (String) i.getSerializableExtra("University");
        rating = (Float) i.getSerializableExtra("rating");
        Email = (String) i.getSerializableExtra("email");
        if(i.getSerializableExtra("distance") == null){
            miles = 999;
        }else{
            miles = (Integer) i.getSerializableExtra("distance");
        }
        if(firstName == null && lastName == null && Email == null && subject == null && university == null && rating == null && miles == 999) {
            firstName = "";
            lastName = "";
            Email = "";
            subject = "";
            university = "";
            rating = 0f;
            miles = 50;
        }else{ //objects from adv search
            searchText.setText(Email);
            fromBasic = false;
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
                        Email = searchText.getText().toString();
                        filter();
                    }
                });

            }
        });

        //sortby buttons
        bRating = (Button) rootView.findViewById(R.id.sRating);
        bUniversity = (Button) rootView.findViewById(R.id.sUniversity);

        bRating.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Collections.sort(filteredList, NewItem.RateComparator);
                adapter.filterList(filteredList);
            }
        });

        bUniversity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Collections.sort(filteredList , NewItem.UniComparator);
                adapter.filterList(filteredList);
            }
        });

        mapView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Search_MapView.class);
                i.putExtra("currentUser", currentUser);
                i.putExtra("listOfAppointments", filteredList);
                i.putExtra("fromBasic", fromBasic);
                i.putExtra("setDistance", miles);
                startActivity(i);
            }
        });

        return rootView;
    }


    /**
     * This method filters entries
     */
    private void filter() {
        filteredList = new ArrayList<>();

        // check if the user does not want to search by distance
        // set user's lat lng to zero
        if(miles == 0){ currentLocation = new LatLng(0,0); }
        // search walk-ins first
        JSONObject response = null;
        try {
            response = new ServerRequester().execute("searchWalkIn.php", "whatever"
                    ,"email", Email
                    ,"firstName", firstName
                    ,"lastName",  lastName
                    ,"subject", subject
                    ,"university", university
                    ,"distance", Integer.toString(miles)
                    ,"walkInLat", Double.toString(currentLocation.latitude) //IF Distance is zero, this must be zero as well
                    ,"walkInLong", Double.toString(currentLocation.longitude) //If Distance is zero, ^^
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
                            next.get("Subjects").toString(),
                            next.get("university").toString(),
                            Float.parseFloat(next.get("averageRating").toString()),
                            Double.parseDouble(next.get("walkInLat").toString()),
                            Double.parseDouble(next.get("walkInLong").toString()),
                            false
                            ));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response = null;
        try {
            response = new ServerRequester().execute("search.php", "whatever"
                    ,"email", Email
                    ,"firstName", firstName
                    ,"lastName",  lastName
                    ,"subject", subject
                    ,"university", university
                    ,"distance", Integer.toString(miles)
                    ,"workLat", Double.toString(currentLocation.latitude) //IF Distance is zero, this must be zero as well
                    ,"workLong", Double.toString(currentLocation.longitude) //If Distance is zero, ^^
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
                    // check if walk in exists first
                    JSONObject next = (JSONObject) response.get(keys.next());
                    if(filteredList.contains(next.get("email").toString())){
                        // do nothing, do not add
                    }else{
                        filteredList.add(new NewItem(Integer.parseInt(next.get("profilePic").toString()),
                                next.get("firstName").toString(),
                                next.get("lastName").toString(),
                                next.get("email").toString(),
                                next.get("walkinStatus").toString(),
                                next.get("Subjects").toString(),
                                next.get("university").toString(),
                                Float.parseFloat(next.get("averageRating").toString()),
                                Double.parseDouble(next.get("workLat").toString()),
                                Double.parseDouble(next.get("workLong").toString()),
                                true
                        ));
                    }
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

        if(filteredList.size() == 0){
            Toast.makeText(getActivity(), "No results found.", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Asks for permission AND if given, gets current user's position
     */
    public void requestLocationPermission(){
        // request permissions
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            String location_context = Context.LOCATION_SERVICE;
            locationManager = (LocationManager) getActivity().getSystemService(location_context);
            List<String> providers = locationManager.getProviders(true);
            for (String provider : providers) {
                locationManager.requestLocationUpdates(provider, 1000, 0,
                        new LocationListener() {
                            public void onLocationChanged(Location location) {}
                            public void onProviderDisabled(String provider) {}
                            public void onProviderEnabled(String provider) {}
                            public void onStatusChanged(String provider, int status, Bundle extras) {}
                        });
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    currentLocation = new LatLng(latitude, longitude);
//                    Toast.makeText(getActivity(), currentLocation.latitude + ", " + currentLocation.longitude, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * For the location listener and updating the location of user should they move
     */
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            // Initialize the location fields
            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(getActivity(), provider + "'s status changed to "+status +"!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getActivity(), "Provider " + provider + " enabled!", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getActivity(), "Provider " + provider + " disabled!",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
