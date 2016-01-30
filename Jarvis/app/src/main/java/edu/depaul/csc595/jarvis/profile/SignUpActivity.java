package edu.depaul.csc595.jarvis.profile;

import android.content.pm.PackageInstaller;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import edu.depaul.csc595.jarvis.R;

public class SignUpActivity extends AppCompatActivity {

    private String firstName, lastName, password, emailAddr;
    private FloatingActionButton fab;
    private EditText fNameInput, lNameInput, emailInput, confirmEmailInput, pwInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fab = (FloatingActionButton) findViewById(R.id.signup_fab);

        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Snackbar.make(v, "Send email to support", Snackbar.LENGTH_LONG)
                                               .setAction("action", null).show();
                                   }
                               }
        );

        fNameInput = (EditText) findViewById(R.id.signup_first_name);
        lNameInput = (EditText) findViewById(R.id.signup_last_name);
        emailInput = (EditText) findViewById(R.id.signup_email);
        confirmEmailInput = (EditText) findViewById(R.id.signup_confirm_email);
        pwInput = (EditText) findViewById(R.id.signup_confirm_email);

    }

    protected void onStart(){
        super.onStart();
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
