package edu.depaul.csc595.jarvis.reminders.staticreminders.services;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Advait on 01-03-2016.
 */
public class MyAlarmService extends Service

{

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}