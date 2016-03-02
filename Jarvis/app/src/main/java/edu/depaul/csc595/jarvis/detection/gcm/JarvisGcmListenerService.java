package edu.depaul.csc595.jarvis.detection.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Calendar;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.main.MainActivity;
import edu.depaul.csc595.jarvis.reminders.main.CustomReminderActivity;
import edu.depaul.csc595.jarvis.reminders.builder.ReminderBuilder;
import edu.depaul.csc595.jarvis.reminders.database.DatabaseHelper;
import edu.depaul.csc595.jarvis.reminders.models.Reminder;
import edu.depaul.csc595.jarvis.reminders.utils.DateAndTimeUtil;

/**
 * JarvisGcmListenerService.java
 * Jarvis
 */
public class JarvisGcmListenerService extends GcmListenerService {
    private static final int MESSAGE_NOTIFICATION_ID = 12345;
    private static final String TAG = "JarvisGcmListenerService";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String appliance_name = data.getString("appliance_name");
        Boolean has_appliance = false;
        if (appliance_name != null){
            createReminder(appliance_name, message);
            has_appliance = true;
        }
        Log.d(TAG, "Got message from GCM");
        Log.d(TAG, "Message = " + message);
        createNotification(from, message, has_appliance);
        Log.d(TAG, " " + appliance_name);

    }

    //Creates a reminder
    private void createReminder(String appliance, String message){
        String title = "Detection for: " + appliance;
        String content = message + ": " + appliance;
        Reminder reminder = ReminderBuilder.buildBasicReminder(this);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.HOUR_OF_DAY, 24);
        reminder.setTitle(title);
        reminder.setContent(content);
        reminder.setDateAndTime(DateAndTimeUtil.toStringDate(mCalendar) + DateAndTimeUtil.toStringTime(mCalendar));
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        databaseHelper.addNotification(reminder);
        databaseHelper.close();
    }

    // Creates notification based on title and body received
    private void createNotification(String title, String body, Boolean hasAppliance) {
        Context context = getBaseContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title)
                .setContentText(body)
                .setOngoing(true)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{0, 1000});

        Class goToClass = (hasAppliance) ? CustomReminderActivity.class : MainActivity.class;
        // Trigger MainActivity when the user clicks on the notification.
        PendingIntent jarvisIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, goToClass), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(jarvisIntent);

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }
}
