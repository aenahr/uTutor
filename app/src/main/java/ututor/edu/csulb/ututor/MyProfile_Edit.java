package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class MyProfile_Edit extends AppCompatActivity {

    User currentUser;
    EditText firstName;
    EditText lastName;
    EditText eEmail;
    EditText collegeName;
    Button saveChanges;
    Button cancelEdit;
    EditText currentPassword;
    EditText newPassword;
    boolean emailAlreadyInDatabase = false;
    EditText description;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_edit);

        Intent i = getIntent();
        currentUser = (User) i.getSerializableExtra("currentUser");
        // initialize all objects
        firstName = (EditText) findViewById(R.id.bio_fname);
        lastName = (EditText) findViewById(R.id.bio_lname);
        eEmail = (EditText) findViewById(R.id.bio_email);
        collegeName = (EditText) findViewById(R.id.text_edu);
        saveChanges = (Button) findViewById(R.id.saveChanges);
        cancelEdit = (Button) findViewById(R.id.Cancel_bio);
        currentPassword = (EditText) findViewById(R.id.bio_currentPass);
        newPassword = (EditText) findViewById(R.id.bio_newPass);
        description = (EditText) findViewById(R.id.summaryEdit);


        // set from User's values
        firstName.setText(currentUser.getFirstName());
        lastName.setText(currentUser.getLastName());
        eEmail.setText(currentUser.getEmail());
        collegeName.setText(currentUser.getUniversity());
        description.setText(currentUser.getDescription());

        // TODO: if newpassword is empty, set currentPassword to newpassword


        saveChanges.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                JSONObject response = null;
                try { //Will internally test for Current Email and Password Matches, Returns Success if successful
                    //TODO Aenah Help, Need to put what is entered into the page fields into the request
                    response = new ServerRequester().execute("changeProfile.php", "whatever",
                            "currentEmail", currentUser.getEmail(),
                            "newEmail", eEmail.getText().toString(),
                            "currentPassword", currentPassword.getText().toString(),
                            "newPassword", newPassword.getText().toString(),
                            "firstName", firstName.getText().toString(),
                            "lastName", lastName.getText().toString(),
                            "university", collegeName.getText().toString(),
                            "description", description.getText().toString()).get();
                    if (response == null) {//Something went horribly , JSON failed to be formed meaning something happened in the server requester
                    } else if (!response.isNull("error")) {//Some incorrect information was sent, but the server and requester still processed it
                        //TODO Handle Server Errors
                        switch(response.get("error").toString()) {
                            case "-1": //Email Password Combo not in the Database
                                Toast.makeText(getApplicationContext(), "Current Password does not match database. Try again." , Toast.LENGTH_SHORT).show();
                                break;
                            case "-2":  //Select Query failed due to something dumb
                                        // Print out response.get("errormessage"), it'll have the mysql error with it

                                break;
                            case "-3": //Update Query Failed Due to New Email is already associated with another account
                                Toast.makeText(getApplicationContext(), "Email address already exists." , Toast.LENGTH_SHORT).show();
                                break;
                            case "-4":  //Update Query Failed Due to Something Else Dumb that I haven't handled yet,
                                        // Print out response.get("errormessage"), it'll have the mysql error with it
                                break;
                            default:    //Some Error Code was printed from the server that isn't handled above

                                break;
                        }
                    }
                    else{
                        // update changes in user throughout all of app
                        currentUser.setFirstName(firstName.getText().toString());
                        currentUser.setLastName(lastName.getText().toString());
                        currentUser.setEmail(eEmail.getText().toString());
                        currentUser.setUniversity(collegeName.getText().toString());
                        currentUser.setDescription(description.getText().toString());

                        // go back to profile with saved changes
                        Intent i = new Intent(MyProfile_Edit.this, HomePage.class);
                        i.putExtra("currentUser", currentUser);
                        i.putExtra("uploadPage", "myProfile");
                        startActivity(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch(JSONException e) {
                }
                }

        });

        cancelEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MyProfile_Edit.super.onBackPressed();
            }
        });


    }
}