package ututor.edu.csulb.ututor;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class SearchMap extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>, OnMapReadyCallback {

    private final LatLng LOCATION_UNIV = new LatLng(33.783768, -118.114336);
    private final LatLng LOCATION_ECS = new LatLng(33.782777, -118.111868);
    private final LatLng LOCATION_1 = new LatLng(33.783823, -118.114090);
    private final LatLng LOCATION_2 = new LatLng(33.787746, -118.114593);
    private final LatLng LOCATION_3 = new LatLng(33.785250, -118.108585);



    private GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_map);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private class LocationInsertTask extends AsyncTask<ContentValues, Void, Void> {
        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            getContentResolver().insert(LocationsContentProvider.CONTENT_URI, contentValues[0]);
            return null;
        }
    }

    private class LocationDeleteTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            getContentResolver().delete(LocationsContentProvider.CONTENT_URI, null, null);
            return null;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        Loader<Cursor> c = null;
        //uri to the content provider LocationsContentProvider
        //Fetches all the rows from locations table
        Uri uri = LocationsContentProvider.CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {

        int locationCount = 0;
        double lat = 0;
        double lng = 0;
        float zoom = 0;


        if (arg1 != null) {
            locationCount = arg1.getCount();
            arg1.moveToFirst();
        } else {
            locationCount = 0;
        }

        for (int i = 0; i < locationCount; i++) {
            //get lat
            //get long
            //get zoom level
            //create instance of LatLng to plot the location in Google Maps
            //draw marker in google maps
            //traverse pointer to the next row

            lat = arg1.getDouble(arg1.getColumnIndex(LocationsDB.FIELD_LAT));
            lng = arg1.getDouble(arg1.getColumnIndex(LocationsDB.FIELD_LONG));
            zoom = arg1.getFloat(arg1.getColumnIndex(LocationsDB.FIELD_ZOOM));
            Log.i("GoogleMapsTutorial", "the lat is " + lat);
            LatLng location = new LatLng(lat, lng);
            writeLocation(location);
            arg1.moveToNext();

        }

        if (locationCount > 0) {
            //moving camera position to last clicked position
            //set the zoom level in the map on last position is clicked

            Log.i("GoogleMapsTutorial", "the lat is now " + lat);
            LatLng coordinate = new LatLng(lat, lng);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, zoom);
            map.animateCamera(yourLocation);
        }

    }

    public void onClick_ECS(View v) {
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 16);
        map.animateCamera(update);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(LOCATION_ECS).title("Aenah Ramones"));
        map.addMarker(new MarkerOptions().position(LOCATION_UNIV).title("Lance McVicar"));
        map.addMarker(new MarkerOptions().position(LOCATION_1).title("Nishant Saxena"));
        map.addMarker(new MarkerOptions().position(LOCATION_2).title("Henry Tran"));
        map.addMarker(new MarkerOptions().position(LOCATION_3).title("Shahar Janjuan"));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        getLoaderManager().initLoader(1, null, this);

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
//                writeLocation(point);
//                ContentValues values = new ContentValues();
//                values.put(LocationsDB.FIELD_LAT, point.latitude);
//                values.put(LocationsDB.FIELD_LONG, point.longitude);
//                values.put(LocationsDB.FIELD_ZOOM, map.getCameraPosition().zoom);
//                LocationInsertTask insertTask = new LocationInsertTask();
//                insertTask.execute(values);
//                //notify user
//                Toast.makeText(getBaseContext(), "New location stored!", Toast.LENGTH_SHORT).show();
            }
        });

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
//
//                map.clear();
//                LocationDeleteTask deleteTask = new LocationDeleteTask();
//                deleteTask.execute();
//
//                // notify user
//                Toast.makeText(getBaseContext(), "All locations deleted.", Toast.LENGTH_LONG).show();

            }
        });

    }

    /**
     * Pin a location to the map
     * @param point lat and lang of the location
     */
    private void writeLocation(LatLng point){
        MarkerOptions loc = new MarkerOptions();
        loc.position(point);
        map.addMarker(loc);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}