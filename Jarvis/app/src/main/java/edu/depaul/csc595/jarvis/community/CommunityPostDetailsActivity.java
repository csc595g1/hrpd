package edu.depaul.csc595.jarvis.community;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Window;
import android.widget.TextView;

import edu.depaul.csc595.jarvis.R;

/**
 * Created by Ed on 3/8/2016.
 */
public class CommunityPostDetailsActivity extends AppCompatActivity {
    private String name;
    private String content;
    private TextView tv_name;
    private TextView tv_content;

    protected void onCreate(Bundle savedInstance){
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Slide());
        super.onCreate(savedInstance);
        setContentView(R.layout.card_view_community_detail_activity);

        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        content = extras.getString("content");

        if(name != null && content != null){
            tv_name = (TextView) findViewById(R.id.comm_details_name);
            tv_content = (TextView) findViewById(R.id.comm_details_content);
            tv_name.setText(name);
            tv_content.setText(content);
        }
    }
}
