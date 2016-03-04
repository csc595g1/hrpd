package edu.depaul.csc595.jarvis.profile.user;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.main.MainActivity;
import edu.depaul.csc595.jarvis.profile.ProfileActivity;

/**
 * Created by Ed on 2/20/2016.
 */
public class GoogleImage extends AsyncTask<Object,Void,Bitmap> {
    private ImageView bmImage;
    private ProgressDialog progressBar;
    private ImageView iv;
    private MainActivity main;
    private ProfileActivity profile;

    protected void onPreExecute(){
        super.onPreExecute();
    }

    protected Bitmap doInBackground(Object... params) {
        Log.d("GoogleImage", "doInBackground in google image");

        if(params[0] instanceof ImageView){
            bmImage = (ImageView)params[0];
        }
        else if(params[0] instanceof ProgressDialog){
            progressBar = (ProgressDialog)params[0];
        }
        //else if(params[0] instanceof ImageView){
        //    iv = (ImageView) params[0];
        //}
        if(params.length > 1) {
            if (params[1] instanceof ProgressDialog) {
                progressBar = (ProgressDialog) params[1];
            }
            else if(params[1] instanceof MainActivity){
                main = (MainActivity) params[1];
            }
            else if(params[1] instanceof ProfileActivity){
                profile = (ProfileActivity) params[1];
            }
        }
        String url;
        try {
            url = UserInfo.getInstance().getGoogleAccount().getPhotoUrl().toString();
        }
        catch(NullPointerException e){
            Log.d("GoogleImage", "doInBackground no image");
            return null;
        }
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
        if(bmImage != null) {
            bmImage.setImageBitmap(result);
            UserInfo.getInstance().setGoogleProfileImage(bmImage);
            UserInfo.getInstance().setGoogleProfileBitMap(result);
        }
        else{
            if(main != null) {
                bmImage.setImageDrawable(main.getResources().getDrawable(R.drawable.profile));
                //bmImage.setImageDrawable(main.getResources().getDrawable(R.));
                main.notify();
                bmImage.notify();
            }
            else if (profile != null){
                bmImage.setImageDrawable(profile.getResources().getDrawable(R.drawable.profile));
                //bmImage.setImageDrawable(main.getResources().getDrawable(R.));
                profile.notify();
                bmImage.notify();
            }
        }
        //bmImage.notify();
        //notify();
    }
}
