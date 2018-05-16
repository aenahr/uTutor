package ututor.edu.csulb.ututor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by aenah on 5/5/18.
 */

public class Search_MapView extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener{

    private final LatLng LOCATION_UNIV = new LatLng(33.783768, -118.114336);
    public LatLng userLocation;
    Context mContext;
    GoogleApiClient mGoogleApiClient;
    Location currentLocation;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
    Circle currentMapCircle;

    GoogleMap mGoogleMap;
    User currentUser;
    SeekBar seekBar;
    TextView seekBarValue;
    int mProgress;

    ArrayList<NewItem> appointmentTutors;
    ArrayList<NewItem> walkInTutors;
    ArrayList<NewItem> allAppointments;
    ArrayList<MapTutor> allMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_map);
        mContext = this;

        // initialize all the map stuff
        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        allAppointments = (ArrayList<NewItem>) i.getSerializableExtra("listOfAppointments");
        appointmentTutors = new ArrayList<NewItem>();
        walkInTutors = new ArrayList<NewItem>();

        // sort tutors
        for(int x = 0; x < allAppointments.size(); x++){
            System.out.println(allAppointments.get(x).getImage());
            if(allAppointments.get(x).getWalkInStatus() == true)
                walkInTutors.add(allAppointments.get(x));
            else
                appointmentTutors.add(allAppointments.get(x));
        }

        allMarkers = new ArrayList<MapTutor>();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.incrementProgressBy(5);
        seekBar.setMax(50);
        if((boolean)i.getSerializableExtra("fromBasic") == true){
            seekBar.setProgress(10);
            mProgress = 10; // starting value
        }
        else{ // from advanced search - keep constant miles value
            mProgress = (int)i.getSerializableExtra("setDistance"); // starting value
            seekBar.setProgress(mProgress);
            seekBar.setEnabled(false);
        }
        seekBarValue = (TextView)findViewById(R.id.seekBarValue);
        seekBarValue.setText(String.valueOf(seekBar.getProgress()) + " miles");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 5;
                progress = progress * 5;
                mProgress = progress;
                seekBarValue.setText(String.valueOf(progress) + " miles");
                drawCircle(userLocation, mProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private void drawCircle(LatLng point, int radius){
        if(currentMapCircle != null){ currentMapCircle.remove(); }
        mGoogleMap.clear();

        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle
        circleOptions.center(point);

        // Radius of the circle
        // radius is in meters, so convert to miles
        circleOptions.radius(radius * 1609.34);

        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK);

        // Fill color of the circle
        circleOptions.fillColor(0x30607D8B);

        // Border width of the circle
        circleOptions.strokeWidth(2);

        // sets only within circle to be visible
        for (int i = 0; i< allMarkers.size(); i++){
            if(distanceBetween(allMarkers.get(i).getMarker().getPosition(), userLocation) > radius){ }
            else{
                MarkerOptions markerOptions = allMarkers.get(i).getMarkerOptions();
                InfoWindowData info = allMarkers.get(i).getInfoWindowData();
                Marker m = mGoogleMap.addMarker(markerOptions);
                m.setTag(info);
                m.showInfoWindow();
            }
        }

        // Adding the circle to the GoogleMap
        currentMapCircle = mGoogleMap.addCircle(circleOptions);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circleOptions.getCenter(), getZoomLevel(currentMapCircle)));

    }

    @Override
    public void onPause() {
        super.onPause();
        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) { mFusedLocationClient.removeLocationUpdates(mLocationCallback); }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setPadding(0, 0, 0, 100);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(12000); // two minute interval
        mLocationRequest.setFastestInterval(12000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        addWalkInLocations();
        addWorkLocations();
        mGoogleMap.setOnInfoWindowClickListener(this);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                // first time finding location
                mGoogleMap.setMyLocationEnabled(true);
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String bestProvider = locationManager.getBestProvider(criteria, true);
                Location location = locationManager.getLastKnownLocation(bestProvider);
                if (location != null) {
                    currentLocation = location;
                    userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    Toast.makeText(this, userLocation.latitude + ", " + userLocation.longitude, Toast.LENGTH_SHORT).show();
                    // create starting circle
                    drawCircle(userLocation, mProgress);
                }
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            } else { //Request Location Permission
                checkLocationPermission();
            }
        }
        else { // API
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                mLastLocation = location;
                if (mCurrLocationMarker != null) { mCurrLocationMarker.remove(); }
                //Place current location marker
                userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                // create starting circle
                drawCircle(userLocation, mProgress);
            }
        }
    };

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Search_MapView.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION );
                            }}).create().show();
            } else { // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    }
                }else{
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }

    @Override
    public void onResume() { super.onResume();}

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) { }

    // gets zoom level for the circle being drawn
    public int getZoomLevel(Circle circle) {
        int zoomLevel = 11;
        if (circle != null) {
            double radius = circle.getRadius() + circle.getRadius() / 2;
            double scale = radius / 500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }

    public void addWorkLocations(){
        for(int i = 0; i < appointmentTutors.size(); i++){

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(appointmentTutors.get(i).getLat(), appointmentTutors.get(i).getLng()))
                    .title(appointmentTutors.get(i).getemail())
                    .icon(BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_RED));

            // add to custom info window
            InfoWindowData info = new InfoWindowData();
            info.setEmail(appointmentTutors.get(i).getemail());
            info.setName(appointmentTutors.get(i).getfirstname() + " " + appointmentTutors.get(i).getlastname());
            info.setProfilePic(appointmentTutors.get(i).getImage());
            info.setRating(appointmentTutors.get(i).getrating());
            if(appointmentTutors.get(i).getstatus().equals("0")) info.setStatus("Walk-In Unavailable");
            else info.setStatus("Walk-In Available");

            // incorporate google map custom window
            CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this);
            mGoogleMap.setInfoWindowAdapter(customInfoWindow);

            Marker m = mGoogleMap.addMarker(markerOptions);
            m.setTag(info);
            m.showInfoWindow();
            MapTutor mapTutor = new MapTutor(markerOptions, m, info);
            allMarkers.add(mapTutor);        }
    }

    public void addWalkInLocations(){
        for(int i = 0; i < walkInTutors.size(); i++){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(walkInTutors.get(i).getLat(), walkInTutors.get(i).getLng()))
                    .title(walkInTutors.get(i).getemail())
                    .icon(BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_CYAN));

            // add to custom info window
            InfoWindowData info = new InfoWindowData();
            info.setEmail(walkInTutors.get(i).getemail());
            info.setName(walkInTutors.get(i).getfirstname() + " " + walkInTutors.get(i).getlastname());
            info.setProfilePic(walkInTutors.get(i).getImage());
            info.setRating(walkInTutors.get(i).getrating());
            if(walkInTutors.get(i).getstatus().equals("0")) info.setStatus("Walk-In Unavailable");
            else info.setStatus("Walk-In Available");

            // incorporate google map custom window
            CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this);
            mGoogleMap.setInfoWindowAdapter(customInfoWindow);

            Marker m = mGoogleMap.addMarker(markerOptions);
            m.setTag(info);
            m.showInfoWindow();
            MapTutor mapTutor = new MapTutor(markerOptions, m, info);
            allMarkers.add(mapTutor);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
//        Toast.makeText(this, "distance between " + marker.getTitle() + " and myLocation is " + distanceBetween(marker.getPosition(), userLocation), Toast.LENGTH_SHORT).show();
        JSONObject response = null;
        try {
            response = new ServerRequester().execute("fetchUser.php", "whatever",
                    "email", marker.getTitle()
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
                otherUser.setSubjectsTaught(new ArrayList(Arrays.asList(response.getString("Subjects").split(","))));

                Intent i = new Intent(this, GenericProfile.class);
                i.putExtra("currentUser", currentUser);
                i.putExtra("otherUser", otherUser);
                startActivity(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * Gets distance between in miles
     * @param a location 1
     * @param b location 2
     * @return
     */
    public double distanceBetween(LatLng a, LatLng b){
        Location aLocation =new Location("locationA");
        aLocation.setLatitude(a.latitude);
        aLocation.setLongitude(a.longitude);
        Location bLocation=new Location("locationB");
        bLocation.setLatitude(b.latitude);
        bLocation.setLongitude(b.longitude);
        double distance=bLocation.distanceTo(aLocation);
        return distance / 1609.34;
    }
}