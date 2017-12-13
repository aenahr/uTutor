package ututor.edu.csulb.ututor;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LocationsContentProvider extends ContentProvider{

    public static final String PROVIDER_NAME = "googlemaps.example.com.googlemaptutorial.LocationsContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/" + LocationsDB.TABLE_NAME);
    LocationsDB mLocationsDB;
    private static final UriMatcher uriMatcher;
    private static final int LOCATIONS = 1;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "aenahLocation", LOCATIONS);
    }

    @Override
    public boolean onCreate() {
        mLocationsDB = new LocationsDB(getContext());
        return true;
    }

    @Override
    public Cursor query( Uri uri, String[] strings, String s, String[] strings1, String s1) {
        if(uriMatcher.match(uri)==LOCATIONS){
            return mLocationsDB.getAllLocations();
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long rowID = mLocationsDB.insertLocation(contentValues);
        Uri uRI=null;
        if(rowID>0){
            uRI = ContentUris.withAppendedId(CONTENT_URI, rowID);
        }else {
            try {
                throw new SQLException("Failed to insert : " + uri);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return uRI;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int cnt = 0;
        cnt = mLocationsDB.deleteLocation();
        return cnt;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
