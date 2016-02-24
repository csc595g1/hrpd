package edu.depaul.csc595.jarvis.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.profile.user.User;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;

/**
 * Created by Ed on 2/23/2016.
 */
public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView frag_prof_email = (TextView) rootView.findViewById(R.id.frag_prof_email);
        TextView frag_prof_name = (TextView) rootView.findViewById(R.id.frag_prof_name);
        TextView frag_prof_points = (TextView) rootView.findViewById(R.id.frag_prof_points);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        if(UserInfo.getInstance().getIsLoggedIn()){
            User user = UserInfo.getInstance().getCredentials();
            frag_prof_email.setText(user.getEmail());
            frag_prof_name.setText(UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName());
        }
        else if(UserInfo.getInstance().isGoogleLoggedIn()){
            frag_prof_email.setText(UserInfo.getInstance().getGoogleAccount().getEmail());
            frag_prof_name.setText(UserInfo.getInstance().getGoogleAccount().getDisplayName());
        }
        return rootView;
    }
}
