package edu.depaul.csc595.jarvis.rewards.HerokuAPI;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ed on 2/25/2016.
 */
public class CreateRewardEventAsyncTask extends AsyncTask<Object,Void,Void> {
final String TAG = "asynctaskcreatereward";
    Activity activity;
    CreateRewardEventModel model;
    final String eventurl = "https://jarvis-services.herokuapp.com/services/rewards/event";

    protected void onPreExecute(){super.onPreExecute();}

    protected Void doInBackground(Object... params){

        if(params[0].getClass().getSuperclass() == Activity.class){
            activity = (Activity)params[0];
        }
        else if (params[0] instanceof CreateRewardEventModel){
            model = (CreateRewardEventModel)params[0];
        }

        if(params.length > 1) {
            if (params[1] instanceof CreateRewardEventModel) {
                model = (CreateRewardEventModel) params[1];
            }
        }
        try {
            JSONObject output = new JSONObject();
            output.put("userId", model.getUserId());
            output.put("eventCategory",model.getEventCategory());
            output.put("units",model.getUnits());
            output.put("title",model.getTitle());
            //Log.d(TAG, "doInBackground " + );
            Log.d("rewardasync", "doInBackground " + output);
            URL url = new URL(eventurl);
            HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
            connect.setDoOutput(true);
            connect.setDoInput(true);
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("Accept", "application/json");
            connect.setRequestMethod("PUT");
            connect.setChunkedStreamingMode(0);

            OutputStreamWriter out = new OutputStreamWriter(connect.getOutputStream());
            out.write(output.toString());
            out.close();

            StringBuilder sb = new StringBuilder();
            int httpResult = connect.getResponseCode();
            if(httpResult == HttpsURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                String line = null;
                while((line = br.readLine()) != null){
                    sb.append(line);
                }
                JSONObject input =  new JSONObject(sb.toString());
                //for testing in app. comment out for production
                if(activity != null){
                    Toast.makeText(activity.getBaseContext(),"Reward sent!",Toast.LENGTH_SHORT).show();
                }
            }

        }
        catch (JSONException | IOException e){}

        return null;
    }

    protected void onPostExecute(Void result){

    }
}
