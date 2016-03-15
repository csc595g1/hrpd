package edu.depaul.csc595.jarvis.community.asynctasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import edu.depaul.csc595.jarvis.community.models.CommunityReplyModel;

/**
 * Created by Ed on 3/13/2016.
 */
public class GetUpvoteCountAsync extends AsyncTask<Object,Void,Integer>{

    private TextView upvotecounttv;
    private String post_id;
    private int count;
    private final String baseURL = "https://jarvis-services.herokuapp.com/services/community/upvotespost";
    protected void onPreExecute(){super.onPreExecute();}

    protected Integer doInBackground(Object... params) {
        count = 0;
        if(params[0] instanceof TextView){
            upvotecounttv = (TextView)params[0];
        }
        if(params[1] instanceof String){
            post_id = (String) params[1];
        }
        try{
        String finalurl = baseURL + "?post_id=" + post_id;
        URL url = new URL(finalurl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("GET");
            connection.setChunkedStreamingMode(0);
            StringBuilder sb = new StringBuilder();
            int response = connection.getResponseCode();
            Log.d("getcommreplyasync", "doInBackground response code " + response);
            if(response == HttpsURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                while((line = br.readLine()) != null){
                    sb.append(line);
                }
                JSONObject obj = new JSONObject(sb.toString());
                count = obj.getInt("count");
                Log.d("upvotecount", "doInBackground " + count);
                return Integer.valueOf(count);
            }
        }catch(MalformedURLException e){
            Log.d("getCommReplyAsync", "doInBackground Malformed URL Exception caught.");
            return 0;
        }
        catch(IOException e){
            Log.d("getCommReplyAsync", "doInBackground IO Exception caught.");
            return 0;
        }
        catch (JSONException e){return 0;}
        return 0;
    }

    protected void onPostExecute(Integer count){
        Integer converted = Integer.valueOf(count);
        upvotecounttv.setText(String.valueOf(converted));
    }
}
