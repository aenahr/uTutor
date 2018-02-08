package ututor.edu.csulb.ututor;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
    public String uID;
    public String uFirst;
    public String uLast;
    public String uEmail;
    public String uUniversity;
    public String uProfilePicture;
    public boolean isTutor = false;
    public boolean walkIn = false;
    public ArrayList<Integer> ratings = null;
    public ArrayList<String> favorites = null;
    public ArrayList<Appointment> appointments;
    public ArrayList<String> subjectsTaught;

    public User(){

        appointments = new ArrayList<Appointment>();
        subjectsTaught = new ArrayList<String>();

    }

    public boolean getWalkIn(){ return walkIn;}

    public void setWalkIn(boolean b){
        walkIn = b;
    }

    public ArrayList<Appointment> getAppointments(){ return appointments;}

    public void addNewAppointment(Appointment a){
        appointments.add(a);
    }

    public String getFirstName(){
        return uFirst;
    }

    public String getLastName(){
        return uLast;
    }

    public String getEmail(){
        return uEmail;
    }

    public String getID(){
        return uID;
    }

    public String getUniversity(){
        return uUniversity;
    }

    public boolean isTutor(){
        return isTutor;
    }

    public void setTutor(boolean b){
        isTutor = b;
    }

    public void setFirstName(String s){
        uFirst = s;
    }

    public void setLastName(String s){
        uLast = s;
    }

    public void setEmail(String s){
        uEmail = s;
    }

    public void setUniversity(String s){
        uUniversity = s;
    }

    public String getProfilePic(){
        return uProfilePicture;
    }

    public void setProfilePic(Bitmap b){
        String newPic = "";
        newPic = BitMapToString(b);
        // Bitmap to String
        // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bacon); // drawable to bitmap
        // String bit = BitMapToString(bitmap);
        uProfilePicture = newPic;
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
