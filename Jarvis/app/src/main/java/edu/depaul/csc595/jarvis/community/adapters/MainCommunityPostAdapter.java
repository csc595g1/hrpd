package edu.depaul.csc595.jarvis.community.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.community.models.CommunityPostMainModel;

/**
 * Created by Ed on 3/8/2016.
 */
public class MainCommunityPostAdapter extends RecyclerView.Adapter<MainCommunityPostAdapter.CommunityPostViewHolder>{
        List<CommunityPostMainModel> modelList;

    public MainCommunityPostAdapter(List<CommunityPostMainModel> modelList){
        this.modelList = modelList;
    }

    public int getItemCount(){return modelList.size(); }

    public void onBindViewHolder(MainCommunityPostAdapter.CommunityPostViewHolder viewHolder, int position){
        CommunityPostViewHolder holder = viewHolder;
        CommunityPostMainModel model = modelList.get(position);
        if(model != null){
            holder.postName.setText(model.name + " says:");
            holder.postContent.setSingleLine(false);
            holder.postContent.setText(model.content);
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

        public CommunityPostViewHolder(View v){
            super(v);
            postName = (TextView) v.findViewById(R.id.cv_com_post_name);
            postContent = (TextView) v.findViewById(R.id.cv_com_post_content);
        }
    }
}
