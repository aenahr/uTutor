package ututor.edu.csulb.ututor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aenah on 4/25/18.
 */

public class AppointmentListView extends BaseAdapter {


    private ArrayList<HashMap<String,String>> list;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public AppointmentListView(ArrayList<HashMap<String,String>> list, Context c) {
        this.list = list;
        this.mContext = c;
        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = mLayoutInflater.inflate(R.layout.fragment_am_item,parent,false);
        Holder h = new Holder();

        // set id's
        h.name = (TextView)(view.findViewById(R.id.name));
        h.typeTitle = (TextView)(view.findViewById(R.id.typeTitle));
        h.date = (TextView)(view.findViewById(R.id.date));
        h.time = (TextView)(view.findViewById(R.id.time));
        h.background = (RelativeLayout)(view.findViewById(R.id.customItem));

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap = list.get(position);

        h.name.setText(hashMap.get("nameKey"));
        h.typeTitle.setText(hashMap.get("typeTitleKey"));
        h.date.setText(hashMap.get("dateKey"));
        h.time.setText(hashMap.get("timeKey"));
        if(hashMap.get("backgroundKey").equals("yellow")){ h.background.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent)); }
        else{ h.background.setBackgroundColor(mContext.getResources().getColor(R.color.colorLightPrimary)); }


        return view;
    }


    private class Holder
    {
        TextView typeTitle;
        TextView name;
        TextView date;
        TextView time;
        RelativeLayout background;
    }
}
