package edu.depaul.csc595.jarvis.reminders.staticreminders.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.reminders.main.CustomReminderActivity;
import edu.depaul.csc595.jarvis.reminders.staticreminders.receivers.ReceiverForWashingMachine;

/**
 * Created by Advait on 01-03-2016.
 */
public class ReminderWashingMachine extends AppCompatActivity
{
    private PendingIntent pendingIntent;
    Button go_to_custom_reminder;
    TextView reminderDate;
    int month, day, year;
    Calendar cal2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_for_washing_machine);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                Intent go_activity = new Intent(getApplicationContext(), CustomReminderActivity.class);
                startActivity(go_activity);
            }
        });

        final Calendar calendar = Calendar.getInstance();
        //set notification for date --> 1th February 2016 at 09:00:00
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.DAY_OF_MONTH, 16);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.PM);

        Intent myIntent = new Intent(ReminderWashingMachine.this, ReceiverForWashingMachine.class);
        pendingIntent = PendingIntent.getBroadcast(ReminderWashingMachine.this, 0, myIntent, 0);
        final AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        //For Every 30 Days
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY*30, pendingIntent);
        //For Every 5 Min
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),90000, pendingIntent);

        /*
        Code for Next Alarm Date
         */
        cal2 = Calendar.getInstance();
        month = cal2.MONTH;
        day = cal2.get(Calendar.DATE);
        year = cal2.YEAR;

        cal2.set(Calendar.YEAR,2016);

        reminderDate = (TextView) findViewById(R.id.appliances_washing_machine_reminder_date);

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
        final RadioButton after_30_days = (RadioButton) findViewById(R.id.reminder_one_month);
        final RadioButton after_3_months = (RadioButton) findViewById(R.id.reminder_three_month);
        final RadioButton after_6_months = (RadioButton) findViewById(R.id.reminder_six_month);
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

                    reminderDate.setText("Next Reminder is : 03 /" + (day + 1) + "/ 2016");
                }
                else if(checkedId == R.id.reminder_one_month)
                {
                    Toast.makeText(getApplicationContext(),"30 Days",Toast.LENGTH_SHORT).show();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 30, pendingIntent);

                    if(month < 8)
                    {
                        reminderDate.setText("Next Reminder is : " + (month + 2) + "/ 01 /" + "2016");
                    }
                    else
                    {
                        reminderDate.setText("Next Reminder is : " + (month + 2) + "/ 01 /" + "2016");
                    }
                }
                else if(checkedId == R.id.reminder_three_month)
                {
                    Toast.makeText(getApplicationContext(),"After 3 Months",Toast.LENGTH_SHORT).show();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 90, pendingIntent);

                    if(month < 8)
                    {
                        reminderDate.setText("Next Reminder is : " + (month + 4) + "/ 01 /" + "2016");
                    }
                    else
                    {
                        reminderDate.setText("Next Reminder is : " + (month + 4) + "/ 01 /" + "2016");
                    }
                }
                else if(checkedId == R.id.reminder_six_month)
                {
                    Toast.makeText(getApplicationContext(),"180 Days",Toast.LENGTH_SHORT).show();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 180, pendingIntent);

                    if(month < 8)
                    {
                        reminderDate.setText("Next Reminder is : " + (month + 7) + "/ 01 /" + "2016");
                    }
                    else
                    {
                        reminderDate.setText("Next Reminder is : " + (month + 7) + "/ 01 /" + "2016");
                    }
                }
                else if(checkedId == R.id.reminder_one_year)
                {
                    Toast.makeText(getApplicationContext(),"365 Days",Toast.LENGTH_SHORT).show();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 365, pendingIntent);

                    reminderDate.setText("Next Reminder is : " + "03" + "/ 01 /" + "2017");
                }
            }
        });

        go_to_custom_reminder = (Button) findViewById(R.id.link_for_custom_reminder);
        go_to_custom_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_activity = new Intent(getApplicationContext(), CustomReminderActivity.class);
                startActivity(go_activity);
            }
        });

    }

}
