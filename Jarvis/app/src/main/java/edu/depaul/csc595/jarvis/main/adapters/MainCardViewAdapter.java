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
public class MainCardViewAdapter extends RecyclerView.Adapter<MainCardViewAdapter.CardViewHolder> {

    private List<CardViewModel> cardList;

    public MainCardViewAdapter(List<CardViewModel> cardList){
        this.cardList = cardList;
    }

    @Override
    public int getItemCount(){
        return cardList.size();
    }

    //@Override
    //public int getItemViewType(int position){
    //    return cardList.get(position);
    //}

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i){
        CardViewModel cm = cardList.get(i);
        cardViewHolder.tvcv_title.setText(cm.title);
        cardViewHolder.tvcv_content.setText(cm.content);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_row_main_activity,viewGroup,false);

        return new CardViewHolder(itemView);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        protected TextView tvcv_title;
        protected TextView tvcv_content;

        public CardViewHolder(View v){
            super(v);
            tvcv_title = (TextView) v.findViewById(R.id.card_view_title);
            tvcv_content = (TextView) v.findViewById(R.id.card_view_content);
        }
    }

}
