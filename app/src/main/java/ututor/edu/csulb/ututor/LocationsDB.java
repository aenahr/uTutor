package ututor.edu.csulb.ututor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocationsDB extends SQLiteOpenHelper{

    private static int DATABASE_VERSION = 1;

    public static String DATABASE_NAME = "googleMapsLocation";
    public static final String TABLE_NAME = "aenahLocation";
    public static final String KEY_ID = "id";
    public static final String FIELD_LAT = "lat";
    public static final String FIELD_LONG = "long";
    public static final String FIELD_ZOOM = "zoom";
    private SQLiteDatabase database;

    public LocationsDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + FIELD_LAT + " double , " +
                FIELD_LONG + " double , " +
                FIELD_ZOOM + " text " +
                " ) ";
        db.execSQL(CREATE_CATEGORIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertLocation(ContentValues values){
        long rowID = database.insert(TABLE_NAME, null, values);
        return rowID;
    }

    public int deleteAllLocations(){
        int num = database.delete(TABLE_NAME, null, null);
        return num;
    }

    public int deleteLocation(){
        int cnt = database.delete(TABLE_NAME, null , null);
        return cnt;
    }

    public Cursor getAllLocations(){
        return database.query(TABLE_NAME, new String[] { KEY_ID,  FIELD_LAT , FIELD_LONG, FIELD_ZOOM } , null, null, null, null, null);
    }


}
