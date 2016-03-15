package edu.depaul.csc595.jarvis.community.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import edu.depaul.csc595.jarvis.community.models.CommunityReplyModel;

/**
 * Created by Ed on 3/13/2016.
 */
public class SendReplyAsync extends AsyncTask<Object,Void,Void>{
    private final String TAG = "SendReplyAsync";
    private CommunityReplyModel model;
    private JSONObject outjson;
    private final String urlString = "https://jarvis-services.herokuapp.com/services/community/reply";

    protected void onPreExecute(){
        super.onPreExecute();
    }

    protected Void doInBackground(Object... params) {
        //post_id, email, content
        if(params[0] instanceof CommunityReplyModel){
            model = (CommunityReplyModel)params[0];
        }

        try {
            outjson = new JSONObject();
            outjson.put("post_id", Integer.parseInt(model.post_id));
            outjson.put("email",model.email);
            outjson.put("content",model.content);

            Log.d(TAG, "doInBackground " + outjson.toString());

            URL url = new URL(urlString);
            HttpsURLConnection secureConnection = (HttpsURLConnection) url.openConnection();
            secureConnection.setDoOutput(true);
            secureConnection.setRequestProperty("Content-Type", "application/json");
            secureConnection.setRequestProperty("Accept", "application/json");
            secureConnection.setRequestMethod("POST");
            secureConnection.setChunkedStreamingMode(0);

            OutputStreamWriter out = new OutputStreamWriter(secureConnection.getOutputStream());
            out.write(outjson.toString());
            out.close();

            Log.d(TAG, "doInBackground response Code: " + secureConnection.getResponseCode());
        }
        catch(MalformedURLException e){
            return null;
        }
        catch(IOException e){
            return null;
        }
        catch(JSONException e){
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result){}
}
