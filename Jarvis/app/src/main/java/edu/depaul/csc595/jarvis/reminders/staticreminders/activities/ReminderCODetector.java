package edu.depaul.csc595.jarvis.reminders.staticreminders.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.reminders.staticreminders.receivers.ReceiverForCODetector;

/**
 * Created by Advait on 02-03-2016.
 */
public class ReminderCODetector extends AppCompatActivity
{

    private PendingIntent pendingIntent;
    TextView reminderDate;
    int month;
    Calendar cal2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_for_co_detector);

        final Calendar calendar = Calendar.getInstance();
        //set notification for date --> 1th February 2016 at 09:00:00
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.DAY_OF_MONTH, 02);
        calendar.set(Calendar.HOUR_OF_DAY, 04);
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.AM);

        Intent myIntent = new Intent(ReminderCODetector.this, ReceiverForCODetector.class);
        pendingIntent = PendingIntent.getBroadcast(ReminderCODetector.this, 0, myIntent, 0);
        final AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        //For Every 30 Days
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY*30, pendingIntent);
        //For Every 5 Min
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),60000, pendingIntent);

        cal2 = Calendar.getInstance();
        month = cal2.MONTH;
        reminderDate = (TextView) findViewById(R.id.reminder_date_co);

        if(month < 8)
        {
            reminderDate.setText("Next Reminder is : 01 / 0" + (month+2) + " / 2016");
        }
        else
        {
            reminderDate.setText("Next Reminder is : 01 / " + (month+2) + " / 2016");
        }

        /* Initialize Radio Group and attach click handler */
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        final RadioButton after_1_day = (RadioButton) findViewById(R.id.reminder_every_day);
        final RadioButton after_7_days = (RadioButton) findViewById(R.id.reminder_one_week);
        final RadioButton after_15_days = (RadioButton) findViewById(R.id.reminder_fifteen_days);
        final RadioButton after_30_days = (RadioButton) findViewById(R.id.reminder_one_month);
        final RadioButton after_3_months = (RadioButton) findViewById(R.id.reminder_three_month);
        final RadioButton after_6_months = (RadioButton) findViewById(R.id.reminder_six_month);
        final RadioButton after_9_months = (RadioButton) findViewById(R.id.reminder_nine_month);
        final RadioButton after_12_months = (RadioButton) findViewById(R.id.reminder_one_year);

        radioGroup.clearCheck();

        /* Attach CheckedChangeListener to radio group */

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if(checkedId == R.id.reminder_every_day)
                {
                    Toast.makeText(getApplicationContext(), "Daily", Toast.LENGTH_SHORT).show();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 1, pendingIntent);
                }
                else if(checkedId == R.id.reminder_one_week)
                {
                    Toast.makeText(getApplicationContext(),"07 Days",Toast.LENGTH_SHORT).show();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 07, pendingIntent);
                }
                else if(checkedId == R.id.reminder_fifteen_days)
                {
                    Toast.makeText(getApplicationContext(),"15 Days",Toast.LENGTH_SHORT).show();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 15, pendingIntent);
                }
                else if(checkedId == R.id.reminder_one_month)
                {
                    Toast.makeText(getApplicationContext(),"30 Days",Toast.LENGTH_SHORT).show();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 30, pendingIntent);
                }
                else if(checkedId == R.id.reminder_three_month)
                {
                    Toast.makeText(getApplicationContext(),"90 Days",Toast.LENGTH_SHORT).show();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 90, pendingIntent);
                }
                else if(checkedId == R.id.reminder_six_month)
                {
                    Toast.makeText(getApplicationContext(),"180 Days",Toast.LENGTH_SHORT).show();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 180, pendingIntent);
                }
                else if(checkedId == R.id.reminder_nine_month)
                {
                    Toast.makeText(getApplicationContext(),"270 Days",Toast.LENGTH_SHORT).show();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 270, pendingIntent);
                }
                else if(checkedId == R.id.reminder_one_year)
                {
                    Toast.makeText(getApplicationContext(),"365 Days",Toast.LENGTH_SHORT).show();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 365, pendingIntent);
                }
            }
        });

    }
}
