package edu.depaul.csc595.jarvis.detection.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import edu.depaul.csc595.jarvis.R;

/**
 * TokenIntentService.java
 * Jarvis
 */
public class TokenIntentService extends IntentService {
    private static final String TAG = "TokenIntentService";
    public static final String GCM_TOKEN = "gcmToken";
    public static final String GCM_OLD_TOKEN = "gcmOldToken";

    public TokenIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("handle", "onHandleIntent");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Fetch token here
        // Make a call to Instance API
        InstanceID instanceID = InstanceID.getInstance(this);
        String senderId = getResources().getString(R.string.gcm_defaultSenderId);
        String token = "";
        try {
            // Request token that will be used by the server to send push notifications
            token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            Log.d(TAG, "SenderID: " + senderId);
            Log.d(TAG, "GCM Registration Token: " + token);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to generate token", e);
        }
        // Save token
        sharedPreferences.edit().putString(GCM_TOKEN, token).apply();

    }
}
