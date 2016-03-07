package edu.depaul.csc595.jarvis.rewards.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.rewards.Models.RewardCatalogModel;

/**
 * Created by markwilhelm on 3/6/16.
 */
public class RewardOrderAdapter extends RecyclerView.Adapter<RewardOrderAdapter.RewardOrderViewHolder> {
    private List<RewardCatalogModel> catalogList;

    public RewardOrderAdapter(List<RewardCatalogModel> catalogList){
        this.catalogList = catalogList;
    }

    public int getItemCount(){
        return catalogList.size();
    }

    public void onBindViewHolder(RewardOrderAdapter.RewardOrderViewHolder viewHolder,int position){
        RewardOrderViewHolder holder = (RewardOrderViewHolder) viewHolder;
        RewardCatalogModel model = (RewardCatalogModel)catalogList.get(position);

        if (model != null) {
            holder.tv_amount.setText(Integer.toString(model.getDenomination()));
            holder.tv_sku.setText(model.getSku());
            holder.tv_description.setText(model.getDescription());
        }
    }

    @Override
    public RewardOrderViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RewardOrderAdapter.RewardOrderViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View v = inflater.inflate(R.layout.card_view_row_orders, viewGroup,false);
        viewHolder = new RewardOrderAdapter.RewardOrderViewHolder(v);

        return viewHolder;
    }

    public static class RewardOrderViewHolder extends RecyclerView.ViewHolder{
        protected TextView tv_sku;
        protected TextView tv_amount;
        protected TextView tv_description;

        public RewardOrderViewHolder(View v){
            super(v);
            tv_sku = (TextView) v.findViewById(R.id.tv_sku);
            tv_amount = (TextView) v.findViewById(R.id.tv_amount);
            tv_description = (TextView) v.findViewById(R.id.tv_description);
        }
    }

}
