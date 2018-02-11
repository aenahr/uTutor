package ututor.edu.csulb.ututor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;


public class SampleActivity extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener{
    Button datePicker;
    int countDays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_layout);


        datePicker = (Button) findViewById(R.id.bDate);

        datePicker.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();

                com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        SampleActivity.this
                    , now.get(Calendar.YEAR)
                    , now.get(Calendar.MONTH)
                    , now.get(Calendar.DAY_OF_MONTH));



                datePickerDialog.setMinDate(Calendar.getInstance());

//                GregorianCalendar g1=new GregorianCalendar();
//                g1.add(Calendar.DATE, 1);
//                GregorianCalendar gc = new GregorianCalendar();
//                gc.add(Calendar.DAY_OF_MONTH, 30);
//                List<Calendar> dayslist= new LinkedList<Calendar>();
//                Calendar[] daysArray;
//                Calendar cAux = Calendar.getInstance();
//                while ( cAux.getTimeInMillis() <= gc.getTimeInMillis()) {
//                    if (cAux.get(Calendar.DAY_OF_WEEK) != 1) {
//                        Calendar c = Calendar.getInstance();
//                        c.setTimeInMillis(cAux.getTimeInMillis());
//                        dayslist.add(c);
//                    }
//                    cAux.setTimeInMillis(cAux.getTimeInMillis() + (24*60*60*1000));
//                }
//                daysArray = new Calendar[dayslist.size()];
//                for (int i = 0; i<daysArray.length;i++)
//                {
//                    daysArray[i]=dayslist.get(i);
//                }
//                datePickerDialog.setSelectableDays(daysArray);



                datePickerDialog.setTitle("Date Picker");
                datePickerDialog.show(getFragmentManager(), "DatePicker");
            }
        });
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(getApplicationContext(), String.format("You selected: : %d/%d/%d", monthOfYear+1,dayOfMonth,year) , Toast.LENGTH_SHORT).show();
    }

    public Calendar[] getCalendarDays(int day){
        GregorianCalendar g1=new GregorianCalendar();
        g1.add(Calendar.DATE, 1);
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DAY_OF_MONTH, 30);
        List<Calendar> dayslist= new LinkedList<Calendar>();
        Calendar[] daysArray;
        Calendar cAux = Calendar.getInstance();
        while ( cAux.getTimeInMillis() <= gc.getTimeInMillis()) {
            if (cAux.get(Calendar.DAY_OF_WEEK) != 1) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(cAux.getTimeInMillis());
                dayslist.add(c);
            }
            cAux.setTimeInMillis(cAux.getTimeInMillis() + (24*60*60*1000));
        }
        daysArray = new Calendar[dayslist.size()];
        //add to total counter
        countDays = countDays +dayslist.size();

        for (int i = 0; i<daysArray.length;i++)
        {
            daysArray[i]=dayslist.get(i);
        }

        return daysArray;
    }
}
