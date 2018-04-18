package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;


public class MyProfile_EditPicture extends AppCompatActivity {

    User currentUser;
    RadioButton blue;
    RadioButton red;
    RadioButton green;
    RadioButton purple;
    RadioButton yellow;
    RadioGroup chooseHead;
    Button saveChanges;
    Button cancelChanges;
    String selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_pic);

        Intent i = getIntent();
        currentUser = (User) i.getSerializableExtra("currentUser");

        // initialize views
        blue = (RadioButton) findViewById(R.id.blueHead);
        red = (RadioButton) findViewById(R.id.redHead);
        green = (RadioButton) findViewById(R.id.greenHead);
        purple = (RadioButton) findViewById(R.id.purpleHead);
        yellow = (RadioButton) findViewById(R.id.yellowHead);
        chooseHead = (RadioGroup) findViewById(R.id.choiceHead);
        saveChanges = (Button) findViewById(R.id.saveChanges);
        cancelChanges = (Button) findViewById(R.id.Cancel_bio);

        //have the current color the user is using selected
        selectCurrentHead();

        chooseHead.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.blueHead) {
                } else if (checkedId == R.id.redHead) {
                } else if (checkedId == R.id.greenHead) {
                } else if (checkedId == R.id.yellowHead) {

                } else if (checkedId == R.id.purpleHead) {

                }

            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {

            ProfilePicture p = new ProfilePicture(MyProfile_EditPicture.this);

            public void onClick(View view) {
                Bitmap bitmap;
                if (blue.isChecked()) {
                    p.setColor(0);
                    currentUser.setNumProfilePic(p.getIntColor());
//                    bitmap = p.getBitmapColor();
//                    currentUser.setProfilePic(bitmap);
                } else if (red.isChecked()) {
                    p.setColor(1);
                    currentUser.setNumProfilePic(p.getIntColor());
//                    bitmap = p.getBitmapColor();
//                    currentUser.setProfilePic(bitmap);
                } else if (green.isChecked()) {
                    p.setColor(2);
                    currentUser.setNumProfilePic(p.getIntColor());
//                    bitmap = p.getBitmapColor();
//                    currentUser.setProfilePic(bitmap);
                } else if (purple.isChecked()) {
                    p.setColor(4);
                    currentUser.setNumProfilePic(p.getIntColor());
//                    bitmap = p.getBitmapColor();
//                    currentUser.setProfilePic(bitmap);
                } else if (yellow.isChecked()) {
                    p.setColor(3);
                    currentUser.setNumProfilePic(p.getIntColor());
//                    bitmap = p.getBitmapColor();
//                    currentUser.setProfilePic(bitmap);
                }

//                currentUser.setNumProfilePic(p.getIntColor());

                // TODO update user in database
                try {
                    JSONObject response = new ServerRequester().execute("changePicture.php", "whatever",
                            "email", currentUser.getEmail(),
                            "profilePicNum", (Integer.toString(currentUser.getuNumProfilePic()))).get();
                    if (response.isNull("success")){
                        if (!response.isNull("error")) {//If the server returned an error code (So the request was at least processed)
                            switch (response.get("error").toString()) {
                                case "-1": //Email is not in the database

                                    break;
                                case "-2"://Query Failed

                                    break;
                                default: //Some unhandled error code was returned

                                    break;
                            }
                        } else {//Stuff happened that should NOT have happened

                        }
                    }else{//Stuff Happened that should have happened
                        Intent i = new Intent(MyProfile_EditPicture.this, HomePage.class);
                        i.putExtra("currentUser", currentUser);
                        i.putExtra("uploadPage", "myProfile");
                        startActivity(i);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {e.printStackTrace();
                }

            }
        });

        cancelChanges.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View view) {
                MyProfile_EditPicture.super.onBackPressed();
                finish();
            }
        });

    }

    public void selectCurrentHead() {
        if (currentUser.getuNumProfilePic() == 0) {
            //blue
            blue.setChecked(true);
        } else if (currentUser.getuNumProfilePic() == 3) {
            yellow.setChecked(true);
        } else if (currentUser.getuNumProfilePic() == 4) {
            purple.setChecked(true);
        } else if (currentUser.getuNumProfilePic() == 2) {
            green.setChecked(true);
        } else if (currentUser.getuNumProfilePic() == 1) {
            red.setChecked(true);
        }
    }

    /**
     * Converting bitmap to string code
     *
     * @param bitmap
     * @return
     */
    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}