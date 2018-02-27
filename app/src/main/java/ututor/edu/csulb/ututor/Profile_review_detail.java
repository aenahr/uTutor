package ututor.edu.csulb.ututor;

/**
 * Created by Henry Tran on 2/26/2018.
 */

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.RatingBar;
import android.widget.TextView;

public class Profile_review_detail extends DialogFragment {

    String feedback = "";
    String feedbackDescription = "";
    int rateID = 0;
    int imgID = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_review_detail, container, false);

        TextView feedback_Title = (TextView) rootView.findViewById(R.id.title);
        ImageView imageV = (ImageView) rootView.findViewById(R.id.image);
        TextView des = (TextView) rootView.findViewById(R.id.description);
        RatingBar rate = (RatingBar)rootView.findViewById(R.id.review_rate) ;

        feedback_Title.setText(feedback);
        imageV.setImageResource(imgID);
        des.setText(feedbackDescription);

        Button dismiss = (Button) rootView.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }

    // method to make the dialog fullscreen
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void getInformation(String name, String description, int img, int rate){
        feedback = name;
        feedbackDescription = description;
        imgID = img;
        rateID = rate;



    }



}
