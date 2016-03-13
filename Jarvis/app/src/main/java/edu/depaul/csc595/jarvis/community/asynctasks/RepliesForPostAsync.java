package edu.depaul.csc595.jarvis.community.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import edu.depaul.csc595.jarvis.community.CommunityPostDetailsActivity;
import edu.depaul.csc595.jarvis.community.adapters.CommunityRepliesAdapter;
import edu.depaul.csc595.jarvis.community.models.CommunityPostMainModel;
import edu.depaul.csc595.jarvis.community.models.CommunityReplyModel;

/**
 * Created by Ed on 3/12/2016.
 * //needs context, CommunityPostDetailsActivity, recyclerView,string(post_id) ,textview
 */
public class RepliesForPostAsync extends AsyncTask<Object,Void,List<CommunityReplyModel>> {
    private Context context;
    private CommunityPostDetailsActivity activity;
    private RecyclerView recyclerView;
    private String post_id;
    private TextView no_content;
    private final String baseUrl = "https://jarvis-services.herokuapp.com/services/community/replies?post_id=";


    protected void onPreExecute(){
        super.onPreExecute();
    }

    protected List<CommunityReplyModel> doInBackground(Object... params) {
        Log.d("getCommReplyAsync", "doInBackground async called for replies");
        List<CommunityReplyModel> list = new ArrayList<>();
        //get params...
        if(params[0] instanceof Context){
            context = (Context) params[0];
        }

        if(params[1] instanceof CommunityPostDetailsActivity){
            activity = (CommunityPostDetailsActivity) params[1];
        }

        if(params[2] instanceof RecyclerView){
            recyclerView = (RecyclerView)params[2];
        }

        if(params[3] instanceof String){
            post_id = (String) params[3];
            Log.d("getCommReplyAsync", "doInBackground post id is " + post_id);
        }

        if(params[4] instanceof TextView){
            no_content = (TextView) params[4];
        }

        try{
            URL url = new URL(baseUrl + post_id);
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
                Log.d("getcommreplyasync", "doInBackground " + sb.toString());
                list = CommunityReplyModel.parseGetCommunityReplyList(sb.toString());
            }
            else return null;
        }catch(MalformedURLException e){
            Log.d("getCommReplyAsync", "doInBackground Malformed URL Exception caught.");
            return null;
        }
        catch(IOException e){
            Log.d("getCommReplyAsync", "doInBackground IO Exception caught.");
            return null;
        }
        return list;
    }

    protected void onPostExecute(List<CommunityReplyModel> modelList){
        if(modelList == null){
            Log.d("getCommReplyAsync", "doInBackground modellistisnull");
            no_content.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else{
            //...
            if(recyclerView != null){
                CommunityRepliesAdapter adapter = new CommunityRepliesAdapter(modelList,context,activity);
                recyclerView.setAdapter(adapter);
                recyclerView.refreshDrawableState();
            }
        }
    }
}
