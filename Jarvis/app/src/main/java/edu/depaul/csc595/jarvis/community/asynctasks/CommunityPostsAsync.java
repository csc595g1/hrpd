package edu.depaul.csc595.jarvis.community.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import edu.depaul.csc595.jarvis.community.CommunityBoardActivity;
import edu.depaul.csc595.jarvis.community.CommunityBoardFragment;
import edu.depaul.csc595.jarvis.community.adapters.MainCommunityPostAdapter;
import edu.depaul.csc595.jarvis.community.models.CommunityPostMainModel;

/**
 * Created by Ed on 3/10/2016.
 */
public class CommunityPostsAsync extends AsyncTask<Object,Void,List<CommunityPostMainModel>> {

    private Context context;
    private CommunityBoardActivity activity;
    //private MainCommunityPostAdapter adapter;
    private RecyclerView recyclerView;
    private final String baseURL = "https://jarvis-services.herokuapp.com/services/community/getPosts";
    private CommunityBoardFragment frag;

    public interface ListListener{
        public abstract void getList(List<CommunityPostMainModel> list);
    }

    private  ListListener listener;

    protected void onPreExecute(){
        super.onPreExecute();
    }


    //needs: 1. Context, 2. CommunityBoardActivity, 3. recyclerview
    protected List<CommunityPostMainModel> doInBackground(Object... params) {
        List<CommunityPostMainModel> modelList = new ArrayList<>();
        //set params
        if(params[0] instanceof Context){
            context = (Context) params[0];
        }

        if(params[1] instanceof CommunityBoardActivity){
            activity = (CommunityBoardActivity) params[1];
        }

        if(params[2] instanceof RecyclerView){
            recyclerView = (RecyclerView)params[2];
        }

        if(params[3] instanceof CommunityBoardFragment){
            frag = (CommunityBoardFragment)params[3];
        }

        try {
            this.listener = (ListListener) frag;
        } catch (final ClassCastException e) {
            // throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }

        try{
            URL url = new URL(baseURL);
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
                modelList = CommunityPostMainModel.parseGetCommunityPostList(sb.toString());
            }
        }catch(MalformedURLException e){
            Log.d("getCommPostAsync", "doInBackground Malformed URL Exception caught.");
            return null;
        }
        catch(IOException e){
            Log.d("getCommPostAsync", "doInBackground IO Exception caught.");
            return null;
        }

        return modelList;
    }

    protected void onPostExecute(List<CommunityPostMainModel> modelList){
        if(recyclerView != null){
            MainCommunityPostAdapter adapter = new MainCommunityPostAdapter(modelList,context,activity);
            recyclerView.setAdapter(adapter);
            if(listener != null) {
                Log.d("postsasync", "onPostExecute listener is null");
                listener.getList(modelList);
            }
        }
    }
}
