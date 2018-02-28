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
    public int uNumProfilePic;
    public boolean isTutor;
    public boolean walkIn;
    public float rating;
    public String uDescription;
    public ArrayList<String> favorites = null;
    public ArrayList<Appointment> appointments;
    public ArrayList<String> subjectsTaught;
    public ArrayList<WorkHour> workHours;

    public User(){

        uNumProfilePic = 0;
        rating = 0;
        isTutor = false;
        walkIn = false;
        appointments = new ArrayList<Appointment>();
        subjectsTaught = new ArrayList<String>();
        workHours = new ArrayList<WorkHour>();
        uUniversity = "NONE";
        uDescription = "NULL";


    }

    public String getDescription(){
        if(uDescription.equals("NULL")){
            return "";
        }
        else{
            return uDescription;
        }
    }
    public int getuNumProfilePic(){ return uNumProfilePic;}

    public float getRating(){ return rating;}

    public ArrayList<String> getSubjectsTaught(){ return subjectsTaught;}

    public ArrayList<WorkHour> getWorkHours(){ return workHours;}

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

    public void setDescription(String newDescription){ uDescription = newDescription;}

    public void setNumProfilePic(int newNum){ uNumProfilePic = newNum;}

    public void setRating(float d){ rating = d;}

    public void addNewHour(WorkHour newHour){
        workHours.add(newHour);
    }

    public void setSubjectsTaught(ArrayList<String> newSubjects){
        subjectsTaught = newSubjects;
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
