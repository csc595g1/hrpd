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
import edu.depaul.csc595.jarvis.detection.RegisterDeviceToken;

/**
 * TokenUpdateIntentService.java
 * Jarvis
 */
public class TokenUpdateIntentService extends IntentService {
    private static final String TAG = "TokenUpdateIntentService";
    public static final String SENT_UPDATED_TOKEN_TO_SERVER = "sentTokenToServer";

    public TokenUpdateIntentService() {
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
            sendUpdatedTokenToServer(token);
            Log.d(TAG, "SenderID: " + senderId);
            Log.d(TAG, "GCM Registration Token: " + token);
        } catch (IOException e) {
            e.printStackTrace();

            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(SENT_UPDATED_TOKEN_TO_SERVER, false).apply();
        }
        // Overwrite GCM_TOKEN.
        sharedPreferences.edit().putString(TokenIntentService.GCM_TOKEN, token).apply();
    }
    private void sendUpdatedTokenToServer(String token) {
        String webServiceUrl = "https://detectionservices.herokuapp.com/update_gcm_token";
        new RegisterDeviceToken(TokenUpdateIntentService.this).execute("", token, webServiceUrl);
    }
}
