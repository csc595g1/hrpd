package edu.depaul.csc595.jarvis.main.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.main.card_view_model.CardViewModel;

/**
 * Created by Ed on 2/23/2016.
 */
public class MainCardViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> cardList;

    private static final int TYPE_REWARD = 0;
    private static final int TYPE_GENERIC = 1;

    public MainCardViewAdapter(List<Object> cardList){
        this.cardList = cardList;
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
    }

    private void configureCardViewHolder(CardViewHolder ch, int position){
        CardViewModel model = (CardViewModel)cardList.get(position);
        if(model != null){
            ch.tvcv_title.setText(model.title);
            ch.tvcv_content.setText(model.content);
        }
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
        public CardViewRewardsHolder(View v){
            super(v);
        }
    }

}
