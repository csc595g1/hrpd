package edu.depaul.csc595.jarvis.community.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.community.CommunityBoardActivity;
import edu.depaul.csc595.jarvis.community.models.CommunityPostMainModel;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;

/**
 * Created by Ed on 3/8/2016.
 */
public class MainCommunityPostAdapter extends RecyclerView.Adapter<MainCommunityPostAdapter.CommunityPostViewHolder>{
        List<CommunityPostMainModel> modelList;
        Context context;

    public MainCommunityPostAdapter(List<CommunityPostMainModel> modelList, Context context){
        this.modelList = modelList;
        this.context = context;
    }

    public int getItemCount(){return modelList.size(); }

    public void onBindViewHolder(MainCommunityPostAdapter.CommunityPostViewHolder viewHolder, int position){
        final CommunityPostViewHolder holder = viewHolder;
        CommunityPostMainModel model = modelList.get(position);
        if(model != null){
            holder.postName.setText(model.name + " says:");
            holder.postContent.setSingleLine(false);
            holder.postContent.setText(model.content);
            holder.upvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(UserInfo.getInstance().getIsLoggedIn() || UserInfo.getInstance().isGoogleLoggedIn()){
                        //todo make green
                        holder.upvote.setColorFilter(Color.GREEN);
                        Toast.makeText(context,"Upvoted",Toast.LENGTH_LONG).show();
                        //todo send rewards
                    }
                    else{
                        Toast.makeText(context,"Sign in to upvote!",Toast.LENGTH_LONG).show();
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

        public CommunityPostViewHolder(View v){
            super(v);
            postName = (TextView) v.findViewById(R.id.cv_com_post_name);
            postContent = (TextView) v.findViewById(R.id.cv_com_post_content);
            upvote = (ImageView) v.findViewById(R.id.post_frag_up_vote);
        }
    }
}
