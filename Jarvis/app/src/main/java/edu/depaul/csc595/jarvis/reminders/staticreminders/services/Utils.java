package edu.depaul.csc595.jarvis.reminders.staticreminders.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.reminders.staticreminders.receiving.ReceiveRewards;

/**
 * Created by Advait on 01-03-2016.
 */
public class Utils {

    @SuppressWarnings("static-access")
    public static void generateNotification(Context mContext){

        Intent intent=new Intent(mContext, ReceiveRewards.class);

        int icon = R.mipmap.ic_launcher;

        int mNotificationId = 007;

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext);
        Notification notification = mBuilder.setSmallIcon(icon).setTicker("Icon").setWhen(0)
                .setAutoCancel(true)
                .setContentTitle("Sump Pump Reminder")
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.InboxStyle())
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.doc_lv_tp_2))
                .setContentText("You need to check your Sump Pump").build();


        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }

}
