package edu.depaul.csc595.jarvis.reminders.staticreminders.rewardgenerate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.main.MainActivity;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.CreateRewardEventAsyncTask;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.CreateRewardEventModel;

/**
 * Created by Advait on 01-03-2016.
 */
public class ReceiveRewards extends Activity
{
    Button generate_reward = (Button) findViewById(R.id.btn_collect_rewards);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_receiving_screen);

        if(UserInfo.getInstance().getIsLoggedIn())
        {
            TextView tv_name = (TextView) findViewById(R.id.generate_reward_tv_user_name);

            tv_name.setText("Howdy "+UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName());

        }
        else if(UserInfo.getInstance().isGoogleLoggedIn()){
            try
            {
                TextView tv_name = (TextView) findViewById(R.id.generate_reward_tv_user_name);

                tv_name.setText(UserInfo.getInstance().getGoogleAccount().getDisplayName());

            }
            catch(NullPointerException e){

            }
        }
        else {
            TextView tv_name = (TextView) findViewById(R.id.generate_reward_tv_user_name);
            tv_name.setText("To get Rewards, you need to logged in ");
            generate_reward.setVisibility(View.INVISIBLE);
        }

        Button generate_reward = (Button) findViewById(R.id.btn_collect_rewards);
        generate_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isAuthed = false;
                String email = "";
                if (UserInfo.getInstance().isGoogleLoggedIn()) {
                    isAuthed = true;
                    email = UserInfo.getInstance().getGoogleAccount().getEmail();
                } else if (UserInfo.getInstance().getIsLoggedIn()) {
                    isAuthed = true;
                    email = UserInfo.getInstance().getCredentials().getEmail();
                }

                //check if logged in, if so, send reward event
                if (isAuthed) {
                    //do event sending here, pass email var set above
                    CreateRewardEventModel model = new CreateRewardEventModel(email, "Reminder Event", 20, "Attended Reminder");

                    CreateRewardEventAsyncTask task = new CreateRewardEventAsyncTask();
                    task.execute(model);

                    Intent go_to_home = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(go_to_home);
                } else {


                }
            }
        });

    }
}