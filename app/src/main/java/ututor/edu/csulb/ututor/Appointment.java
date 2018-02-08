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

    public Appointment(){

    }

    public Calendar getDate(){ return dateOfAppointment;}

    public Calendar getStartTime(){ return startTime;}

    public Calendar getEndTime(){ return endTime;}

    public String getLocation(){ return location;}

    public String getTutor(){ return tutor;}

    public String getTutee(){ return tutee;}

    public long getLengthOfAppointment(){return lengthOfAppointment;}

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
