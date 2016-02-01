package edu.depaul.csc595.jarvis.profile.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.profile.user.User;
import edu.depaul.csc595.jarvis.settings.SettingsActivity;

/**
 * Created by Ed on 1/31/2016.
 */
public class LogInAutoCompleteAdapter extends ArrayAdapter<User> {
    private ArrayList<User> users;
    private Context context;
    private int viewResourceId;

    public LogInAutoCompleteAdapter(Context context, int textViewResourceId, ArrayList<User> users){
        super(context, textViewResourceId, users);
        this.users = users;
        this.context = context;
        viewResourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if(v == null){
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId,null);
        }

        User user = users.get(position);
        if(user != null){
            TextView userName = (TextView) v.findViewById(R.id.login_auto_fill_user);
            if(userName != null){
                userName.setText(String.valueOf(user.getEmail()));
            }
        }
        return v;
    }
}
