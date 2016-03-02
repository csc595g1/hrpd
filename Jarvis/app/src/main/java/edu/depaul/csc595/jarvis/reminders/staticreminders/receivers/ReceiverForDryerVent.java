package edu.depaul.csc595.jarvis.reminders.staticreminders.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import edu.depaul.csc595.jarvis.reminders.staticreminders.services.Utils;

/**
 * Created by Advait on 02-03-2016.
 */
public class ReceiverForDryerVent extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
		/*Intent service1 = new Intent(context, MyAlarmService.class);
	     context.startService(service1);*/
        Log.i("App", "called receiver method");
        try
        {
            Utils.notificationForDryerVent(context);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}