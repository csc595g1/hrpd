package edu.depaul.csc595.jarvis.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.profile.adapters.LogInAutoCompleteAdapter;
import edu.depaul.csc595.jarvis.profile.user.HerokuGoogleAuth;
import edu.depaul.csc595.jarvis.profile.user.HerokuLogin;
import edu.depaul.csc595.jarvis.profile.user.User;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class LogInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;
    private UserInfo userInfo;
    //private AutoCompleteTextView email;
    private EditText email;
    private EditText pw;
    private FloatingActionButton fab;
    private ArrayList<User> userList;
    //private android.support.v7.widget.AppCompatButton loginBtn;
    private Button loginBtn;
    private ProgressDialog mProgDialog;
    private SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        //setSupportActionBar(toolbar);

        //email = (AutoCompleteTextView) findViewById(R.id.login_username);
        email = (EditText) findViewById(R.id.emailField);
        //pw = (EditText) findViewById(R.id.login_pw);
        pw = (EditText) findViewById(R.id.passwordField);
        //fab = (FloatingActionButton) findViewById(R.id.login_fab);
        //loginBtn = (android.support.v7.widget.AppCompatButton) findViewById(R.id.login_login_btn);
        loginBtn = (Button) findViewById(R.id.button_login);

//        fab.setOnClickListener(new View.OnClickListener() {
//                                   @Override
//                                   public void onClick(View v) {
//                                       Snackbar.make(v, "Send email to support", Snackbar.LENGTH_LONG)
//                                               .setAction("action",null).show();
//                                   }
//                               }
//        );

        TextView createAccountLink = (TextView) findViewById(R.id.login_link_signup);
        createAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            }
        });

        //Google Auth
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        signInButton = (SignInButton) findViewById(R.id.google_sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
    }

    @Override
    protected void onStart(){
        super.onStart();
        final LogInActivity logInActivity = this;
        userInfo = UserInfo.getInstance();
        userList = UserInfo.getUserList(getApplicationContext());
        //mProgDialog = new ProgressDialog();
        LogInAutoCompleteAdapter autoCompleteAdapter = new LogInAutoCompleteAdapter(getApplicationContext(),R.layout.login_auto_fill,userList);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().equals(" ") && !pw.getText().toString().equals(" ")){
                    mProgDialog = new ProgressDialog(LogInActivity.this);
                    mProgDialog.setTitle("Sign In");
                    mProgDialog.setMessage("Signing In...");
                    mProgDialog.show();
                    //mProgDialog //= ProgressDialog.show(getBaseContext(),"Sign In", "Signing In..,",true);
                    //boolean authed = userInfo.logInUser(email.getText().toString(),pw.getText().toString(),getApplicationContext(),logInActivity,mProgDialog);
                    userInfo.setCredentials(email.getText().toString(), pw.getText().toString());
                    HerokuLogin hlogin = new HerokuLogin();
                    hlogin.execute(LogInActivity.this,mProgDialog,getBaseContext());
                }
            }
        });

        //Google Auth
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        Log.d(TAG, "handleSignInResult in method");
        Log.d(TAG, "handleSignInResult success: " + result.isSuccess());
        if(result.isSuccess()){
            Log.d(TAG, "handleSignInResult in if statement for success");
            GoogleSignInAccount account = result.getSignInAccount();
            UserInfo.getInstance().signInWithGoogle(account,getBaseContext());
            Log.d(TAG, "handleSignInResult print out some account stuff: " + account.getEmail() + " " + account.getDisplayName());
            //send heroku info
            HerokuGoogleAuth heroku = new HerokuGoogleAuth();
            heroku.execute(LogInActivity.this);
//            Intent intent = new Intent(LogInActivity.this,ProfileActivity.class);
//            startActivity(intent);
//            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
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
    }
}
