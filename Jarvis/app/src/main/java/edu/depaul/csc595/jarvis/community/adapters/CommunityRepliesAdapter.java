package edu.depaul.csc595.jarvis.community.adapters;

import android.content.Context;
import android.graphics.Color;
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
import edu.depaul.csc595.jarvis.community.CommunityPostDetailsActivity;
import edu.depaul.csc595.jarvis.community.models.CommunityReplyModel;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;

/**
 * Created by Ed on 3/12/2016.
 */
public class CommunityRepliesAdapter extends RecyclerView.Adapter<CommunityRepliesAdapter.CommunityReplyViewHolder>{

    List<CommunityReplyModel> modelList;
    Context context;
    CommunityPostDetailsActivity activity;

    public CommunityRepliesAdapter(List<CommunityReplyModel> modelList, Context context, CommunityPostDetailsActivity activity){
        this.modelList = modelList;
        this.context = context;
        this.activity = activity;
    }

    public int getItemCount(){return modelList.size(); }

    public void onBindViewHolder(CommunityRepliesAdapter.CommunityReplyViewHolder viewHolder, int position){
        final CommunityReplyViewHolder holder = viewHolder;
        CommunityReplyModel model = modelList.get(position);
        if(model != null){
            holder.postName.setText(model.name + " says:");
            holder.postContent.setSingleLine(false);
            holder.postContent.setText(model.content);
            holder.upvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (UserInfo.getInstance().getIsLoggedIn() || UserInfo.getInstance().isGoogleLoggedIn()) {
                        //todo make green
                        holder.upvote.setColorFilter(Color.GREEN);
                        Toast.makeText(context, "Upvoted", Toast.LENGTH_LONG).show();
                        //todo send rewards
                    } else {
                        Toast.makeText(context, "Sign in to upvote!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public CommunityRepliesAdapter.CommunityReplyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        CommunityReplyViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View v = inflater.inflate(R.layout.card_view_community_reply_post,viewGroup,false);
        viewHolder = new CommunityReplyViewHolder(v);
        return viewHolder;
    }

    public static class CommunityReplyViewHolder extends RecyclerView.ViewHolder{
        protected TextView postName;
        protected  TextView postContent;
        protected ImageView upvote;
        protected LinearLayout comreplyll;



        public CommunityReplyViewHolder(View v){
            super(v);
            postName = (TextView) v.findViewById(R.id.cv_com_reply_name);
            postContent = (TextView) v.findViewById(R.id.cv_com_reply_content);
            upvote = (ImageView) v.findViewById(R.id.reply_frag_up_vote);
            comreplyll = (LinearLayout)v.findViewById(R.id.comm_reply_ll);
        }
    }
}
