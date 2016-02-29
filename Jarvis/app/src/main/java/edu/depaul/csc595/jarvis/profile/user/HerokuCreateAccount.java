package edu.depaul.csc595.jarvis.profile.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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

import edu.depaul.csc595.jarvis.detection.RegisterDeviceToken;
import edu.depaul.csc595.jarvis.detection.gcm.TokenIntentService;
import edu.depaul.csc595.jarvis.profile.ProfileActivity;
import edu.depaul.csc595.jarvis.profile.SignUpActivity;

/**
 * Created by Ed on 2/16/2016.
 */
public class HerokuCreateAccount extends AsyncTask<Object, Void, Void> {
    SignUpActivity signUpActivity;
    UserInfo userInfo;
    private final String URL_STRING = "https://jarvis-services.herokuapp.com/services/createaccount";
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
            signUpActivity = (SignUpActivity) params[0];
        }
        if(params[1] != null){
            m_ProgressDialog = (ProgressDialog) params[1];
        }
        if(params[2] != null){
            context = (Context) params[2];
        }
        userInfo = UserInfo.getInstance();
        try {
            User user = userInfo.getCredentials();
            Log.d("HerokuCreate",userInfo.getFirstName() + " " + userInfo.getLastName() + " " + user.getEmail() + " " + user.getPw());
            outJson = new JSONObject();
            outJson.put("email",user.getEmail());
            outJson.put("pw",user.getPw());
            outJson.put("firstName",userInfo.getFirstName());
            outJson.put("lastName",userInfo.getLastName());

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
            Log.d("HerokuCreate",String.valueOf(httpResult));
            if(httpResult == HttpsURLConnection.HTTP_OK){
                Log.d("HerokuCreate","200 OK");
                BufferedReader br = new BufferedReader(new InputStreamReader(secureConnection.getInputStream()));//need to put UTF-8?
                String line = null;
                while((line = br.readLine()) != null){
                    sb.append(line);
                    Log.d("HerokuCreate",line);
                }
                outJson = new JSONObject(sb.toString());
                // TODO: 2/11/2016 finish parsing JSON and making sure user is logged in.
                String tempAuth = outJson.getString("accountCreated");
                auth = tempAuth.equals("true");
                //authMessage = outJson.getString("message");
                Log.d("HerokuLogin","Log in status: " + auth );
                // TODO: 2/11/2016 set auth and message to UserInfo and finish activity.
                userInfo.setLoggedIn(auth);
                if(auth) {
                    userInfo.setAuthMessage("account created");
                    userInfo.insertLocalInformationForLogin(context);
                }
                else{
                    userInfo.setAuthMessage("account creation failed");
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
        if(context != null) {
            UserInfo.getInstance().insertLocalInformationForLogin(context);
        }
        m_ProgressDialog.dismiss();
        if(userInfo.getIsLoggedIn()){
//            // Fetch the newly created user and his email.
//            User currentUser = userInfo.getCredentials();
//            String email_address = currentUser.getEmail();
//
//            // Fetch the device token.
//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(signUpActivity);
//            String token = sharedPreferences.getString(TokenIntentService.GCM_TOKEN, "");
//
//            // Set url for the web service
//            String webServiceUrl = "https://detectionservices.herokuapp.com/register_gcm_token";
//
//            // Register this device(token) on the server.
//            new RegisterDeviceToken(signUpActivity).execute(email_address, token, webServiceUrl);

            Intent intent = new Intent(signUpActivity.getBaseContext(),ProfileActivity.class);
            signUpActivity.startActivity(intent);
        }
        else{
            Toast.makeText(signUpActivity.getBaseContext(), "Account Creation Failed!", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(result);
    }
}
