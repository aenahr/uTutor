package ututor.edu.csulb.ututor;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Calendar;

public class Appointment implements Serializable{
    String tutorEmail;
    String tuteeEmail;
    String tuteeFName;
    String tuteeLName;
    Calendar dateOfAppointment;
    Calendar startTime;
    Calendar endTime;
    String location;
    String tutor; // email format
    String tutee; //email format
    long lengthOfAppointment; // this is in seconds...convert as needed
    String typeOfAppointment; // OPTIONS ARE: PENDING, UPCOMING, PAST, WALKIN, NULL
    boolean isAccepted;
    Context context;
    // PENDING: needs confirmation from tutor
    // UPCOMING: confirmed by tutor and awaiting time
    // PAST: completed
    // WALKIN: appointment generated from walkin
    // NULL: invalid or empty appointment

    public Appointment(Context c ){

        tutorEmail = "NULL";
        tuteeEmail = "NULL";
        typeOfAppointment = "NULL";
        context = c;
        isAccepted = false;
    }

    public Appointment(){
        typeOfAppointment = "NULL";
        isAccepted = false;
    }

    public void setTuteeFName(String s){ tuteeFName = s;}
    public void setTuteeLName(String s){ tuteeLName = s;}
    public String getTuteeLName(){ return tuteeLName;}
    public String getTuteeFName(){ return tuteeFName;}
    public String getTutorEmail(){ return tutorEmail;}

    public void setTutorEmail(String s){ tutorEmail = s;}

    public boolean getAccepted(){ return isAccepted;}

    public void setAccepted(boolean b){
        isAccepted = b;
    }

    public String getTuteeEmail(){return tuteeEmail;}

    public void setTuteeEmail(String s){ tuteeEmail = s;}

    public String getTypeOfAppointment(){ return typeOfAppointment;}

    public Calendar getDate(){ return dateOfAppointment;}

    public Calendar getStartTime(){ return startTime;}

    public Calendar getEndTime(){ return endTime;}

    public String getLocation(){ return location;}

    public String getTutor(){ return tutor;}

    public String getTutee(){ return tutee;}

    public long getLengthOfAppointment(){return lengthOfAppointment;}

    /**
     * Specific number of options to choose from
     * @param s type of Appointment
     */
    public void setTypeOfAppointment(String s){
        if (s.equals("PENDING") || s.equals("UPCOMING") || s.equals("PAST") || s.equals("WALKIN")) { // must be valid type
            typeOfAppointment = s;
        }
        else{
            typeOfAppointment = "NULL";
        }
    }

    public void setDateOfAppointment(Calendar c){
        dateOfAppointment = c;
    }

    public void setStartTime(Calendar c){
        startTime = c;
    }

    public void setEndTime(Calendar c){
        endTime = c;
    }

    public void setLocation(String s){
        location = s;
    }

    public void setTutor(String s){
        tutor = s;
    }

    public void setTutee(String s){
        tutee = s;
    }

    public void setLengthOfAppointment(long l){
        lengthOfAppointment = l;
    }

    public String toString(){
        String date = String.format("%02-%02-%02", dateOfAppointment.get(Calendar.MONTH), dateOfAppointment.get(Calendar.DAY_OF_MONTH), dateOfAppointment.get(Calendar.YEAR));
        String time = String.format("%02:%02 - %02:%02", startTime.get(Calendar.HOUR_OF_DAY), startTime.get(Calendar.MINUTE), endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE));
        return "Name: " + tuteeFName + " " + tuteeLName + "\tDate: " + date + "\tTime:" + time;
    }

}
