package edu.depaul.csc595.jarvis.detection.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.main.MainActivity;

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
        Log.d(TAG, "Got message from GCM");
        Log.d(TAG, "Message = " + message);
        createNotification(from, message);
    }

    // Creates notification based on title and body received
    private void createNotification(String title, String body) {
        Context context = getBaseContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title)
                .setContentText(body)
                .setOngoing(true)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[] {0, 1000});

        // Trigger MainActivity when the user clicks on the notification.
        PendingIntent jarvisIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(jarvisIntent);

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }
}
