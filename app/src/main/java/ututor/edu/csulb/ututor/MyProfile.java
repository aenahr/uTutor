package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;


public class MyProfile extends Fragment {

    User currentUser;
    Button modifyProfile;
    boolean canModify = false;
    TextView bioText;
    TextView profileName;
    ImageView profilePic;
    LinearLayout linearBio;
    LinearLayout linearReadReviews;
    ImageView editPic;

    RatingBar ratingBar;


    public MyProfile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.my_profile, container, false);

        // get user information
        Intent i = getActivity().getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");

        // initialize objects
        editPic = (ImageView) rootView.findViewById(R.id.editPic);
        modifyProfile = (Button) rootView.findViewById(R.id.modifyProfile);
        bioText = (TextView) rootView.findViewById(R.id.Biography);
        ratingBar =(RatingBar) rootView.findViewById(R.id.ratingBar);
        profilePic = (ImageView) rootView.findViewById(R.id.profilePic);
        profileName = (TextView) rootView.findViewById(R.id.profileName);
        linearBio = (LinearLayout) rootView.findViewById(R.id.linearBio);
        linearReadReviews = (LinearLayout) rootView.findViewById(R.id.linearReadReviews);

        JSONObject response = null;
        try {
            response = new ServerRequester().execute("fetchUser.php", "whatever",
                    "email", currentUser.getEmail()
            ).get();
            if (response == null) {//Something went horribly wrong, JSON failed to be formed meaning something happened in the server requester
            } else if (!response.isNull("error")) {//Some incorrect information was sent, but the server and requester still processed it
                switch (response.get("error").toString()) {
                    default:    //Some Error Code was printed from the server that isn't handled above

                        break;
                }
            } else { //Everything Went Well
                currentUser.setFirstName(response.get("firstName").toString());
                currentUser.setLastName(response.get("lastName").toString());
                currentUser.setEmail(response.get("email").toString());
                currentUser.setUniversity(response.get("university").toString());
                currentUser.setRating(Float.parseFloat(response.get("averageRating").toString()));
                currentUser.setDescription(response.get("userDescription").toString());
                currentUser.setNumProfilePic(Integer.parseInt(response.get("profilePic").toString()));
                currentUser.setSubjectsTaught(new ArrayList(Arrays.asList(response.getString("Subjects").split(","))));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        // Remember this format:
//        double num = 3.5;
//        currentUser.setRating((float)num);

        // load User's information
        ratingBar.setRating(currentUser.getRating());
        profileName.setText(currentUser.getFirstName() + " " + currentUser.getLastName()); // set First and Last Name

        ProfilePicture p = new ProfilePicture(getActivity());
        p.setColor(currentUser.getuNumProfilePic());

        Bitmap b = p.getBitmapColor();
        profilePic.setImageBitmap(b);


        linearBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canModify == true){
//                    Toast.makeText(getActivity(), "Going to edit", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), MyProfile_Edit.class);
                    i.putExtra("currentUser", currentUser);
                    startActivity(i);
                }
                else{
//                    Toast.makeText(getActivity(), "Going to view", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), MyProfile_Bio.class);
                    i.putExtra("currentUser", currentUser);
                    startActivity(i);
                }

            }
        });

        linearReadReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Profile_readreview.class);
                i.putExtra("from", "MyProfile");
                i.putExtra("currentUser", currentUser);
                startActivity(i);
            }
        });


        modifyProfile.setOnClickListener(new View.OnClickListener() { // edit profile information
            public void onClick(View view) {

                if(canModify == true){
                    canModify = false;
                    modifyProfile.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    modifyProfile.setTextColor(getResources().getColor(R.color.white));
                    modifyProfile.setText("Edit Profile");
                    bioText.setText("View Biography");
                    editPic.setVisibility(View.INVISIBLE);
                }
                else{
                    canModify = true;
                    modifyProfile.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    modifyProfile.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    modifyProfile.setText("Save Changes");
                    bioText.setText("Edit Biography");
                    editPic.setVisibility(View.VISIBLE);
                }
            }
        });

        editPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyProfile_EditPicture.class);
                i.putExtra("currentUser", currentUser);
                startActivity(i);
            }
        });


        return rootView;
    }

    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}