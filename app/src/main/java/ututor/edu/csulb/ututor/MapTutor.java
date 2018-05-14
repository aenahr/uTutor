package ututor.edu.csulb.ututor;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by aenah on 5/13/18.
 */

public class MapTutor {
    private MarkerOptions markerOptions;
    private Marker marker;
    private InfoWindowData infoWindowData;

    public MapTutor(MarkerOptions mo, Marker m, InfoWindowData i){
        markerOptions = mo;
        marker = m;
        infoWindowData = i;
    }

    public MarkerOptions getMarkerOptions(){ return markerOptions;}
    public Marker getMarker(){ return marker;}
    public InfoWindowData getInfoWindowData(){return infoWindowData;}

}
