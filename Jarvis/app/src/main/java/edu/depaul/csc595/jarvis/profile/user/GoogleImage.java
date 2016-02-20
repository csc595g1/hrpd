package edu.depaul.csc595.jarvis.profile.user;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Ed on 2/20/2016.
 */
public class GoogleImage extends AsyncTask<Void,Void,Bitmap> {
    ImageView bmImage;

    protected void onPreExecute(){
        super.onPreExecute();
    }

    protected Bitmap doInBackground(Void... arg0) {

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
        bmImage.setImageBitmap(result);
        UserInfo.getInstance().setGoogleProfileImage(bmImage);
    }
}
