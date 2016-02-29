package edu.depaul.csc595.jarvis.reminders.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.depaul.csc595.jarvis.reminders.database.DatabaseHelper;
import edu.depaul.csc595.jarvis.reminders.models.Reminder;
import edu.depaul.csc595.jarvis.reminders.utils.NotificationUtil;

public class SnoozeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseHelper database = DatabaseHelper.getInstance(context);
        int reminderId = intent.getIntExtra("NOTIFICATION_ID", 0);
        if (reminderId != 0) {
            Reminder reminder = database.getNotification(reminderId);
            NotificationUtil.createNotification(context, reminder);
        }
        database.close();
    }
}