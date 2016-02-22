package edu.depaul.csc595.jarvis.detection.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * JarvisInstanceIdListenerService.java
 * Jarvis
 */
public class JarvisInstanceIdListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        // Fetch updated InstanceID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, TokenUpdateIntentService.class);
        startService(intent);
    }
}
