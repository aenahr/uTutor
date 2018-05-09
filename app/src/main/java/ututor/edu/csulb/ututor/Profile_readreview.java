package ututor.edu.csulb.ututor;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

/**
 * Created by Henry Tran on 2/12/2018.
 */

public class Profile_readreview extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Profile_review_detail> adapter;
    private ArrayList<Profile_review_detail> arrayList;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_readreview);
        listView = (ListView)findViewById(R.id.simpleListView);

        Intent i = getIntent();
        if(i.getSerializableExtra("from").equals("GenericProfile")){
            user = (User)i.getSerializableExtra("otherUser");
        }else{
            user = (User)i.getSerializableExtra("currentUser");

        }
        JSONObject response = null;
        arrayList = new ArrayList<>();

        try {
            response = new ServerRequester().execute("fetchRatings.php", "whatever"
                    ,"email", user.getEmail()
            ).get();
            if (response == null) {//Something went horribly wrong, JSON failed to be formed meaning something happened in the server requester

            } else if (!response.isNull("error")) {//Some incorrect information was sent, but the server and requester still processed it
                //TODO Handle Server Errors
                switch (response.get("error").toString()) {
                    case "2": //TODO Path taken if a user has no reviews/ratings

                        break;
                    default:    //Some Error Code was printed from the server that isn't handled above

                        break;
                }
            } else { //Everything Went Well
                Iterator<String> keys = response.keys();
                while(keys.hasNext()){
                    JSONObject next = (JSONObject) response.get(keys.next());
                    Profile_review_detail nextReview = new Profile_review_detail(Float.parseFloat(next.getString("rating")),next.getString("firstName") + " " + next.getString("lastName"),next.getString("feedback"));
                    //arrayList.add(new Profile_review_detail(Float.parseFloat(next.getString("rating")),next.getString("firstName") + " " + next.getString("lastName"),next.getString("feedback")));
                    System.out.println(nextReview.getName() + " " + nextReview.getRatingStar());
                    arrayList.add(nextReview);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

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
                RatingBar Star = (RatingBar) dialog.findViewById(R.id.rate_detail);

                Profile_review_detail user = (Profile_review_detail) parent.getAdapter().getItem(position);
                username.setText(user.getName());
                feedback.setText(user.getFeedback());
                Star.setRating(user.getRatingStar());

                Button dismiss = (Button) dialog.findViewById(R.id.Dimiss);

                dismiss.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        };
    }
}
