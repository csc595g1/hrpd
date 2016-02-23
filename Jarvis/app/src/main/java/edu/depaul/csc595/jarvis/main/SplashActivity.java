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
//        User user;
//        UserInfo userInfo = UserInfo.getInstance();
//        UserLoginDataSource userDB;
//        HerokuLogin hlogin;
        Log.d(TAG, "onCreate: in splash " );
        //try silent google auth here
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .requestProfile()
//                .build();
//        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
//        OptionalPendingResult<GoogleSignInResult> pendingResult =
//                Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
//        if(pendingResult.isDone()){
//            GoogleSignInAccount account = pendingResult.get().getSignInAccount();
//            doGoogleAuth(account);
//            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        else{
//            pendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(GoogleSignInResult googleSignInResult) {
//                    GoogleSignInAccount account = googleSignInResult.getSignInAccount();
//                    doGoogleAuth(account);
//                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            });
//        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onCreate: in onStart ");
        //try heroku auth first then google.
        User user = UserInfo.getInstance().getUserForSplash(this.getBaseContext());
        //check if null
        if (user != null) {
            Log.d(TAG, "onCreate user is not null");
            //use credentials and send to server for auth
            HerokuLogin hlogin = new HerokuLogin();
            hlogin.execute(this, this.getBaseContext(), user.getEmail(), user.getPw());
        }
        else {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestProfile()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
            OptionalPendingResult<GoogleSignInResult> pendingResult =
                    Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
            if (pendingResult.isDone()) {
                GoogleSignInAccount account = pendingResult.get().getSignInAccount();
                if (null == account.getEmail()) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                doGoogleAuth(account);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                pendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {
                        GoogleSignInAccount account = googleSignInResult.getSignInAccount();
                        //if(account.getDisplayName() == null){
                        try {
                            String testNull = account.getDisplayName();
                        } catch (NullPointerException e) {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        //}
                        doGoogleAuth(account);
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }
    }

    private void doGoogleAuth(GoogleSignInAccount account){
        UserInfo.getInstance().signInWithGoogle(account, getBaseContext());
        //dialog = new ProgressDialog(SplashActivity.this);
        //dialog.setMessage("Loading...");
        //dialog.setTitle("Loading");
        //dialog.show();
        //GoogleImage image = new GoogleImage();
        //image.execute(dialog);
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
