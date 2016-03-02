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
import edu.depaul.csc595.jarvis.reminders.staticreminders.rewardgenerate.ReceiveRewards;

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

    @SuppressWarnings("static-access")
    public static void notificationForDryer(Context mContext){

        Intent intent=new Intent(mContext, ReceiveRewards.class);

        int icon = R.mipmap.ic_launcher;

        int mNotificationId = 001;

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
                .setContentTitle("Dryer Reminder")
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.InboxStyle())
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.doc_lv_tp_2))
                .setContentText("You need to check your Dryer").build();


        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }

    @SuppressWarnings("static-access")
    public static void notificationForCODetector(Context mContext){

        Intent intent=new Intent(mContext, ReceiveRewards.class);

        int icon = R.mipmap.ic_launcher;

        int mNotificationId = 002;

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
                .setContentTitle("CO Detector Reminder")
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.InboxStyle())
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.doc_lv_tp_2))
                .setContentText("You need to check your CO Detector").build();


        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }

    @SuppressWarnings("static-access")
    public static void notificationForDryerVent(Context mContext){

        Intent intent=new Intent(mContext, ReceiveRewards.class);

        int icon = R.mipmap.ic_launcher;

        int mNotificationId = 003;

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
                .setContentTitle("Dryer Vent Reminder")
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.InboxStyle())
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.doc_lv_tp_2))
                .setContentText("You need to check your Dryer Vent").build();


        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }

    @SuppressWarnings("static-access")
    public static void notificationForFireExting(Context mContext){

        Intent intent=new Intent(mContext, ReceiveRewards.class);

        int icon = R.mipmap.ic_launcher;

        int mNotificationId = 004;

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
                .setContentTitle("Fire Extinguisher Reminder")
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.InboxStyle())
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.doc_lv_tp_2))
                .setContentText("You need to check your Fire Extinguisher").build();


        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }

    @SuppressWarnings("static-access")
    public static void notificationForGenerator(Context mContext){

        Intent intent=new Intent(mContext, ReceiveRewards.class);

        int icon = R.mipmap.ic_launcher;

        int mNotificationId = 005;

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
                .setContentTitle("Generator Reminder")
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.InboxStyle())
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.doc_lv_tp_2))
                .setContentText("You need to check your Generator").build();


        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }

    @SuppressWarnings("static-access")
    public static void notificationForSmokeAlarm(Context mContext){

        Intent intent=new Intent(mContext, ReceiveRewards.class);

        int icon = R.mipmap.ic_launcher;

        int mNotificationId = 006;

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
                .setContentTitle("Smoke Alarm Reminder")
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.InboxStyle())
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.doc_lv_tp_2))
                .setContentText("You need to check your Smoke Alarm").build();


        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }

    @SuppressWarnings("static-access")
    public static void notificationForSumppump(Context mContext){

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

    @SuppressWarnings("static-access")
    public static void notificationForWashMach(Context mContext){

        Intent intent=new Intent(mContext, ReceiveRewards.class);

        int icon = R.mipmap.ic_launcher;

        int mNotificationId = 010;

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
                .setContentTitle("Washing Machine Reminder")
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.InboxStyle())
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.doc_lv_tp_2))
                .setContentText("You need to check your Washing Machine").build();


        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }

    @SuppressWarnings("static-access")
    public static void notificationForWaterHeater(Context mContext){

        Intent intent=new Intent(mContext, ReceiveRewards.class);

        int icon = R.mipmap.ic_launcher;

        int mNotificationId = 011;

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
                .setContentTitle("Water Heater Reminder")
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.InboxStyle())
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.doc_lv_tp_2))
                .setContentText("You need to check your Water Heater").build();


        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }
}
