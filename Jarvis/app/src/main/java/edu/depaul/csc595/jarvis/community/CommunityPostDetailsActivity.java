package edu.depaul.csc595.jarvis.community;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.community.adapters.CommunityRepliesAdapter;
import edu.depaul.csc595.jarvis.community.asynctasks.RepliesForPostAsync;
import edu.depaul.csc595.jarvis.community.models.CommunityReplyModel;

/**
 * Created by Ed on 3/8/2016.
 */
public class CommunityPostDetailsActivity extends AppCompatActivity {
    private String name;
    private String content;
    private TextView tv_name;
    private TextView tv_content;
    private String post_id;
    private TextView no_content;

    protected void onCreate(Bundle savedInstance){
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Slide());
        super.onCreate(savedInstance);
        setContentView(R.layout.card_view_community_detail_activity);

        no_content = (TextView) findViewById(R.id.emptyRecycler);

        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        content = extras.getString("content");
        post_id = extras.getString("post_id");

        if(name != null && content != null){
            tv_name = (TextView) findViewById(R.id.comm_details_name);
            tv_content = (TextView) findViewById(R.id.comm_details_content);
            tv_name.setText(name);
            tv_content.setText(content);
        }


        //set reply list
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_community_comments_recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(this.getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        List<CommunityReplyModel> list = new ArrayList<>();
        CommunityRepliesAdapter adapter = new CommunityRepliesAdapter(list,getBaseContext(),CommunityPostDetailsActivity.this);
        recyclerView.setAdapter(adapter);
        //needs context, CommunityPostDetailsActivity, recyclerView,string(post_id) ,textview
        RepliesForPostAsync async = new RepliesForPostAsync();
        async.execute(getBaseContext(),CommunityPostDetailsActivity.this,recyclerView,post_id,no_content);
    }
}
