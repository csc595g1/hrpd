package edu.depaul.csc595.jarvis.profile.user;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;

/**
 * Created by Ed on 2/20/2016.
 */
public class GoogleImage extends AsyncTask<Object,Void,Bitmap> {
    private ImageView bmImage;
    private ProgressDialog progressBar;

    protected void onPreExecute(){
        super.onPreExecute();
    }

    protected Bitmap doInBackground(Object... params) {
        if(params[0] instanceof ImageView){
            bmImage = (ImageView)params[0];
        }
        else if(params[0] instanceof ProgressDialog){
            progressBar = (ProgressDialog)params[0];
        }
        if(params.length > 1) {
            if (params[1] != null && params[1] instanceof ProgressDialog) {
                progressBar = (ProgressDialog) params[1];
            }
        }
        String url = UserInfo.getInstance().getGoogleAccount().getPhotoUrl().toString();
        Bitmap bm1 = null;
        try{
            InputStream in = new java.net.URL(url).openStream();
            bm1 = BitmapFactory.decodeStream(in);

        }
        catch(Exception e){}
        return bm1;
    }

    protected void onPostExecute(Bitmap result){
        if(progressBar != null){
            progressBar.dismiss();
        }
        bmImage.setImageBitmap(result);
        UserInfo.getInstance().setGoogleProfileImage(bmImage);
        UserInfo.getInstance().setGoogleProfileBitMap(result);
        notify();
    }
}
