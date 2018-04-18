package ututor.edu.csulb.ututor;

/**
 * Created by Henry Tran on 3/5/2018.
 */

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class Review_ListViewAdapter extends ArrayAdapter<Profile_review_detail>  {

    private AppCompatActivity activity;
    private List<Profile_review_detail> reviewList;

    public Review_ListViewAdapter(AppCompatActivity context, int resource, List<Profile_review_detail> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.reviewList = objects;
    }

    @Override
    public Profile_review_detail getItem(int position) {
        return reviewList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.profile_review_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
            //holder.ratingBar.getTag(position);
        }

        holder.ratingBar.setOnRatingBarChangeListener(onRatingChangedListener(holder, position));

        holder.ratingBar.setTag(position);
        holder.ratingBar.setRating(getItem(position).getRatingStar());
        holder.userName_Feed.setText(getItem(position).getName());

        return convertView;
    }

    private RatingBar.OnRatingBarChangeListener onRatingChangedListener(final ViewHolder holder, final int position) {
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Profile_review_detail item = getItem(position);
                item.setRatingStar(v);
                Log.i("Adapter", "star: " + v);
            }
        };
    }

    private static class ViewHolder {
        private RatingBar ratingBar;
        private TextView userName_Feed;

        public ViewHolder(View view) {
            ratingBar = (RatingBar) view.findViewById(R.id.RatingBar_list);
            userName_Feed = (TextView) view.findViewById(R.id.textView);
        }
    }
}