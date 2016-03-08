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

import edu.depaul.csc595.jarvis.rewards.Models.RewardOrderModel;

/**
 * Created by markwilhelm on 3/1/16.
 */
public class RewardOrderAsyncTask  extends AsyncTask<Object,Void,Void> {
    final String TAG = "RewardOrderAsyncTask";

    final String orderUrl = "https://jarvis-services.herokuapp.com/services/rewards/order";

    Activity activity;
    RewardOrderModel rewardOrderModel;

    protected void onPreExecute(){super.onPreExecute();}

    protected Void doInBackground(Object... params){

        if(params[0].getClass().getSuperclass() == Activity.class){
            activity = (Activity)params[0];
        }
        else if (params[0] instanceof RewardOrderModel){
            rewardOrderModel = (RewardOrderModel)params[0];
        }

        if(params.length > 1) {
            if (params[1] instanceof RewardOrderModel) {
                rewardOrderModel = (RewardOrderModel) params[1];
            }
        }
        try {
            JSONObject jsonOutput = new JSONObject();

            jsonOutput = rewardOrderModel.toJSON();

            Log.d(TAG, "doInBackground " + jsonOutput);

            URL url = new URL(orderUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//            conn.setRequestProperty("Authorization", "Basic Q29ubmVjdGVkSG9tZVRlc3Q6OVp2a0F0THQyQmt6QUtYdHlidU1sTVh4QjJ3SVpMWmNWQXJIU0d3cTJXWEVoZldmTkNmc0VFaXlv");

            conn.setRequestMethod("POST");
            conn.setChunkedStreamingMode(0);

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(jsonOutput.toString());
            out.close();

            StringBuilder sb = new StringBuilder();
            int httpResult = conn.getResponseCode();
            if(httpResult == HttpsURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while((line = br.readLine()) != null){
                    sb.append(line);
                }
                JSONObject input =  new JSONObject(sb.toString());
                //for testing in app. comment out for production
                if(activity != null){
                    Toast.makeText(activity.getBaseContext(), "Order sent!", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (JSONException | IOException e) {
//        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(Void result){

    }

}
