package edu.depaul.csc595.jarvis.main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.profile.user.HerokuLogin;
import edu.depaul.csc595.jarvis.profile.user.User;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.profile.user.UserLoginDataSource;

public class SplashActivity extends AppCompatActivity {
final String TAG = "Splash";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        User user;
        UserInfo userInfo = UserInfo.getInstance();
        UserLoginDataSource userDB;
        HerokuLogin hlogin;
        Log.d(TAG, "onCreate: in splash " );
        //check if user info exists and flag is 1.
        //if data exists, attempt to log into server and auth
        //if not successful, continue to main activity
        user = userInfo.getUserForSplash(this.getBaseContext());
        //check if null
        if(user != null){
            Log.d(TAG, "onCreate user is not null");
            //use credentials and send to server for auth
            hlogin = new HerokuLogin();
            hlogin.execute(this,this.getBaseContext(),user.getEmail(),user.getPw());
        }
        else{
            Log.d(TAG, "onCreate user is null");
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


//    @Override
//    protected void onStart(){
//        super.onStart();
//        User user;
//        UserInfo userInfo = UserInfo.getInstance();
//        UserLoginDataSource userDB;
//        HerokuLogin hlogin;
//
//        //check if user info exists and flag is 1.
//        //if data exists, attempt to log into server and auth
//        //if not successful, continue to main activity
//        user = userInfo.getUserForSplash(this.getBaseContext());
//        //check if null
//        if(user != null){
//            //use credentials and send to server for auth
//            hlogin = new HerokuLogin();
//            hlogin.execute(this,this.getBaseContext(),user.getEmail(),user.getPw());
//        }
//        else{
//            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
