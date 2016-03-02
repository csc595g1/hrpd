package edu.depaul.csc595.jarvis.profile;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.detection.RegisterDeviceToken;
import edu.depaul.csc595.jarvis.detection.gcm.TokenIntentService;
import edu.depaul.csc595.jarvis.profile.user.User;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.GetTotalPointsAsyncTask;

/**
 * Created by Ed on 2/23/2016.
 */
public class ProfileFragment extends Fragment {

    private String email_address;
    private final String TAG = "ProfileFragment";
    private GetTotalPointsAsyncTask pointsTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView frag_prof_email = (TextView) rootView.findViewById(R.id.frag_prof_email);
        TextView frag_prof_name = (TextView) rootView.findViewById(R.id.frag_prof_name);
        TextView frag_prof_points = (TextView) rootView.findViewById(R.id.frag_prof_points);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        Button registerDeviceBtn = (Button) rootView.findViewById(R.id.button_register_device);
        pointsTask = new GetTotalPointsAsyncTask();

        if(UserInfo.getInstance().getIsLoggedIn()){
            User user = UserInfo.getInstance().getCredentials();
            frag_prof_email.setText(user.getEmail());
            frag_prof_name.setText(UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName());
            email_address = user.getEmail();
            pointsTask.execute(ProfileFragment.this,frag_prof_points);
        }
        else if(UserInfo.getInstance().isGoogleLoggedIn()){
            frag_prof_email.setText(UserInfo.getInstance().getGoogleAccount().getEmail());
            frag_prof_name.setText(UserInfo.getInstance().getGoogleAccount().getDisplayName());
            email_address = UserInfo.getInstance().getGoogleAccount().getEmail();
            pointsTask.execute(ProfileFragment.this,frag_prof_points);
        }

        registerDeviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch the device token.
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String token = sharedPreferences.getString(TokenIntentService.GCM_TOKEN, "");

                // Set url for the web service
                String webServiceUrl = "https://detectionservices.herokuapp.com/register_gcm_token";

                // Register this device(token) on the server.
                new RegisterDeviceToken(getContext()).execute(email_address, token, webServiceUrl);
                Log.d(TAG, "Email_Address = " + email_address);
            }
        });


        return rootView;
    }
}
