package edu.depaul.csc595.jarvis.reminders.staticreminders.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.reminders.staticreminders.services.MyReceiver;

/**
 * Created by Advait on 01-03-2016.
 */
public class ReminderFireExtinguisher extends Activity
{

    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_for_fire_extinguisher);

        Calendar calendar = Calendar.getInstance();
        //set notification for date --> 1th February 2016 at 09:00:00


        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.DAY_OF_MONTH, 01);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.AM);

        Intent myIntent = new Intent(ReminderFireExtinguisher.this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(ReminderFireExtinguisher.this, 0, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        //For Every 30 Days
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY*30, pendingIntent);
        //For Every 5 Min
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY*30, pendingIntent);

    }
}
