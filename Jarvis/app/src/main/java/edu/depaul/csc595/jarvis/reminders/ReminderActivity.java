package edu.depaul.csc595.jarvis.reminders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.depaul.csc595.jarvis.R;

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