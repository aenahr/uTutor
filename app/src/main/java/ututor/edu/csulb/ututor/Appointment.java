package ututor.edu.csulb.ututor;

import java.io.Serializable;
import java.util.Calendar;

public class Appointment implements Serializable{
    Calendar dateOfAppointment;
    Calendar startTime;
    Calendar endTime;
    String location;
    String tutor; // email format
    String tutee; //email format
    long lengthOfAppointment; // this is in seconds...convert as needed
    String typeOfAppointment; // OPTIONS ARE: PENDING, UPCOMING, PAST, WALKIN, NULL
    // PENDING: needs confirmation from tutor
    // UPCOMING: confirmed by tutor and awaiting time
    // PAST: completed
    // WALKIN: appointment generated from walkin
    // NULL: invalid or empty appointment

    public Appointment(){
        typeOfAppointment = "none";
    }

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




}
