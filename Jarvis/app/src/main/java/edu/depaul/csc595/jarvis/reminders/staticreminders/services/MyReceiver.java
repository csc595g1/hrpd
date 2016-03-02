package edu.depaul.csc595.jarvis.reminders.staticreminders.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderFireExtinguisher;

/**
 * Created by Advait on 01-03-2016.
 */
public class MyReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
		/*Intent service1 = new Intent(context, MyAlarmService.class);
	     context.startService(service1);*/
        Log.i("App", "called receiver method");
        try
        {
            Utils.generateNotification(context);
            if(ReminderFireExtinguisher.ACCESSIBILITY_SERVICE == "")
            {

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
