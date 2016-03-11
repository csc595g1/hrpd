package edu.depaul.csc595.jarvis.community.asynctasks;

import android.os.AsyncTask;

/**
 * Created by Ed on 3/10/2016.
 */
public class CommunityPostsAsync extends AsyncTask<Object,Void,Void> {

    protected void onPreExecute(){
        super.onPreExecute();
    }


    //needs: Context, CommunityBoardActivity, MainCommunityPostAdapter, recyclerview
    protected Void doInBackground(Object... params) {
        return null;
    }

    protected void onPostExecute(){}
}
