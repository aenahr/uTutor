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
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import android.widget.TimePicker;


import java.util.ArrayList;
import java.util.Calendar;


public class AppointmentTimePicker extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener{

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

    private TextView generateStartDate;
    User currentUser;
    User otherUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_time);

        //initialize objects
        displayStart = (TextView)findViewById(R.id.startTime);
        displayEnd = (TextView) findViewById(R.id.endTime);
        generateStartDate = (TextView) findViewById(R.id.generatedStartDate);

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

        ///////////
        // TODO get other user's date
        Intent i = getIntent();
        currentUser = (User)i.getSerializableExtra("currentUser");
        otherUser = (User)i.getSerializableExtra("otherUser");

        // THIS IS FINAL - start Date of Appointment
        //TODO get current date chosen by user from previous activity



        dayView = (CalendarDayView) findViewById(R.id.calendar);
        dayView.setLimitTime(0, 23);

//        ((CdvDecorationDefault) (dayView.getDecoration())).setOnEventClickListener(
//                new EventView.OnEventClickListener() {
//                    @Override
//                    public void onEventClick(EventView view, IEvent data) {
//                        Log.e("TAG", "onEventClick:" + data.getName());
//                    }
//
//                    @Override
//                    public void onEventViewClick(View view, EventView eventView, IEvent data) {
//                        Log.e("TAG", "onEventViewClick:" + data.getName());
//                        if (data instanceof Event) {
//                            // change event (ex: set event color)
//                            dayView.setEvents(events);
//                        }
//                    }
//                });
//
//        ((CdvDecorationDefault) (dayView.getDecoration())).setOnPopupClickListener(
//                new PopupView.OnEventPopupClickListener() {
//                    @Override
//                    public void onPopupClick(PopupView view, IPopup data) {
//                        Log.e("TAG", "onPopupClick:" + data.getTitle());
//                    }
//
//                    @Override
//                    public void onQuoteClick(PopupView view, IPopup data) {
//                        Log.e("TAG", "onQuoteClick:" + data.getTitle());
//                    }
//
//                    @Override
//                    public void onLoadData(PopupView view, ImageView start, ImageView end,
//                                           IPopup data) {
//                        start.setImageResource(R.drawable.bacon);
//                    }
//                });

        events = new ArrayList<>();
        EventView ev = new EventView(this);

        // events = time slots during that day
        {
            int eventColor = ContextCompat.getColor(this, R.color.colorPrimaryDark);
            Calendar timeStart = Calendar.getInstance();
            timeStart.set(Calendar.HOUR_OF_DAY, 7);
            timeStart.set(Calendar.MINUTE, 0);
            Calendar timeEnd = (Calendar) timeStart.clone();
            timeEnd.set(Calendar.HOUR_OF_DAY, 12);
            timeEnd.set(Calendar.MINUTE, 30);
            Event event = new Event(1, timeStart, timeEnd, "", "", eventColor);

            events.add(event);
        }

        {
            int eventColor = ContextCompat.getColor(this, R.color.colorPrimaryDark);
            Calendar timeStart = Calendar.getInstance();
            timeStart.set(Calendar.HOUR_OF_DAY, 20);
            timeStart.set(Calendar.MINUTE, 0);
            Calendar timeEnd = (Calendar) timeStart.clone();
            timeEnd.set(Calendar.HOUR_OF_DAY, 23);
            timeEnd.set(Calendar.MINUTE, 30);
            Event event = new Event(1, timeStart, timeEnd, "", "", eventColor);

            events.add(event);
        }

        popups = new ArrayList<>();

        // any scheduled appointments
        {
            Calendar timeStart = Calendar.getInstance();
            timeStart.set(Calendar.HOUR_OF_DAY, 12);
            timeStart.set(Calendar.MINUTE, 0);
            Calendar timeEnd = (Calendar) timeStart.clone();
            timeEnd.set(Calendar.HOUR_OF_DAY, 13);
            timeEnd.set(Calendar.MINUTE, 30);


            PopUp popup = new PopUp();
            popup.setStartTime(timeStart);
            popup.setEndTime(timeEnd);
            popup.setImageStart("");
            popup.setTitle("event 1 with title");
            popup.setDescription("Yuong alsdf");
            popup.isAutohide();
            popups.add(popup);
        }

        {
            Calendar timeStart = Calendar.getInstance();
            timeStart.set(Calendar.HOUR_OF_DAY, 20);
            timeStart.set(Calendar.MINUTE, 0);
            Calendar timeEnd = (Calendar) timeStart.clone();
            timeEnd.set(Calendar.HOUR_OF_DAY, 22);
            timeEnd.set(Calendar.MINUTE, 0);

            PopUp popup = new PopUp();
            popup.setStartTime(timeStart);
            popup.setEndTime(timeEnd);
            popup.setImageStart("");
            popup.setTitle("Time Blocked");
            popup.setDescription("");
            popups.add(popup);
        }

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
                startTime.get(startTime.HOUR_OF_DAY),
                startTime.get(startTime.MINUTE),
                true);
        timePickerDialog.setTitle("TimePicker");
        timePickerDialog.show(getFragmentManager(), "TimePicker");
        startOrEnd = 1;
    }

    public void EndDate(View v){
        Toast.makeText(getApplicationContext(), "Ending Date" , Toast.LENGTH_SHORT).show();


    }

    public void CheckInput(View v){

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", "HelloWorld");
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

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
}
