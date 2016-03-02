package edu.depaul.csc595.jarvis.reminders.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.profile.LogInActivity;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;

/**
 * Created by Advait on 18-02-2016.
 */
public class ReminderActivity extends AppCompatActivity
{
    Button linkForCustom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        //if not logged in, send to login page.
        if(!UserInfo.getInstance().isGoogleLoggedIn() && !UserInfo.getInstance().getIsLoggedIn()){
            Intent intent = new Intent(ReminderActivity.this,LogInActivity.class);
            startActivity(intent);
            finish();
        }

        linkForCustom = (Button) findViewById(R.id.go_to_custom_reminder);
        linkForCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go = new Intent(getApplicationContext(), CustomReminderActivity.class);
                startActivity(go);

            }
        });

    }
}