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

    LogInActivity logInActivity;
    SplashActivity splashActivity;
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

    protected void onPreExecute(){
        super.onPreExecute();
    }

    protected Void doInBackground(Object... params) {

        Log.d("HerokugoogleLogin","Entering async task for log in");
        try{
            Log.d("HerokuLogin",UserInfo.getInstance().getGoogleAccount().getEmail() + " " + UserInfo.getInstance().getGoogleAccount().getDisplayName());
            outJson = new JSONObject();
            outJson.put("email",UserInfo.getInstance().getGoogleAccount().getEmail());
            outJson.put("name",UserInfo.getInstance().getGoogleAccount().getDisplayName());

            Log.d("HerokuLogin", outJson.toString());

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
            Log.d("HerokugoogleLogin",String.valueOf(httpResult));
            if(httpResult == HttpsURLConnection.HTTP_OK){
                Log.d("HerokugoogleLogin","200 OK");
                BufferedReader br = new BufferedReader(new InputStreamReader(secureConnection.getInputStream()));//need to put UTF-8?
                String line = null;
                while((line = br.readLine()) != null){
                    sb.append(line);
                    Log.d("HerokugoogleLogin",line);
                }
                outJson = new JSONObject(sb.toString());
                // TODO: 2/11/2016 finish parsing JSON and making sure user is logged in.
                String tempAuth = outJson.getString("auth");
                auth = tempAuth.equals("true");
                authMessage = outJson.getString("message");
                Log.d("HerokugoogleLogin", "Log in status: " + auth + " message: " + authMessage);
            }
        }
        catch(MalformedURLException e){
            //userInfo.setLoggedIn(false);
            UserInfo.getInstance().signOutWithGoogle();
            return null;
        }
        catch(IOException e){
            UserInfo.getInstance().signOutWithGoogle();
            return null;
        }
        catch(JSONException e){
            UserInfo.getInstance().signOutWithGoogle();
            return null;
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        //for some reason, if data pass fails just null everything...may crash app depending when
        //this executes.
        if(!auth){
            Log.d("GoogleHeroku", "onPostExecute google auth with heroku failed. see log above for auth message");
            UserInfo.getInstance().signOutWithGoogle();
        }
        super.onPostExecute(result);
    }

}
