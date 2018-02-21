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

import java.io.ByteArrayOutputStream;


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
    Button imgBack;


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
        imgBack = (Button) findViewById(R.id.backButton);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyProfile_EditPicture.super.onBackPressed();
                finish();
            }
        });

        //have the current color the user is using selected
        selectCurrentHead();

        chooseHead.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.blueHead) {
                } else if(checkedId == R.id.redHead){
                } else if(checkedId == R.id.greenHead){
                } else if(checkedId == R.id.yellowHead){

                } else if(checkedId == R.id.purpleHead){

                }

            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Bitmap bitmap;
                if(blue.isChecked()){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ututorlogo); // drawable to bitmap
                    currentUser.setProfilePic(bitmap);
                } else if(red.isChecked()){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tutorhead_red); // drawable to bitmap
                    currentUser.setProfilePic(bitmap);
                } else if(green.isChecked()){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tutorhead_green); // drawable to bitmap
                    currentUser.setProfilePic(bitmap);
                } else if(purple.isChecked()){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tutorhead_purple); // drawable to bitmap
                    currentUser.setProfilePic(bitmap);
                } else if(yellow.isChecked()){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tutorhead_yellow); // drawable to bitmap
                    currentUser.setProfilePic(bitmap);
                }
                // TODO update user's profile pic in database
                // go back to profile with saved changes
                Intent i = new Intent(MyProfile_EditPicture.this, HomePage.class);
                i.putExtra("currentUser", currentUser);
                i.putExtra("uploadPage", "myProfile");
                startActivity(i);
            }
        });

        cancelChanges.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                MyProfile_EditPicture.super.onBackPressed();
                finish();
            }
        });

    }

    public void selectCurrentHead(){
        if(currentUser.getProfilePic().equals(BitMapToString(BitmapFactory.decodeResource(getResources(), R.drawable.ututorlogo)))){
            //blue
            blue.setChecked(true);
        } else if(currentUser.getProfilePic().equals(BitMapToString(BitmapFactory.decodeResource(getResources(), R.drawable.tutorhead_yellow)))){
            yellow.setChecked(true);
        } else if(currentUser.getProfilePic().equals(BitMapToString(BitmapFactory.decodeResource(getResources(), R.drawable.tutorhead_purple)))){
            purple.setChecked(true);
        } else if(currentUser.getProfilePic().equals(BitMapToString(BitmapFactory.decodeResource(getResources(), R.drawable.tutorhead_green)))){
            green.setChecked(true);
        } else if(currentUser.getProfilePic().equals(BitMapToString(BitmapFactory.decodeResource(getResources(), R.drawable.tutorhead_red)))){
            red.setChecked(true);
        }
    }

    /**
     * Converting bitmap to string code
     * @param bitmap
     * @return
     */
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}