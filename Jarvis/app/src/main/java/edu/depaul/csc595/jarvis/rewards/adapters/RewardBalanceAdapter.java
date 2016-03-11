package edu.depaul.csc595.jarvis.rewards.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.main.adapters.MainCardViewAdapter;
import edu.depaul.csc595.jarvis.rewards.Models.RewardBalanceModel;

/**
 * Created by Ed on 2/26/2016.
 */
public class RewardBalanceAdapter extends RecyclerView.Adapter<RewardBalanceAdapter.RewardBalanceViewHolder> {
    private List<RewardBalanceModel> balanceList;

    public RewardBalanceAdapter(List<RewardBalanceModel> balanceList){
        this.balanceList = balanceList;
    }

    public int getItemCount(){
        return balanceList.size();
    }

    public void onBindViewHolder(RewardBalanceAdapter.RewardBalanceViewHolder viewHolder,int position){
        RewardBalanceViewHolder holder = (RewardBalanceViewHolder) viewHolder;
        RewardBalanceModel model = (RewardBalanceModel)balanceList.get(position);
        if(model != null){
            holder.points_tv.setText(Integer.toString(model.units));
            if(model.units < 0){
                holder.points_tv.setTextColor(Color.RED);
            }
            else if(model.units > 0){
                holder.points_tv.setTextColor(Color.GREEN);
            }
            holder.event_tv.setText(model.title);
            //holder.balance_dttm.setText(model.dttm);
            holder.balance_dttm.setText(model.getFormattedDttm());
        }
    }

    public RewardBalanceAdapter.RewardBalanceViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        RewardBalanceAdapter.RewardBalanceViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View v = inflater.inflate(R.layout.reward_balance_row,viewGroup,false);
        viewHolder = new RewardBalanceAdapter.RewardBalanceViewHolder(v); // new MainCardViewAdapter.CardViewRewardsHolder(v);
        return viewHolder;
    }

    public static class RewardBalanceViewHolder extends RecyclerView.ViewHolder{
        protected TextView balance_dttm;
        protected TextView event_tv;
        protected TextView points_tv;

        public RewardBalanceViewHolder(View v){
            super(v);
            balance_dttm = (TextView) v.findViewById(R.id.reward_balance_datetime_tv);
            event_tv = (TextView) v.findViewById(R.id.reward_balance_event_tv);
            points_tv = (TextView) v.findViewById(R.id.reward_balance_points_tv);
        }
    }
}
