package ututor.edu.csulb.ututor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.framgia.library.calendardayview.CalendarDayView;
import com.framgia.library.calendardayview.EventView;
import com.framgia.library.calendardayview.data.IEvent;
import com.framgia.library.calendardayview.data.IPopup;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import android.widget.TimePicker;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class AppointmentTimePicker extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    // other user's information needed - appointments and work hours
    CalendarDayView dayView;
    ArrayList<IEvent> events;
    ArrayList<IPopup> popups;
    private int startOrEnd = 0;

    // time Pickers
    private TimePicker startTimePicker;
    private int startHour = 0;
    private int startMinute = 0;

    //text Views
    private TextView displayStart;
    private TextView displayEnd;
    Calendar startTime;
    Calendar endTime;

    private TextView title;
    private TextView generateStartDate;
    private TextView endDate;
    User currentUser;
    User otherUser;

    Calendar dateChosen;
    Calendar dateEnded;

    private int indexOfWorkHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_time);

        //initialize objects
        displayStart = (TextView)findViewById(R.id.startTime);
        displayEnd = (TextView) findViewById(R.id.endTime);
        generateStartDate = (TextView) findViewById(R.id.generatedStartDate);
        endDate = (TextView) findViewById(R.id.endDate);
        title = (TextView) findViewById(R.id.currentDate);

        //initialize start and end times
        startTime = Calendar.getInstance(); // change to calendar date given
        startTime.set(Calendar.HOUR_OF_DAY,0);
        startTime.set(Calendar.MINUTE,0);
        startTime.set(Calendar.SECOND,0);
        startTime.set(Calendar.MILLISECOND,0);
        displayStart.setText(String.format("%02d:%02d", startTime.get(Calendar.HOUR_OF_DAY), startTime.get(Calendar.MINUTE)));

        endTime = Calendar.getInstance();
        endTime.set(Calendar.HOUR_OF_DAY,0);
        endTime.set(Calendar.MINUTE,0);
        endTime.set(Calendar.SECOND,0);
        endTime.set(Calendar.MILLISECOND,0);
        displayEnd.setText(String.format("%02d:%02d", endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE)));

        // getting currentUser and otherUser
        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        otherUser = (User)i.getSerializableExtra("otherUser");
        dateChosen = (Calendar)i.getSerializableExtra("dateChosen");

        // initialize start and end dates
        title.setText(String.format("%d/%d", dateChosen.get(Calendar.MONTH)+1, dateChosen.get(Calendar.DAY_OF_MONTH)) + " SCHEDULE");
        generateStartDate.setText(String.format("%d-%d-%d", dateChosen.get(Calendar.MONTH)+1, dateChosen.get(Calendar.DAY_OF_MONTH), dateChosen.get(Calendar.YEAR)));
        endDate.setText(String.format("%d-%d-%d", dateChosen.get(Calendar.MONTH)+1, dateChosen.get(Calendar.DAY_OF_MONTH), dateChosen.get(Calendar.YEAR)));


        // create dayViewCalendar objects
        dayView = (CalendarDayView) findViewById(R.id.calendar);
        dayView.setLimitTime(0, 24);

        ///////////
        /// Below is the code that displays the tutor's work hours from date chosen
        ///////////
        events = new ArrayList<>();
        EventView ev = new EventView(this);
        for(int j = 0; j < otherUser.getWorkHours().size(); j++){

            int eventColor = ContextCompat.getColor(this, R.color.colorPrimaryDark);
            String start[] = otherUser.getWorkHours().get(j).getStartTime().split(":");
            Calendar timeStart = Calendar.getInstance();
            timeStart.set(Calendar.HOUR_OF_DAY, Integer.parseInt(start[0]));
            timeStart.set(Calendar.MINUTE, Integer.parseInt(start[1]));
            String end[] = otherUser.getWorkHours().get(j).getEndTime().split(":");
            Calendar timeEnd = (Calendar) timeStart.clone();
            timeEnd.set(Calendar.HOUR_OF_DAY, Integer.parseInt(end[0]));
            timeEnd.set(Calendar.MINUTE, Integer.parseInt(end[1]));
            Event event = new Event(1, timeStart, timeEnd, "", "", eventColor);
            events.add(event);

        }


        ///////////
        /// Below is the code that displays the tutor's appointments from date chosen
        ///////////
        popups = new ArrayList<>();

        // TODO - get ALL of user's appointments from the given day (later: to the date specified in endDate) from DATABASE
        // TODO - TELL ME WHEN YOU'RE WORKING ON THISSS
        // how to get current Calendar date (months are from 0-11)
        // year = dateChosen.get(Calendar.YEAR), month = dateChosen.get(Calendar.MONTH), day = dateChosen.get(DAY_OF_MONTH)
        // currentUser = tutor, so getting tutor's email: currentUser.getEmail()
        // I made some phony appointments TEHEE
        Calendar s = Calendar.getInstance();
        Calendar e = (Calendar) s.clone();
        otherUser.getAppointments().clear();
        Appointment one = new Appointment();
        s.set(Calendar.HOUR_OF_DAY, 7);
        s.set(Calendar.MINUTE, 0);
        e.set(Calendar.HOUR_OF_DAY, 10);
        e.set(Calendar.MINUTE, 0);
        one.setStartTime(s);
        one.setEndTime(e);
        otherUser.addNewAppointment(one);
        Calendar s1 = Calendar.getInstance();
        Calendar e1 = (Calendar) s1.clone();
        Appointment two = new Appointment();
        s1.set(Calendar.HOUR_OF_DAY, 22);
        s1.set(Calendar.MINUTE, 30);
        e1.set(Calendar.HOUR_OF_DAY, 23);
        e1.set(Calendar.MINUTE, 30);
        two.setStartTime(s1);
        two.setEndTime(e1);
        otherUser.addNewAppointment(two);
        // erase until here


        for(int k = 0; k < otherUser.getAppointments().size(); k++){
            Calendar timeStart = Calendar.getInstance();
            timeStart.set(Calendar.HOUR_OF_DAY, otherUser.getAppointments().get(k).getStartTime().get(Calendar.HOUR_OF_DAY));
            timeStart.set(Calendar.MINUTE, otherUser.getAppointments().get(k).getStartTime().get(Calendar.MINUTE));
            Calendar timeEnd = (Calendar) timeStart.clone();
            timeEnd.set(Calendar.HOUR_OF_DAY, otherUser.getAppointments().get(k).getEndTime().get(Calendar.HOUR_OF_DAY));
            timeEnd.set(Calendar.MINUTE, otherUser.getAppointments().get(k).getEndTime().get(Calendar.MINUTE));

            PopUp popup = new PopUp();
            popup.setStartTime(timeStart);
            popup.setEndTime(timeEnd);
            popup.setImageStart("");
            popup.setTitle("Appointment Scheduled");
            popup.setDescription(String.format("%02d:%02d to %02d:%02d", otherUser.getAppointments().get(k).getStartTime().get(Calendar.HOUR_OF_DAY), otherUser.getAppointments().get(k).getStartTime().get(Calendar.MINUTE), otherUser.getAppointments().get(k).getEndTime().get(Calendar.HOUR_OF_DAY), otherUser.getAppointments().get(k).getEndTime().get(Calendar.MINUTE)));
            popup.isAutohide();
            popups.add(popup);
        }

        // add all tutor's appointments and hours in
        dayView.setEvents(events);
        dayView.setPopups(popups);
    }

    public void AddTime(View v){
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog timePickerDialog = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(AppointmentTimePicker.this,
                startTime.get(startTime.HOUR_OF_DAY),
                startTime.get(startTime.MINUTE),
                true);
        timePickerDialog.setTitle("TimePicker");
        timePickerDialog.show(getFragmentManager(), "TimePicker");
        startOrEnd = 0;

    }

    public void EndTime(View v){
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog timePickerDialog = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(AppointmentTimePicker.this,
                endTime.get(endTime.HOUR_OF_DAY),
                endTime.get(endTime.MINUTE),
                true);
        timePickerDialog.setTitle("TimePicker");
        timePickerDialog.show(getFragmentManager(), "TimePicker");
        startOrEnd = 1;
    }

    public void EndDate(View v){
        Toast.makeText(getApplicationContext(), "Ending Date" , Toast.LENGTH_SHORT).show();


    }

    public void SetTimes(View v){
        // get seconds
        long totalTime = (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000;

        if(totalTime <= 0){
            // try again
            Toast.makeText(getApplicationContext(), "Invalid time. Please try again." , Toast.LENGTH_SHORT).show();
        }
        else if(isWithinConflicts() == true){
            // return time start, time end
            Intent returnIntent = new Intent();

            String start = String.format("%02d:%02d", startTime.get(Calendar.HOUR_OF_DAY), startTime.get(Calendar.MINUTE));
            String end = String.format("%02d:%02d", endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE));

            returnIntent.putExtra("startTime", start);
            returnIntent.putExtra("endTime", end);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        if (startOrEnd == 0){
            displayStart.setText(String.format("%02d:%02d", hourOfDay, minute));

            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
            startTime.set(Calendar.MINUTE,minute);
            startTime.set(Calendar.SECOND,0);
            startTime.set(Calendar.MILLISECOND,0);
        }else{
            displayEnd.setText(String.format("%02d:%02d", hourOfDay, minute));

            endTime = Calendar.getInstance();
            endTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
            endTime.set(Calendar.MINUTE,minute);
            endTime.set(Calendar.SECOND,0);
            endTime.set(Calendar.MILLISECOND,0);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // set selected date in the Date Output for visual purposes
        endDate.setText(String.format("%d-%d-%d", monthOfYear+1, dayOfMonth, year));

        // update current user's chosen date
        dateEnded = Calendar.getInstance();
        dateEnded.set(Calendar.MONTH, monthOfYear);
        dateEnded.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateEnded.set(Calendar.YEAR, year);

        Toast.makeText(getApplicationContext(), String.format("%d-%d-%d", dateEnded.get(Calendar.MONTH)+1, dateEnded.get(Calendar.DAY_OF_MONTH), dateEnded.get(Calendar.YEAR)), Toast.LENGTH_LONG).show();
    }

    /**
     *
     * @return true if there are no conflicts, false is there is
     */
    public boolean isWithinConflicts(){
        indexOfWorkHour = 0;
        if(isWithinTutorAppointments() && isWithinTutorHours() == true){
            return true;
        }
        return false;
    }

    /**
     * Check with all appointments a tutor has during the specified day(s).
     * @return
     */
    public boolean isWithinTutorAppointments(){
        // from DATABASE FETCH: get other user's appointments within a specified day(s)
        for(int i = 0; i < otherUser.getAppointments().size(); i++){
//                    Toast.makeText(getApplicationContext(), "Iteration of j = " + j  , Toast.LENGTH_SHORT).show();
            String start = String.format("%02d:%02d:00", otherUser.getAppointments().get(i).getStartTime().get(Calendar.HOUR_OF_DAY), otherUser.getAppointments().get(i).getStartTime().get(Calendar.MINUTE));
            String end = String.format("%02d:%02d:00", otherUser.getAppointments().get(i).getEndTime().get(Calendar.HOUR_OF_DAY), otherUser.getAppointments().get(i).getEndTime().get(Calendar.MINUTE));

            if(isInAppointments(start, end) == true){
                // in this case, if it is within the time, then false because it is conflicting.
                Toast.makeText(getApplicationContext(), "Error: Desired times conflict with existing tutor appointments." , Toast.LENGTH_SHORT).show();
                return false;
            }
        }

//        for(int i = 0; i < otherUser.getWorkHours().size(); i++){
////            Toast.makeText(getApplicationContext(), "Iteration of i = " + i  , Toast.LENGTH_SHORT).show();
//
//            if(isInTime(otherUser.getWorkHours().get(i).getStartTime(), otherUser.getWorkHours().get(i).getEndTime()) == true){
//                for(int j = 0; j < otherUser.getAppointments().size(); j++){
////                    Toast.makeText(getApplicationContext(), "Iteration of j = " + j  , Toast.LENGTH_SHORT).show();
//                    String start = String.format("%02d:%02d:00", otherUser.getAppointments().get(i).getStartTime().get(Calendar.HOUR_OF_DAY), otherUser.getAppointments().get(i).getStartTime().get(Calendar.MINUTE));
//                    String end = String.format("%02d:%02d:00", otherUser.getAppointments().get(i).getEndTime().get(Calendar.HOUR_OF_DAY), otherUser.getAppointments().get(i).getEndTime().get(Calendar.MINUTE));
//                    if(isInAppointments(start, end) == true){
//                        // in this case, if it is within the time, then false because it is conflicting.
//                        Toast.makeText(getApplicationContext(), "Error: Desired times conflict with existing tutor appointments." , Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                }
//            }
//
//        }

        return true;
    }

    /**
     * Check with all work hours a tutor has during the specified day(s).
     * @return
     */
    public boolean isWithinTutorHours(){
        // TODO - DATABASE FETCH: get other user's work hours within a specified day(s)
        for(int i = 0; i < otherUser.getWorkHours().size(); i++){
            if(isInTime(otherUser.getWorkHours().get(i).getStartTime(), otherUser.getWorkHours().get(i).getEndTime()) == true){
                indexOfWorkHour = i;
                return true;
            }

        }
        Toast.makeText(getApplicationContext(), "Error: Desired times conflict with tutor's working hours." , Toast.LENGTH_SHORT).show();
        return false;
    }

    public boolean isInTime(String tutorStartTime, String tutorEndTime){

        try {
            String string1;
            if(tutorStartTime.length() == 8){ string1 = tutorStartTime; }
            else{ string1= tutorStartTime+":00"; }
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            String string2;
            if(tutorEndTime.length() == 8){string2 = tutorEndTime; }
            else{ string2 = tutorEndTime+":00"; }
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);

            String startString = startTime.get(Calendar.HOUR_OF_DAY)+":"+startTime.get(Calendar.MINUTE)+":00";
            Date start = new SimpleDateFormat("HH:mm:ss").parse(startString);
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(start);

            String endString = endTime.get(Calendar.HOUR_OF_DAY)+":"+endTime.get(Calendar.MINUTE)+":00";
            Date end = new SimpleDateFormat("HH:mm:ss").parse(endString);
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(end);

            Date x = startCal.getTime();
            Date y = endCal.getTime();

            if ((x.after(calendar1.getTime()) || x.equals(calendar1.getTime())) && (y.before(calendar2.getTime()) || y.equals(calendar2.getTime())) ) {
//                Toast.makeText(getApplicationContext(), "Good" , Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
//                Toast.makeText(getApplicationContext(), "Bad" , Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * checking the desired time with the appointments
     * @param tutorStartTime
     * @param tutorEndTime
     * @return
     */
    public boolean isInAppointments(String tutorStartTime, String tutorEndTime){
        try {
            String string1;
            if(tutorStartTime.length() == 8){ string1 = tutorStartTime; }
            else{ string1= tutorStartTime+":00"; }
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            String string2;
            if(tutorEndTime.length() == 8){string2 = tutorEndTime; }
            else{ string2 = tutorEndTime+":00"; }
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);

            String startString = startTime.get(Calendar.HOUR_OF_DAY)+":"+startTime.get(Calendar.MINUTE)+":00";
            Date start = new SimpleDateFormat("HH:mm:ss").parse(startString);
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(start);

            String endString = endTime.get(Calendar.HOUR_OF_DAY)+":"+endTime.get(Calendar.MINUTE)+":00";
            Date end = new SimpleDateFormat("HH:mm:ss").parse(endString);
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(end);

            //check beginning time of appointment
            if( (start.after(calendar1.getTime()) || start.equals(calendar1.getTime())) && (start.before(calendar2.getTime()) || start.equals(calendar2.getTime())) ){
                return true;
            }
            // check ending time of appointment
            if( (end.after(calendar1.getTime()) || end.equals(calendar1.getTime())) && (end.before(calendar2.getTime()) || end.equals(calendar2.getTime())) ){
                return true;
            }

            return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public String getTimeDiff(Date dateOne, Date dateTwo) {
        String diff = "";
        long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
        diff = String.format("%d hour(s) %d min(s)", TimeUnit.MILLISECONDS.toHours(timeDiff),
                TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
        return diff;
    }
}
