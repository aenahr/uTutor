package ututor.edu.csulb.ututor;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Henry Tran on 2/12/2018.
 */

public class Profile_readreview extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Profile_review_detail> adapter;
    private ArrayList<Profile_review_detail> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.simpleListView);
        setLisData();
        adapter = new Review_ListViewAdapter(this, R.layout.profile_review_list, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(onItemClickListener());

    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(Profile_readreview.this);//create a dialog
                dialog.setContentView(R.layout.profile_review_detail);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
                dialog.setTitle("Feedback details");

                TextView username = (TextView) dialog.findViewById(R.id.user_name);
                TextView feedback = (TextView) dialog.findViewById(R.id.username_feedback);
                TextView starRate = (TextView) dialog.findViewById(R.id.rate);
                RatingBar Star = (RatingBar) dialog.findViewById(R.id.rate_detail);

                Profile_review_detail user = (Profile_review_detail) parent.getAdapter().getItem(position);
                username.setText("Username: " + user.getName());
                feedback.setText("Feedback: " + user.getFeedback());
                starRate.setText("Your rate: " + user.getRatingStar());
                Star.setRating(user.getRatingStar());


                dialog.show();
            }
        };
    }

    private void setLisData() {
        arrayList = new ArrayList<>();
        arrayList.add(new Profile_review_detail(1, "Aneah", "A very positive experience. Christie is encouraging, supportive, and highly knowledgeable. The resources she has provided are useful and greatly help my understanding and confidence of the subject."));
        arrayList.add(new Profile_review_detail(2, "Bacon", "Thank you so much for a great lesson!"));
        arrayList.add(new Profile_review_detail(3, "Henry", "Covered a whole section of Mechanics thoroughly."));
        arrayList.add(new Profile_review_detail(3, "Lance", "He was extremely helpful."));
        arrayList.add(new Profile_review_detail(4, "Nishant", "Always happy to help me go through anything I don't get right, always smiling, great teacher!"));
        arrayList.add(new Profile_review_detail(2, "Smile", "Very helpful and clear "));


    }
}
