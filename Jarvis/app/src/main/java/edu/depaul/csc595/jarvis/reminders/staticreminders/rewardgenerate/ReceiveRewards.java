package edu.depaul.csc595.jarvis.reminders.staticreminders.rewardgenerate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.main.MainActivity;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.reminders.staticreminders.rewardgenerate.circularimage.CircleDisplay;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.CreateRewardEventAsyncTask;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.CreateRewardEventModel;

/**
 * Created by Advait on 01-03-2016.
 */
public class ReceiveRewards extends Activity implements CircleDisplay.SelectionListener
{

    private CircleDisplay mCircleDisplay;

    Button add_my_rewards;
    TextView current_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_receiving_screen);

        mCircleDisplay = (CircleDisplay) findViewById(R.id.circleDisplay);
        mCircleDisplay.setVisibility(View.GONE);

        current_user_name = (TextView) findViewById(R.id.generate_reward_tv_user_name);
        add_my_rewards = (Button) findViewById(R.id.for_rewards_click_me);

        if(UserInfo.getInstance().getIsLoggedIn())
        {
            current_user_name.setText("Hello " + UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName());
        }
        else if(UserInfo.getInstance().isGoogleLoggedIn())
        {
            current_user_name.setText("Hello " + UserInfo.getInstance().getGoogleAccount().getDisplayName());
        }

        add_my_rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                CreateRewardEventModel model =
                        new CreateRewardEventModel(UserInfo.getInstance().
                                getCredentials().getEmail(),
                                "Reminder Event",
                                20,
                                "Attended Reminder");

                CreateRewardEventAsyncTask task = new CreateRewardEventAsyncTask();
                task.execute(model);

                mCircleDisplay.setVisibility(View.VISIBLE);

                mCircleDisplay.setAnimDuration(10000);
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
                }, 12000);

            }
        });
    }

    @Override
    public void onSelectionUpdate(float val, float maxval) {
        Log.i("Main", "Selection update: " + val + ", max: " + maxval);
    }

    @Override
    public void onValueSelected(float val, float maxval) {
        Log.i("Main", "Selection complete: " + val + ", max: " + maxval);
    }
}
