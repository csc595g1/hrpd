package edu.depaul.csc595.jarvis.reminders.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.depaul.csc595.jarvis.reminders.utils.NotificationUtil;

public class DismissReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationUtil.cancelNotification(context, intent.getIntExtra("NOTIFICATION_ID", 0));
    }
}
