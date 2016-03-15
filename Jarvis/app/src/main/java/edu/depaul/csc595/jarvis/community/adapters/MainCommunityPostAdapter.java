package edu.depaul.csc595.jarvis.community.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.community.CommunityBoardActivity;
import edu.depaul.csc595.jarvis.community.CommunityPostDetailsActivity;
import edu.depaul.csc595.jarvis.community.asynctasks.CheckUserUpvotedPostAsync;
import edu.depaul.csc595.jarvis.community.asynctasks.GetUpvoteCountAsync;
import edu.depaul.csc595.jarvis.community.asynctasks.SendUpvoteForPost;
import edu.depaul.csc595.jarvis.community.models.CommunityPostMainModel;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;

/**
 * Created by Ed on 3/8/2016.
 */
public class MainCommunityPostAdapter extends RecyclerView.Adapter<MainCommunityPostAdapter.CommunityPostViewHolder>{
        List<CommunityPostMainModel> modelList;
        Context context;
    CommunityBoardActivity activity;

    public MainCommunityPostAdapter(List<CommunityPostMainModel> modelList, Context context, CommunityBoardActivity activity){
        this.modelList = modelList;
        this.context = context;
        this.activity = activity;
    }

    public int getItemCount(){return modelList.size(); }

    public void onBindViewHolder(MainCommunityPostAdapter.CommunityPostViewHolder viewHolder, int position){
        final CommunityPostViewHolder holder = viewHolder;
        final CommunityPostMainModel model = modelList.get(position);
        if(model != null){
            holder.postName.setText(model.name + " says:");
            holder.postContent.setSingleLine(false);
            holder.postContent.setText(model.content);

            //getupvotescount
            GetUpvoteCountAsync task = new GetUpvoteCountAsync();
            task.execute(holder.upvotecount,model.post_id);

            //check if user upvoted
            CheckUserUpvotedPostAsync async2 = new CheckUserUpvotedPostAsync();
            async2.execute(holder.upvote,model.email,model.post_id,activity);

            if(Integer.valueOf(model.repliesCount) > 0){
                if(Integer.valueOf(model.repliesCount) == 1) {
                    holder.countOfReplies.setText("(" + model.repliesCount + " reply)");
                }
                else{
                    holder.countOfReplies.setText("(" + model.repliesCount + " replies)");
                }
            }
            holder.vieweditcomments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if (UserInfo.getInstance().getIsLoggedIn() || UserInfo.getInstance().isGoogleLoggedIn()) {
                    Intent intent = new Intent(CommunityBoardActivity.activity, CommunityPostDetailsActivity.class);
                    intent.putExtra("name", String.valueOf(holder.postName.getText()));
                    intent.putExtra("content", String.valueOf(holder.postContent.getText()));
                    //put somebackground info in
                    intent.putExtra("post_id", model.post_id);
                    String transitionName = context.getString(R.string.transition_comm_post_to_details);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CommunityBoardActivity.activity, holder.compostll, transitionName);
                    ActivityCompat.startActivity(CommunityBoardActivity.activity, intent, options.toBundle());
                    //} else {
                    //Toast.makeText(context, "Log in to view or post comments", Toast.LENGTH_LONG).show();
                    //}
                }
            });
            holder.upvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.upvote.isClickable()) {
                        if (UserInfo.getInstance().getIsLoggedIn() || UserInfo.getInstance().isGoogleLoggedIn()) {
                            //todo make green
                            holder.upvote.setColorFilter(Color.GREEN);
                            Toast.makeText(context, "Upvoted", Toast.LENGTH_LONG).show();
                            //todo send rewards
                            SendUpvoteForPost task1 = new SendUpvoteForPost();
                            task1.execute(model.email,model.post_id);
                            holder.upvote.setClickable(false);
                        } else {
                            Toast.makeText(context, "Sign in to upvote!", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(context, "Already Upvoted", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public MainCommunityPostAdapter.CommunityPostViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        CommunityPostViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View v = inflater.inflate(R.layout.card_view_community_post_frag,viewGroup,false);
        viewHolder = new CommunityPostViewHolder(v);
        return viewHolder;
    }

    public static class CommunityPostViewHolder extends RecyclerView.ViewHolder{
        protected TextView postName;
        protected  TextView postContent;
        protected ImageView upvote;
        protected TextView vieweditcomments;
        protected TextView countOfReplies;
        protected LinearLayout compostll;
        protected TextView upvotecount;

        public CommunityPostViewHolder(View v){
            super(v);
            postName = (TextView) v.findViewById(R.id.cv_com_post_name);
            postContent = (TextView) v.findViewById(R.id.cv_com_post_content);
            upvote = (ImageView) v.findViewById(R.id.post_frag_up_vote);
            vieweditcomments = (TextView) v.findViewById(R.id.comm_frag_view_post_comment);
            compostll = (LinearLayout)v.findViewById(R.id.comm_post_ll);
            countOfReplies = (TextView)v.findViewById(R.id.comm_frag_view_reply_count);
            upvotecount = (TextView)v.findViewById(R.id.upvotecount);
        }
    }
}
