package ututor.edu.csulb.ututor;

import ututor.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.search_map_info, null);

        TextView name_tv = view.findViewById(R.id.name);
        TextView email_tv = view.findViewById(R.id.email);
        ImageView img = view.findViewById(R.id.pic);
        TextView status_tv = view.findViewById(R.id.status);
        @SuppressLint("WrongViewCast") RatingBar ratingBar = view.findViewById(R.id.rating);


        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

        int picCode = infoWindowData.getProfilePic();
        if( picCode == 0 ){ //blue
            img.setImageResource(R.drawable.ututorlogo);
        } else if( picCode == 1 ){ // red
            img.setImageResource( R.drawable.tutorhead_red);
        } else if(picCode == 2) // green
        {
            img.setImageResource( R.drawable.tutorhead_green);
        } else if(picCode == 3) // yellow
        {
            img.setImageResource( R.drawable.tutorhead_yellow);
        } else if( picCode == 4)// purple
        {
            img.setImageResource( R.drawable.tutorhead_purple);
        }


        email_tv.setText(infoWindowData.getEmail());
        name_tv.setText(infoWindowData.getName());
        status_tv.setText(infoWindowData.getStatus());
        ratingBar.setRating(infoWindowData.getRating());

        return view;
    }
}
