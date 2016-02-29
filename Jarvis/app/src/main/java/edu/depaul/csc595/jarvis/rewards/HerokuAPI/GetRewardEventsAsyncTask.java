package edu.depaul.csc595.jarvis.rewards.HerokuAPI;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;


import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import edu.depaul.csc595.jarvis.profile.ProfileActivity;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.rewards.Models.RewardBalanceModel;
import edu.depaul.csc595.jarvis.rewards.adapters.RewardBalanceAdapter;

/**
 * Created by Ed on 2/28/2016.
 */
public class GetRewardEventsAsyncTask extends AsyncTask<Object,Void,List<RewardBalanceModel>> {
    private final String TAG = "GetRewardAsync";
    private final String baseURL = "https://jarvis-services.herokuapp.com/services/rewards/getevents?email=";
    private List<RewardBalanceModel> eventList;
    private ProgressDialog dialog;
    private RecyclerView recyclerView;

    protected void onPreExecute(){super.onPreExecute();}

    protected List<RewardBalanceModel> doInBackground(Object... params){
        String email;
        JsonArray jarray;
        if(UserInfo.getInstance().isGoogleLoggedIn()){
            email = UserInfo.getInstance().getGoogleAccount().getEmail();
        }
        else if(UserInfo.getInstance().getIsLoggedIn()){
            email = UserInfo.getInstance().getCredentials().getEmail();
        }
        else{
            return null;
        }

        if(params.length > 0){
            if(params[0] instanceof List){
                eventList = (List<RewardBalanceModel>) params[0];

                if(params.length > 1){
                    if(params[1] instanceof ProgressDialog){
                        dialog = (ProgressDialog) params[1];
                    }

                    if(params.length > 2){
                        recyclerView = (RecyclerView) params[2];
                    }
                }
            }
        }

        try {
            URL url = new URL(baseURL + email);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("GET");
            connection.setChunkedStreamingMode(0);

            StringBuilder sb = new StringBuilder();
            int response = connection.getResponseCode();
            if(response == HttpsURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                while((line = br.readLine()) != null){
                    sb.append(line);
                }
                JsonParser parser = new JsonParser();
                jarray = parser.parse(sb.toString()).getAsJsonObject().getAsJsonArray("events");
                eventList = new ArrayList<RewardBalanceModel>();
                for(int i = 0; i < jarray.size(); i++){
                    JsonObject obj = jarray.get(i).getAsJsonObject();
                    Log.d(TAG, "doInBackground " + obj);
                    RewardBalanceModel model = new RewardBalanceModel();
                    model.eventId = obj.get("eventId").getAsString();  //obj.getString("eventId");
                    model.eventCatrgory = obj.get("eventCategory").getAsString();//obj.getString("eventCategory");
                    model.title = obj.get("title").getAsString();//obj.getString("title");
                    model.units = obj.get("units").getAsInt();//obj.getInt("units");
                    model.dttm = obj.get("dttm").getAsString();//obj.getString("dttm");
                    eventList.add(model);
                }
            }

            return eventList;
        }
        catch(MalformedURLException e){
            Log.d(TAG, "doInBackground Malformed URL Exception caught.");
            return null;
        }
        catch(IOException e){
            Log.d(TAG, "doInBackground IO Exception caught.");
            return null;
        }
//        catch (JSONException e){
//            Log.d(TAG, "doInBackground JSON Exception caught.");
//            Log.d(TAG, "doInBackground " + e.getMessage());
//            //Log.d(TAG, e.printStackTrace().);
//            return null;
//        }
    }
    protected void onPostExecute(List<RewardBalanceModel> results){

        if(recyclerView != null){
            RewardBalanceAdapter adapter = new RewardBalanceAdapter(results);
            recyclerView.setAdapter(adapter);
        }

        if(dialog != null){
            dialog.dismiss();
        }
    }
}
