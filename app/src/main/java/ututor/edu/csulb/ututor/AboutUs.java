package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;
import android.net.Uri;

public class AboutUs extends Fragment {

    public Button henry;
    public Button nishant;
    public Button aenah;
    public Button lance;
    public Button c_email;
    public Button c_number;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.about_us, container, false);
        /* Henry button to go Henry profile */
        henry = (Button) rootView.findViewById(R.id.Henry_id);
        henry.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {


            }
        });

        nishant = (Button) rootView.findViewById(R.id.Nishant_id);
        nishant.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {


            }
        });

        aenah = (Button) rootView.findViewById(R.id.Aenah_id);
        aenah.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {


            }
        });

        lance = (Button) rootView.findViewById(R.id.Lance_id);
        lance.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {


            }
        });

        c_number = (Button) rootView.findViewById(R.id.contactnumber);
        c_number.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:0377778888"));
                startActivity(callIntent);
            }

        });

        c_email = (Button) rootView.findViewById(R.id.contactEmail);
        c_email.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                sendEmail();

            }
        });

        return rootView;
    }

    protected void sendEmail() {


        Log.i("Send email", "");
        String[] TO = {"ututor.project@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
        startActivity(emailIntent);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}