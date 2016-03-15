package edu.depaul.csc595.jarvis.detection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

import edu.depaul.csc595.jarvis.detection.gcm.TokenIntentService;
import edu.depaul.csc595.jarvis.main.MainActivity;

/**
 * RegisterDeviceToken.java
 * Jarvis
 */
public class RegisterDeviceToken extends AsyncTask<String, Double, String> {
    private final String TAG = "RegisterDeviceToken Async Task";
    private String result;
    private Context context;
    public RegisterDeviceToken(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        JSONObject jsonObject = new JSONObject();
        String email_address = params[0];
        String token = params[1];
        String webServiceUrl = params[2];
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String oldToken = sharedPreferences.getString(TokenIntentService.GCM_OLD_TOKEN, "");
        try {
            jsonObject.put("email_address", email_address);
            jsonObject.put("token", token);
            jsonObject.put("new_token", token);
            jsonObject.put("old_token", oldToken);
            Log.d(TAG, "JSON: " + jsonObject.toString());
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        try {

                URL url = new URL(webServiceUrl);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");


                urlConnection.getOutputStream().write(jsonObject.toString().getBytes());

                sharedPreferences.edit().putString(TokenIntentService.GCM_OLD_TOKEN, token).apply();
                urlConnection.getInputStream();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // OK
                    Log.d(TAG, "Successfully connected to the webservice!");
                    result = "Success";
                }
                else {
                    Log.d(TAG, "Error connecting to the webservice!");
                    result = "Error";
                }
        }
        catch (Exception e) {
            result = "Exception";
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("Success")) {
            Toast.makeText(context, "This device has been registered to receive alerts from your smart products!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Error Registering Device! Please Try Again.", Toast.LENGTH_SHORT).show();
        }
    }
}
