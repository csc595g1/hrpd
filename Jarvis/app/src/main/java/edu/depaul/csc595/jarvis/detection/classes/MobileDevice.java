package edu.depaul.csc595.jarvis.detection.classes;

/**
 * Created by uchennafokoye on 2/28/16.
 */
public class MobileDevice {
    public String id;
    public String gcm_token;

    public MobileDevice(String id, String gcm_token) {
        this.id = id;
        this.gcm_token = gcm_token;
    }

    @Override
    public String toString() {
        return "GCM Token: " + gcm_token;
    }
}
