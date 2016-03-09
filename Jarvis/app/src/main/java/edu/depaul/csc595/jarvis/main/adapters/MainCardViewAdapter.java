package edu.depaul.csc595.jarvis.main.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.community.CommunityBoardActivity;
import edu.depaul.csc595.jarvis.main.MainActivity;
import edu.depaul.csc595.jarvis.main.card_view_model.CardViewModel;
import edu.depaul.csc595.jarvis.profile.LogInActivity;
import edu.depaul.csc595.jarvis.profile.ProfileActivity;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.GetTotalPointsAsyncTask;
import edu.depaul.csc595.jarvis.rewards.RewardsActivity;

/**
 * Created by Ed on 2/23/2016.
 */
public class MainCardViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> cardList;
    private MainActivity mainActivity;

    private static final int TYPE_REWARD = 0;
    private static final int TYPE_GENERIC = 1;
    private static final int TYPE_COMMUNITY = 2;

    public MainCardViewAdapter(List<Object> cardList, MainActivity activity){
        this.cardList = cardList;
        mainActivity = activity;
    }

    @Override
    public int getItemCount(){
        return cardList.size();
    }

    @Override
    public int getItemViewType(int position){
        if(cardList.get(position) instanceof String && cardList.get(position).equals("reward_card")){
            return TYPE_REWARD;
        }
        else if(cardList.get(position) instanceof CardViewHolder){
            return TYPE_GENERIC;
        }
        else if(cardList.get(position) instanceof String && cardList.get(position).equals("community_card")){
            return TYPE_COMMUNITY;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){

        switch(viewHolder.getItemViewType()){
            case TYPE_REWARD:
                CardViewRewardsHolder crewards = (CardViewRewardsHolder) viewHolder;
                configureCardViewRewardsHolder(crewards,position);
                break;
            case TYPE_GENERIC:
                CardViewHolder cardviewholder = (CardViewHolder) viewHolder;
                configureCardViewHolder(cardviewholder, position);
                break;
            case TYPE_COMMUNITY:
                CardViewCommunityHolder cardholder = (CardViewCommunityHolder) viewHolder;
                configureCardviewCommunityHolder(cardholder,position);
                break;
            default:
                CardViewHolder ch = (CardViewHolder) viewHolder;
                configureCardViewHolder(ch, position);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch(viewType){
            case TYPE_REWARD:
                View v1 = inflater.inflate(R.layout.card_view_row_rewards,viewGroup,false);
                viewHolder = new CardViewRewardsHolder(v1);
                break;
            case TYPE_GENERIC:
                View v2 = inflater.inflate(R.layout.card_view_row_main_activity,viewGroup,false);
                viewHolder = new CardViewHolder(v2);
                break;
            case TYPE_COMMUNITY:
                View v3 = inflater.inflate(R.layout.card_view_row_community_main_activity,viewGroup,false);
                viewHolder = new CardViewCommunityHolder(v3);
                break;
            default:
                View v = inflater.inflate(R.layout.card_view_row_main_activity, viewGroup, false);
                viewHolder = new CardViewHolder(v);
                break;
        }

        return viewHolder;
    }

    private void configureCardViewRewardsHolder(CardViewRewardsHolder holder, int position){
        //do nothing as view is configured for demo.
        //can update as reward functionality arises
        if(UserInfo.getInstance().getIsLoggedIn() || UserInfo.getInstance().isGoogleLoggedIn()) {
            GetTotalPointsAsyncTask task = new GetTotalPointsAsyncTask();
            task.execute(holder);
            holder.tv_redeem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mainActivity, RewardsActivity.class);
                    //intent.
                    mainActivity.startActivity(intent);
                }
            });
        }
        else{
            holder.tv_content.setText("0");
            holder.tv_redeem.setText("Log In or Sign Up to Get Rewards!");
            holder.tv_redeem.setTextColor(Color.WHITE);
            holder.tv_redeem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mainActivity,LogInActivity.class);
                    mainActivity.startActivity(intent);
                }
            });
        }
    }

    private void configureCardViewHolder(CardViewHolder ch, int position){
        CardViewModel model = (CardViewModel)cardList.get(position);
        if(model != null){
            ch.tvcv_title.setText(model.title);
            ch.tvcv_content.setText(model.content);
        }
    }

    private void configureCardviewCommunityHolder(CardViewCommunityHolder ch, int position){
        ch.clickable_link_to_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, CommunityBoardActivity.class);
                mainActivity.startActivity(intent);
            }
        });
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        protected TextView tvcv_title;
        protected TextView tvcv_content;

        public CardViewHolder(View v){
            super(v);
            tvcv_title = (TextView) v.findViewById(R.id.card_view_title);
            tvcv_content = (TextView) v.findViewById(R.id.card_view_content);
        }

        public void setTvcv_title(TextView title){tvcv_title = title;}
        public void setTvcv_content(TextView content){tvcv_content = content;}
    }

    public static class CardViewRewardsHolder extends RecyclerView.ViewHolder{
        public TextView tv_content;
        protected TextView tv_redeem;
        public CardViewRewardsHolder(View v){
            super(v);
            tv_content = (TextView) v.findViewById(R.id.card_view_rewards_content);
            tv_redeem = (TextView) v.findViewById(R.id.card_view_rewards_redeem);
        }
    }

    public static class CardViewCommunityHolder extends RecyclerView.ViewHolder{

        public TextView clickable_link_to_activity;

        public CardViewCommunityHolder(View v){
            super(v);
            clickable_link_to_activity = (TextView) v.findViewById(R.id.cv_main_act_comm_click_tv);
        }
    }

}
