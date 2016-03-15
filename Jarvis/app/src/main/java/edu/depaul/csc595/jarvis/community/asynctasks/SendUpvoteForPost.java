package edu.depaul.csc595.jarvis.community.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ed on 3/13/2016.
 */
public class SendUpvoteForPost extends AsyncTask<Object,Void,Void>{
    private String email,post_id;
    private final String baseURL = "https://jarvis-services.herokuapp.com/services/community/postupvote";
    protected void onPreExecute(){super.onPreExecute();}
    protected Void doInBackground(Object... params) {

        if(params[0] instanceof String){
            email = (String)params[0];
        }

        if(params[1] instanceof String){
            post_id = (String)params[1];
        }
        try{
        String finalurl = baseURL + "?post_id=" + post_id+"&email=" + email;
        URL url = new URL(finalurl);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        connection.setChunkedStreamingMode(0);
        int response = connection.getResponseCode();
        Log.d("getcommreplyasync", "doInBackground response code " + response);
        }
        catch(MalformedURLException e){
        Log.d("getCommReplyAsync", "doInBackground Malformed URL Exception caught.");
        }
        catch(IOException e){
        Log.d("getCommReplyAsync", "doInBackground IO Exception caught.");
        }
        //catch (JSONException e){}
        return null;
    }
    protected void onPostExecute(){}
}
