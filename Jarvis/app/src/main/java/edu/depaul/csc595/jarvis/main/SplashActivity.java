package edu.depaul.csc595.jarvis.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.profile.user.GoogleImage;
import edu.depaul.csc595.jarvis.profile.user.HerokuGoogleAuth;
import edu.depaul.csc595.jarvis.profile.user.HerokuLogin;
import edu.depaul.csc595.jarvis.profile.user.User;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.profile.user.UserLoginDataSource;

public class SplashActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private final String TAG = "Splash";
    private GoogleApiClient mGoogleApiClient;
    ProgressDialog dialog;
    private static int RC_SIGN_IN = 9001;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;
    private boolean mResolvingConnectionFailure = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d(TAG, "onCreate: in splash " );
    }

    @Override
    protected void onStart(){
        super.onStart();

        /*
        * workflow for splash:
        * 1. try to log in with google
        * 2. if success, set singleton class and send to main
        * 3. if fail, try to log into heroku auth
        * 4. if success, set singleton class and send to main
        * 5. if fail, send to main with login status set to false
        */

        //reset all flags
        UserInfo.getInstance().signOutWithGoogle();
        UserInfo.getInstance().setLoggedIn(false);

        //try google auth
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        OptionalPendingResult<GoogleSignInResult> pendingResult =
                Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        Log.d(TAG, "onStart pending result isdone" + pendingResult.isDone());
        if (pendingResult.isDone()) {
            GoogleSignInAccount account = pendingResult.get().getSignInAccount();
            if (null == account.getEmail()) {
                Log.d(TAG, "onStart account is null");
                //something went wrong and sending to main with no auth
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                //just putting in returns to stop code execution
                return;
            }
            else{
                Log.d(TAG, "onStart setting google auth after successful google login");
                doGoogleAuth(account);
                ProgressDialog dialog = new ProgressDialog(SplashActivity.this);
                dialog.setTitle("Signing in");
                dialog.setMessage("Signing in");
                dialog.show();
                HerokuGoogleAuth heroku = new HerokuGoogleAuth();
                heroku.execute(dialog,SplashActivity.this);
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
                //just putting this in to ensure that code execution stops after finish.
                return;
            }
        }
        //end google auth

        Log.d(TAG, "onStart googleauth bypassed");
        //now try heroku auth if google fails
        //try to get user info from DB
        User user = UserInfo.getInstance().getUserForSplash(this.getBaseContext());

        //check if null
        if (user == null || null == user.getEmail()){
            //send with no auth
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        else{
            //do heroku login async task if user info pulled from db
            HerokuLogin hlogin = new HerokuLogin();
            hlogin.execute(this, this.getBaseContext(), user.getEmail(), user.getPw());
        }
    }

    private void doGoogleAuth(GoogleSignInAccount account){
        Log.d(TAG, "doGoogleAuth in doGoogleAuth");
        UserInfo.getInstance().signInWithGoogle(account, getBaseContext());
        //dialog = new ProgressDialog(SplashActivity.this);
        //dialog.setMessage("Loading...");
        //dialog.setTitle("Loading");
        //dialog.show();
        //GoogleImage image = new GoogleImage();
        //image.execute(dialog);
    }


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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        if(mResolvingConnectionFailure){return;}
        if(mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mResolvingConnectionFailure = true;
            //try jarvis auth
            User user = UserInfo.getInstance().getUserForSplash(this.getBaseContext());
            //check if null
            if (user != null) {
                Log.d(TAG, "onCreate user is not null");
                //use credentials and send to server for auth
                HerokuLogin hlogin = new HerokuLogin();
                hlogin.execute(this, this.getBaseContext(), user.getEmail(), user.getPw());
            } else {
                Log.d(TAG, "onCreate user is null");
                UserInfo.getInstance().logOutUser(getBaseContext());
                UserInfo.getInstance().signOutWithGoogle();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void onConnectionSuspended(int i){
        Log.d(TAG, "onCreate user is null");
        UserInfo.getInstance().logOutUser(getBaseContext());
        UserInfo.getInstance().signOutWithGoogle();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onConnected(Bundle bundle){

    }

}
