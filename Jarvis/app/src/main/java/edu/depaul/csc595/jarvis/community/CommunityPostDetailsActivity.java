package edu.depaul.csc595.jarvis.community;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.community.adapters.CommunityRepliesAdapter;
import edu.depaul.csc595.jarvis.community.asynctasks.RepliesForPostAsync;
import edu.depaul.csc595.jarvis.community.asynctasks.SendReplyAsync;
import edu.depaul.csc595.jarvis.community.models.CommunityReplyModel;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;

/**
 * Created by Ed on 3/8/2016.
 */
//, RepliesForPostAsync.ListListener
public class CommunityPostDetailsActivity extends AppCompatActivity implements ReplyDialogFragment.OnReplyDialogResultListener, RepliesForPostAsync.ListListener{
    private String name;
    private String content;
    private TextView tv_name;
    private TextView tv_content;
    private String post_id;
    private TextView no_content;
    private TextView enterReply;
    private List<CommunityReplyModel> list;
    RecyclerView recyclerView;
    CommunityRepliesAdapter adapter;

    protected void onCreate(Bundle savedInstance){
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Slide());
        super.onCreate(savedInstance);
        setContentView(R.layout.card_view_community_detail_activity);

        no_content = (TextView) findViewById(R.id.emptyRecycler);

        enterReply = (TextView) findViewById(R.id.postReply);
        enterReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterReply();
            }
        });


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
        recyclerView = (RecyclerView) findViewById(R.id.activity_community_comments_recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(this.getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        list = new ArrayList<>();
        adapter = new CommunityRepliesAdapter(list,getBaseContext(),CommunityPostDetailsActivity.this);
        recyclerView.setAdapter(adapter);
        //needs context, CommunityPostDetailsActivity, recyclerView,string(post_id) ,textview
        RepliesForPostAsync async = new RepliesForPostAsync();
        //async.s

        async.execute(getBaseContext(),CommunityPostDetailsActivity.this,recyclerView,post_id,no_content,list);
    }

    public void enterReply(){
        DialogFragment frag = new ReplyDialogFragment();
        frag.show(getSupportFragmentManager(),"reply");
    }

    public void onPost(String result){
        if(UserInfo.getInstance().isGoogleLoggedIn()||UserInfo.getInstance().getIsLoggedIn()) {
            CommunityReplyModel model = new CommunityReplyModel();
            model.reply_id = " ";
            model.content = result;
            if (UserInfo.getInstance().isGoogleLoggedIn()) {
                model.email = UserInfo.getInstance().getGoogleAccount().getEmail();
                model.name = UserInfo.getInstance().getGoogleAccount().getDisplayName();
            } else {
                model.email = UserInfo.getInstance().getCredentials().getEmail();
                model.name = UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName();
            }
            model.post_id = post_id;
            Log.d("replyactivity", "onPostExecute list size: " + list.size());
            Log.d("replyactivity", "onPostExecute list reference: " + list.toString());
            //immediately add to list to show. then kick off async to send to server
            list.add(model);
            adapter = new CommunityRepliesAdapter(list,getBaseContext(),CommunityPostDetailsActivity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.refreshDrawableState();
            if(recyclerView.getVisibility() == View.INVISIBLE){
                recyclerView.setVisibility(View.VISIBLE);
                no_content.setVisibility(View.INVISIBLE);
            }
            //no kick off the async task
            SendReplyAsync async = new SendReplyAsync();
            async.execute(model);

            //RepliesForPostAsync async1 = new RepliesForPostAsync();
            //async1.execute(getBaseContext(),CommunityPostDetailsActivity.this,recyclerView,post_id,no_content,list);
        }
        else{
            Toast.makeText(getBaseContext(),"Need to login to post!",Toast.LENGTH_LONG).show();
        }
    }

    public void getList(List<CommunityReplyModel> list){
        //make sure activity has updated list
        Log.d("postdetailsactivity", "getList setting list...");
        this.list = list;
    }

}
