package edu.depaul.csc595.jarvis.rewards.HerokuAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import edu.depaul.csc595.jarvis.main.adapters.MainCardViewAdapter;
import edu.depaul.csc595.jarvis.profile.ProfileFragment;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;

/**
 * Created by Ed on 2/29/2016.
 */
public class GetTotalPointsAsyncTask extends AsyncTask<Object, Void, Integer> {

    private final String TAG = "GetTotalPointsAsync";
    private final String baseURL = "https://jarvis-services.herokuapp.com/services/rewards/totalpoints?email=";
    private ProfileFragment profileFragment;
    private TextView points_tv;
    private MainCardViewAdapter.CardViewRewardsHolder rewardsHolder;

    protected void onPreExecute(){super.onPreExecute();}

    protected Integer doInBackground(Object... params){

        String email;
        JSONObject jobj;

        if(UserInfo.getInstance().isGoogleLoggedIn()){
            email = UserInfo.getInstance().getGoogleAccount().getEmail();
        }
        else if(UserInfo.getInstance().getIsLoggedIn()){
            email = UserInfo.getInstance().getCredentials().getEmail();
        }
        else{
            return null;
        }

        //do any params setting here....
        if(params.length > 0){
            if(params[0] instanceof ProfileFragment) {
                profileFragment = (ProfileFragment) params[0];
            }
            else if(params[0]instanceof MainCardViewAdapter.CardViewRewardsHolder){
                rewardsHolder = (MainCardViewAdapter.CardViewRewardsHolder) params[0];
            }
            if(params.length > 1){
                if(params[1] instanceof TextView){
                    points_tv = (TextView) params[1];
                }
            }
        }

        //get points...
        try{
            URL url = new URL(baseURL + email);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("GET");
            connection.setChunkedStreamingMode(0);

            StringBuilder sb = new StringBuilder();
            int response = connection.getResponseCode();
            if(response == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                jobj = new JSONObject(sb.toString());
                return jobj.getInt("sum");
            }
        }
        catch(MalformedURLException e){
            Log.d(TAG, "doInBackground error, returning 0");
            Log.d(TAG, "doInBackground " + e.getMessage());
            return 0;
        }
        catch(IOException e){
            Log.d(TAG, "doInBackground error, returning 0");
            Log.d(TAG, "doInBackground " + e.getMessage());
            return 0;
        }
        catch(JSONException e){
            Log.d(TAG, "doInBackground error, returning 0");
            Log.d(TAG, "doInBackground " + e.getMessage());
            return 0;
        }

        return null;
    }

    protected void onPostExecute(Integer result){
        if(profileFragment != null){
            if(points_tv != null){
                points_tv.setText(Integer.toString(result));
                //profileFragment.
            }
        }
        else if (rewardsHolder != null){
            rewardsHolder.tv_content.setText(Integer.toString(result));
        }
    }
}
