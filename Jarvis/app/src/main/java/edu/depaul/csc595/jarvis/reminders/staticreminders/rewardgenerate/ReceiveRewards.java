package edu.depaul.csc595.jarvis.reminders.staticreminders.rewardgenerate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.CreateRewardEventAsyncTask;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.CreateRewardEventModel;

/**
 * Created by Advait on 01-03-2016.
 */
public class ReceiveRewards extends Activity
{
    //Button generate_reward = (Button) findViewById(R.id.for_rewards_click_me);
    //TextView tv_name = (TextView) findViewById(R.id.generate_reward_tv_user_name);

    Button add_my_rewards;
    TextView current_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_receiving_screen);

        current_user_name = (TextView) findViewById(R.id.generate_reward_tv_user_name);
        add_my_rewards = (Button) findViewById(R.id.for_rewards_click_me);

        if(UserInfo.getInstance().getIsLoggedIn())
        {
            current_user_name.setText("Hello " + UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName());
        }
        else if(UserInfo.getInstance().isGoogleLoggedIn())
        {
            //tv_name.setText("Howdy " + UserInfo.getInstance().getGoogleAccount().getDisplayName());
            current_user_name.setText("Hello " + UserInfo.getInstance().getGoogleAccount().getDisplayName());
        }

        add_my_rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                CreateRewardEventModel model = new CreateRewardEventModel(UserInfo.getInstance().getCredentials().getEmail(), "Reminder Event", 20, "Attended Reminder");

                CreateRewardEventAsyncTask task = new CreateRewardEventAsyncTask();
                task.execute(model);

                //Intent go_to_home = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(go_to_home);
            }
        });
/*
        generate_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CreateRewardEventModel model = new CreateRewardEventModel(UserInfo.getInstance().getCredentials().getEmail(), "Reminder Event", 20, "Attended Reminder");

                CreateRewardEventAsyncTask task = new CreateRewardEventAsyncTask();
                task.execute(model);

                //Intent go_to_home = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(go_to_home);


            }
        });
*/
    }
}
