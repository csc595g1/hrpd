package edu.depaul.csc595.jarvis.community.asynctasks;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.community.CommunityBoardActivity;

/**
 * Created by Ed on 3/13/2016.
 */
public class CheckUserUpvotedPostAsync extends AsyncTask<Object,Void,Boolean> {

    //what we need
    private ImageView img;
    private String email;
    private String post_id;
    private CommunityBoardActivity activity;

    //others..
    private final String baseUrl = "https://jarvis-services.herokuapp.com/services/community/userupvoted";

    protected void onPreExecute(){super.onPreExecute();}

    protected Boolean doInBackground(Object... params) {
        Boolean hasUpvoted = false;
        if(params[0] instanceof ImageView){
            img = (ImageView)params[0];
        }
        if(params[1] instanceof String){
            email = (String) params[1];
        }
        if(params[2] instanceof String){
            post_id = (String) params[2];
        }

        if(params[3] instanceof CommunityBoardActivity){
            activity = (CommunityBoardActivity)params[3];
        }

        try{
            String finalurl = baseUrl + "?post_id=" + post_id+"&email=" + email;
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
                    Log.d("getcommreplyasync", "doInBackground " + sb.toString());
                }
                JSONObject obj = new JSONObject(sb.toString());
                hasUpvoted = obj.getBoolean("hasUserUpvoted");
                Log.d("getcommreplyasync", "doInBackground hasupvoted " + hasUpvoted);
                return Boolean.valueOf(hasUpvoted);
                //Log.d("getcommreplyasync", "doInBackgroundshould have returned ");
            }
        }catch(MalformedURLException e){
            Log.d("getCommReplyAsync", "doInBackground Malformed URL Exception caught.");
            return false;
        }
        catch(IOException e){
            Log.d("getCommReplyAsync", "doInBackground IO Exception caught.");
            return false;
        }
        catch (JSONException e){return false;}
        Log.d("getcommreplyasync", "doInBackground Line 80 got here somehow");
        return false;
    }

    protected void onPostExecute(Boolean condition){
        Log.d("getCommReplyAsync", "onPostExecute " + condition);
        if(condition){
            //img =(ImageView) activity.getSupportFragmentManager().findFragmentById(R.id.community_board_fragment).getView().findViewById(R.id.post_frag_up_vote);
            img.setColorFilter(Color.GREEN);
            img.setClickable(false);
            //img.refreshDrawableState();
            //img.notifyAll();
        }
    }
}
