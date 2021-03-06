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
    String tutorFName;
    String tutorLName;
    Calendar dateOfAppointment;
    Calendar startTime;
    Calendar endTime;
    String location;
    String tutor; // email format
    String tutee; //email format
    long lengthOfAppointment; // this is in seconds...convert as needed
    int isAccepted;
    Context context;
    // PENDING: needs confirmation from tutor
    // UPCOMING: confirmed by tutor and awaiting time
    // PAST: completed
    // WALKIN: appointment generated from walkin
    // NULL: invalid or empty appointment

    public Appointment(Context c ){

        tutorEmail = "NULL";
        tuteeEmail = "NULL";
        context = c;
        // 0 = not accepted or rejected
        // 1 = accepted
        // -1 = rejected
        isAccepted = 0;
    }

    public Appointment(){
        isAccepted = 0;
    }

    public void setTutorFName(String s){ tutorFName = s;}
    public void setTutorLName(String s){ tutorLName = s;}
    public String getTutorLName(){ return tutorLName;}
    public String getTutorFName(){ return tutorFName;}

    public void setTuteeFName(String s){ tuteeFName = s;}
    public void setTuteeLName(String s){ tuteeLName = s;}
    public String getTuteeLName(){ return tuteeLName;}
    public String getTuteeFName(){ return tuteeFName;}
    public String getTutorEmail(){ return tutorEmail;}

    public void setTutorEmail(String s){ tutorEmail = s;}

    public int getAccepted(){ return isAccepted;}

    public void setAccepted(int i){
        isAccepted = i;
    }

    public String getTuteeEmail(){return tuteeEmail;}

    public void setTuteeEmail(String s){ tuteeEmail = s;}

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

    public String toStringTutee(){
        String date = String.format("%2s-%2s-%2s", dateOfAppointment.get(Calendar.MONTH), dateOfAppointment.get(Calendar.DAY_OF_MONTH), dateOfAppointment.get(Calendar.YEAR)).replace(' ', '0');
        String time = String.format("%2s:%2s-%2s:%2s", startTime.get(Calendar.HOUR_OF_DAY), startTime.get(Calendar.MINUTE), endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE)).replace(' ', '0');
        return "Name: " + tuteeFName + " " + tuteeLName + "  Date: " + date + "  Time:" + time;
    }

    public String toStringTutor(){
        String date = String.format("%2s-%2s-%2s", dateOfAppointment.get(Calendar.MONTH), dateOfAppointment.get(Calendar.DAY_OF_MONTH), dateOfAppointment.get(Calendar.YEAR)).replace(' ', '0');
        String time = String.format("%2s:%2s-%2s:%2s", startTime.get(Calendar.HOUR_OF_DAY), startTime.get(Calendar.MINUTE), endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE)).replace(' ', '0');
        return "Name: " + tutorFName + " " + tutorLName + "  Date: " + date + "  Time:" + time;
    }

}
