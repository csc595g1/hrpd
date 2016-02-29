package edu.depaul.csc595.jarvis.profile;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.detection.RegisterDeviceToken;
import edu.depaul.csc595.jarvis.detection.gcm.TokenIntentService;
import edu.depaul.csc595.jarvis.profile.user.HerokuCreateAccount;
import edu.depaul.csc595.jarvis.profile.user.User;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;

public class SignUpActivity extends AppCompatActivity {

    private String firstName, lastName, password, emailAddr;
    //private FloatingActionButton fab;
    private EditText fNameInput, lNameInput, emailInput, confirmEmailInput, pwInput;
    private Button signup_btn;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        fNameInput = (EditText) findViewById(R.id.signup_activity_first_name);
        lNameInput = (EditText) findViewById(R.id.signup_activity_last_name);
        emailInput = (EditText) findViewById(R.id.signup_activity_email_address);
        confirmEmailInput = (EditText) findViewById(R.id.signup_activity_confirm_email);
        pwInput = (EditText) findViewById(R.id.signup_activity_pw);
        signup_btn = (Button) findViewById(R.id.button_signup);
    }

    protected void onStart(){
        super.onStart();
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((fNameInput.getText().toString().equals("") || fNameInput.getText().toString().equals(" "))
                        || (lNameInput.getText().toString().equals("") || lNameInput.getText().toString().equals(" "))){
                    Toast.makeText(getBaseContext(),"First and last name is required!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(emailInput.getText().toString().equals("") || emailInput.getText().toString().equals(" ")){
                    Toast.makeText(getBaseContext(),"Email is required!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(confirmEmailInput.getText().toString().equals("") || confirmEmailInput.getText().toString().equals(" ")){
                    Toast.makeText(getBaseContext(),"Please confirm email!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(pwInput.getText().toString().equals("") || pwInput.getText().toString().equals(" ")){
                    Toast.makeText(getBaseContext(),"Password is required!",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    if(emailInput.getText().toString().equals(confirmEmailInput.getText().toString())){
                        //firstName, lastName, password, emailAddr
                        //firstName = fNameInput.getText().toString();
                        //lastName = lNameInput.getText().
                        UserInfo userInfo = UserInfo.getInstance();
                        userInfo.setFirstName(fNameInput.getText().toString());
                        userInfo.setLastName(lNameInput.getText().toString());
                        userInfo.setCredentials(emailInput.getText().toString(), pwInput.getText().toString());

                        mProgressDialog = new ProgressDialog(SignUpActivity.this);
                        mProgressDialog.setTitle("Creating Account");
                        mProgressDialog.setMessage("Please wait...");
                        mProgressDialog.show();

                        HerokuCreateAccount herokuCreateAccount = new HerokuCreateAccount();
                        herokuCreateAccount.execute(SignUpActivity.this, mProgressDialog, getBaseContext());

                        // Fetch the newly created user and his email.
//                        User currentUser = userInfo.getCredentials();
//                        String email_address = currentUser.getEmail();
//
//                        // Fetch the device token.
//                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);
//                        String token = sharedPreferences.getString(TokenIntentService.GCM_TOKEN, "");
//
//                        // Set url for the web service
//                        String webServiceUrl = "https://detectionservices.herokuapp.com/register_gcm_token";
//
//                        // Register this device(token) on the server.
//                        new RegisterDeviceToken(SignUpActivity.this).execute(email_address, token, webServiceUrl);
                    }
                    else{
                        Toast.makeText(getBaseContext(),"Email confirmation does not match!",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
