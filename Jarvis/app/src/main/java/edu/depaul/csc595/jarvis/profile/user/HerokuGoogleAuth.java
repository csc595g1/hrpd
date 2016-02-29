package edu.depaul.csc595.jarvis.profile.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import edu.depaul.csc595.jarvis.main.MainActivity;
import edu.depaul.csc595.jarvis.main.SplashActivity;
import edu.depaul.csc595.jarvis.profile.LogInActivity;
import edu.depaul.csc595.jarvis.profile.ProfileActivity;

/**
 * Created by Ed on 2/10/2016.
 */
public class HerokuGoogleAuth extends AsyncTask<Object, Void, Void> {
    final String TAG = "GoogleHerokuLogin";
    LogInActivity logInActivity;
    //SplashActivity splashActivity;
    UserInfo userInfo;
    private final String URL_STRING = "https://jarvis-services.herokuapp.com/services/login/googlelogin";
    HttpsURLConnection secureConnection;
    JSONObject outJson, inJson;
    URL url;
    InputStream in;
    OutputStreamWriter out;
    boolean auth;
    String authMessage;
    private ProgressDialog m_ProgressDialog = null;
    private Context context;
    private ProgressDialog dialog;
    private SplashActivity splashActivity;

    protected void onPreExecute(){
        super.onPreExecute();
    }

    protected Void doInBackground(Object... params) {
        if(params[0] != null){
            if(params[0] instanceof ProgressDialog){
                dialog = (ProgressDialog) params[0];
            }
            else if(params[0] instanceof LogInActivity){
                logInActivity = (LogInActivity) params[0];
            }
        }

        if(params.length > 1){
            if(params[1] instanceof SplashActivity){
                splashActivity = (SplashActivity) params[1];
            }
        }

        Log.d(TAG,"Entering async task for log in");
        try{
            Log.d(TAG,UserInfo.getInstance().getGoogleAccount().getEmail() + " " + UserInfo.getInstance().getGoogleAccount().getDisplayName());
            outJson = new JSONObject();
            outJson.put("email",UserInfo.getInstance().getGoogleAccount().getEmail());
            outJson.put("name",UserInfo.getInstance().getGoogleAccount().getDisplayName());

            Log.d(TAG, outJson.toString());

            url = new URL(URL_STRING);
            secureConnection = (HttpsURLConnection) url.openConnection();
            secureConnection.setDoOutput(true);
            secureConnection.setDoInput(true);
            secureConnection.setRequestProperty("Content-Type", "application/json");
            secureConnection.setRequestProperty("Accept", "application/json");
            secureConnection.setRequestMethod("POST");
            secureConnection.setChunkedStreamingMode(0);

            out = new OutputStreamWriter(secureConnection.getOutputStream());
            out.write(outJson.toString());
            out.close();

            StringBuilder sb = new StringBuilder();
            int httpResult = secureConnection.getResponseCode();
            Log.d(TAG,String.valueOf(httpResult));
            if(httpResult == HttpsURLConnection.HTTP_OK){
                Log.d(TAG,"200 OK");
                BufferedReader br = new BufferedReader(new InputStreamReader(secureConnection.getInputStream()));//need to put UTF-8?
                String line = null;
                while((line = br.readLine()) != null){
                    sb.append(line);
                    Log.d(TAG,line);
                }
                outJson = new JSONObject(sb.toString());
                // TODO: 2/11/2016 finish parsing JSON and making sure user is logged in.
                String tempAuth = outJson.getString("auth");
                auth = tempAuth.equals("true");
                authMessage = outJson.getString("message");
                Log.d(TAG, "Log in status: " + auth + " message: " + authMessage);
            }
        }
        catch(MalformedURLException e){
            //userInfo.setLoggedIn(false);
            Log.d(TAG, "doInBackground malformed exception");
            UserInfo.getInstance().signOutWithGoogle();
            return null;
        }
        catch(IOException e){
            Log.d(TAG, "doInBackground IOexception");
            UserInfo.getInstance().signOutWithGoogle();
            return null;
        }
        catch(JSONException e){
            Log.d(TAG, "doInBackground JSONException");
            UserInfo.getInstance().signOutWithGoogle();
            return null;
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        //for some reason, if data pass fails just null everything...may crash app depending when
        //this executes.

//        Intent intent = new Intent(splashActivity, MainActivity.class);
//        splashActivity.startActivity(intent);
//        splashActivity.finish();

        if(!auth){
            Log.d(TAG, "onPostExecute google auth with heroku failed. see log above for auth message");
            UserInfo.getInstance().signOutWithGoogle();
            Intent intent = new Intent(splashActivity, MainActivity.class);
            splashActivity.startActivity(intent);
            splashActivity.finish();
        }
        if(dialog != null){
            dialog.dismiss();
            Intent intent = new Intent(splashActivity, MainActivity.class);
            splashActivity.startActivity(intent);
            splashActivity.finish();
        }
        else if(logInActivity != null){
            Intent intent = new Intent(logInActivity,ProfileActivity.class);
            logInActivity.startActivity(intent);
            logInActivity.finish();
        }
        super.onPostExecute(result);
    }

}
