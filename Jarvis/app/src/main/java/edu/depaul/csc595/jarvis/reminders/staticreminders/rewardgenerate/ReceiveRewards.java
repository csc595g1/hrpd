package edu.depaul.csc595.jarvis.reminders.staticreminders.rewardgenerate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.main.MainActivity;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.reminders.staticreminders.rewardgenerate.circularimage.CircleDisplay;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.CreateRewardEventAsyncTask;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.CreateRewardEventModel;

/**
 * Created by Advait on 01-03-2016.
 */
public class ReceiveRewards extends AppCompatActivity implements CircleDisplay.SelectionListener
{

    private CircleDisplay mCircleDisplay;

    TextView current_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_receiving_screen);

        mCircleDisplay = (CircleDisplay) findViewById(R.id.circleDisplay);

        current_user_name = (TextView) findViewById(R.id.generate_reward_tv_user_name);

        if(UserInfo.getInstance().getIsLoggedIn())
        {
            current_user_name.setText("Hello " + UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName());
        }
        else if(UserInfo.getInstance().isGoogleLoggedIn())
        {
            current_user_name.setText("Hello " + UserInfo.getInstance().getGoogleAccount().getDisplayName());
        }

        CreateRewardEventModel model =
                new CreateRewardEventModel(UserInfo.getInstance().getEmail(),
                        "Reminder Event",
                        20,
                        "Attended Reminder");

        CreateRewardEventAsyncTask task = new CreateRewardEventAsyncTask();
        task.execute(model);

        mCircleDisplay.setAnimDuration(4000);
        mCircleDisplay.setValueWidthPercent(55f);
        mCircleDisplay.setFormatDigits(1);
        mCircleDisplay.setDimAlpha(80);
        //mCircleDisplay.setSelectionListener();
        mCircleDisplay.setTouchEnabled(false);
        mCircleDisplay.setUnit("%");
        mCircleDisplay.setStepSize(0.5f);
        mCircleDisplay.showValue(100f, 100f, true);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // TODO: Your application init goes here.
                Intent mInHome =
                        new Intent(ReceiveRewards.this,
                                MainActivity.class);
                ReceiveRewards.this.startActivity(mInHome);
                ReceiveRewards.this.finish();
                Toast.makeText(getApplicationContext(),
                        "Rewards added",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }, 5000);

    }

    @Override
    public void onSelectionUpdate(float val, float maxval) {
        Log.i("Main", "Selection update: " + val + ", max: " + maxval);
    }

    @Override
    public void onValueSelected(float val, float maxval) {
        Log.i("Main", "Selection complete: " + val + ", max: " + maxval);
    }

    @Override
    public void onBackPressed() {
        // Write your code here

        Intent go = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(go);

        super.onBackPressed();
    }
}
