package edu.depaul.csc595.jarvis.reminders.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Calendar;

import edu.depaul.csc595.jarvis.reminders.database.DatabaseHelper;
import edu.depaul.csc595.jarvis.reminders.models.Reminder;
import edu.depaul.csc595.jarvis.reminders.utils.AlarmUtil;
import edu.depaul.csc595.jarvis.reminders.utils.NotificationUtil;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseHelper database = DatabaseHelper.getInstance(context);
        Reminder reminder = database.getNotification(intent.getIntExtra("NOTIFICATION_ID", 0));
        reminder.setNumberShown(reminder.getNumberShown() + 1);
        database.updateNotification(reminder);

        NotificationUtil.createNotification(context, reminder);

        // Check if new alarm needs to be set
        if (reminder.getNumberToShow() > reminder.getNumberShown() || Boolean.parseBoolean(reminder.getForeverState())) {
            AlarmUtil.setNextAlarm(context, reminder, database, Calendar.getInstance());
        }

        // Update lists in tab fragments
        updateLists(context);
        database.close();
    }

    public void updateLists(Context context) {
        Intent intent = new Intent("BROADCAST_REFRESH");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}