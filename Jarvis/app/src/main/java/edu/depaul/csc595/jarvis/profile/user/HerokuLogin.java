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
public class HerokuLogin extends AsyncTask<Object, Void, Void> {

    LogInActivity logInActivity;
    SplashActivity splashActivity;
    UserInfo userInfo;
    private final String URL_STRING = "https://jarvis-services.herokuapp.com/services/login";
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

        Log.d("HerokuLogin","Entering async task for log in");

        if(params[0] != null) {
            Log.d("HerokuLogin", "param0 " + params[0]);
            if(params[0] instanceof LogInActivity) {
                logInActivity = (LogInActivity) params[0];
            }
            else if(params[0] instanceof SplashActivity){
                splashActivity = (SplashActivity) params[0];
            }
            else {
                //invalid or no activity passed
                UserInfo.getInstance().setLoggedIn(false);
                return null;
            }
        }
        //check instance type of param0 since that will dictate params going forward...
        if(params[0] instanceof LogInActivity) {
            if (params[1] != null) {
                m_ProgressDialog = (ProgressDialog) params[1];
            }
            if (params[2] != null) {
                context = (Context) params[2];
            }
        }
        else{
            //for splash activity passed, don't need progress dialog but will get user email and pw
            if(params[1] != null){
                context = (Context) params[1];
            }
        }
        userInfo = UserInfo.getInstance();
        User user = new User();
        try {
            if(params[0] instanceof LogInActivity) {
                user = userInfo.getCredentials();
            }
            else{
                //set credentials to use for splash
                user.setEmail((String) params[2]);
                user.setPw((String) params[3]);
            }
            Log.d("HerokuLogin",user.getEmail() + " " + user.getPw());
            outJson = new JSONObject();
            outJson.put("email",user.getEmail());
            outJson.put("pw",user.getPw());

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
            Log.d("HerokuLogin",String.valueOf(httpResult));
            if(httpResult == HttpsURLConnection.HTTP_OK){
                Log.d("HerokuLogin","200 OK");
                BufferedReader br = new BufferedReader(new InputStreamReader(secureConnection.getInputStream()));//need to put UTF-8?
                String line = null;
                while((line = br.readLine()) != null){
                    sb.append(line);
                    Log.d("HerokuLogin",line);
                }
                outJson = new JSONObject(sb.toString());
                // TODO: 2/11/2016 finish parsing JSON and making sure user is logged in.
                String tempAuth = outJson.getString("auth");
                auth = tempAuth.equals("true");
                authMessage = outJson.getString("message");
                Log.d("HerokuLogin", "Log in status: " + auth + " message: " + authMessage);
                // TODO: 2/11/2016 set auth and message to UserInfo and finish activity.
                userInfo.setLoggedIn(auth);
                userInfo.setAuthMessage(authMessage);
                if (!(params[0] instanceof SplashActivity)) {
                    userInfo.insertLocalInformationForLogin(context);
                }
                //if log in successful and this is from splash, set credentials in userinfo
                if(auth) {
                    if (params[0] instanceof SplashActivity) {
                        userInfo.setCredentials(user.getEmail(), user.getPw());
                        userInfo.insertLocalInformationForLogin(context);
                    }
                    //now that's a success, let's do one more call to the server get the user name
                    Log.d("HerokuLogin", "getting user first and last name");
                    //clear url var and reset it
                    url = null;
                    url = new URL("https://jarvis-services.herokuapp.com/services/login/getName?email=" + user.getEmail());
                    secureConnection = (HttpsURLConnection) url.openConnection();
                    secureConnection.setDoOutput(false);
                    secureConnection.setDoInput(true);
                    secureConnection.setRequestProperty("Content-Type", "application/json");
                    secureConnection.setRequestProperty("Accept", "application/json");
                    secureConnection.setRequestMethod("GET");
                    secureConnection.setChunkedStreamingMode(0);

                    sb = new StringBuilder();
                    httpResult = secureConnection.getResponseCode();
                    Log.d("HerokuLogin",String.valueOf(httpResult));
                    if(httpResult == HttpsURLConnection.HTTP_OK) {
                        Log.d("HerokuLogin", "200 OK");
                        br = new BufferedReader(new InputStreamReader(secureConnection.getInputStream()));//need to put UTF-8?
                        line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                            Log.d("HerokuLogin", line);
                        }
                        outJson = new JSONObject(sb.toString());
                        UserInfo.getInstance().setFirstName(outJson.getString("firstName"));
                        UserInfo.getInstance().setLastName(outJson.getString("lastName"));
                    }
                }
            }
        }
        catch(MalformedURLException e){
            userInfo.setLoggedIn(false);
            return null;
        }
        catch(IOException e){
            userInfo.setLoggedIn(false);
            return null;
        }
        catch(JSONException e){
            userInfo.setLoggedIn(false);
            return null;
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        //do stuff here
        userInfo = UserInfo.getInstance();
        //SystemClock.sleep(5000);
//        try {
//
//            wait(3000);
//        }
//        catch (InterruptedException e){}
        if(context != null) {
            UserInfo.getInstance().insertLocalInformationForLogin(context);
        }
        if(logInActivity != null) {
            m_ProgressDialog.dismiss();
        }
        if(userInfo.getIsLoggedIn()){
            if(logInActivity != null) {
                Intent intent = new Intent(logInActivity.getBaseContext(), ProfileActivity.class);
                logInActivity.startActivity(intent);
            }
            else if(splashActivity != null){
                Intent intent = new Intent(splashActivity.getBaseContext(), MainActivity.class);
                splashActivity.startActivity(intent);
                splashActivity.finish();
            }
        }
        else{
            if(logInActivity != null) {
                Toast.makeText(logInActivity.getBaseContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
            }
            else if (splashActivity != null){
                Intent intent = new Intent(splashActivity.getBaseContext(),MainActivity.class);
                splashActivity.startActivity(intent);
                splashActivity.finish();
            }
        }
        super.onPostExecute(result);
    }

}
