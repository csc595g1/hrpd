package edu.depaul.csc595.jarvis.rewards.adapters;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.rewards.ItemClickSupport;
import edu.depaul.csc595.jarvis.rewards.Models.RewardCatalogModel;

/**
 * Created by markwilhelm on 3/6/16.
 */
public class RewardOrderAdapter extends RecyclerView.Adapter<RewardOrderAdapter.RewardOrderViewHolder> {
    final String TAG = "RewardOrderAdapter";

    private ArrayList<RewardCatalogModel> alRewardCatalogModel;

//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        public TextView mtv_sku;
//        public TextView mtv_amount;
//        public TextView mtv_description;
//        public ViewHolder(View v) {
//            super(v);
//            mtv_sku = (TextView) v.findViewById(R.id.tv_sku);
//            mtv_amount = (TextView) v.findViewById(R.id.tv_amount);
//            mtv_description = (TextView) v.findViewById(R.id.tv_description);
//        }
//
//    }

    public RewardOrderAdapter(ArrayList<RewardCatalogModel> catalogList){
        Log.d(TAG, "RewardOrderAdapter " );
        this.alRewardCatalogModel = catalogList;
    }

    @Override
    public RewardOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RewardOrderAdapter.RewardOrderViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.card_view_row_orders, parent, false);
        viewHolder = new RewardOrderAdapter.RewardOrderViewHolder(v);

        return viewHolder;
    }

    public void onBindViewHolder(RewardOrderAdapter.RewardOrderViewHolder viewHolder, int position){
        RewardOrderViewHolder holder = (RewardOrderViewHolder) viewHolder;

        Log.d(TAG, "onBindViewHolder " );
        RewardCatalogModel model = (RewardCatalogModel) alRewardCatalogModel.get(position);
        if (model != null) {
            holder.tv_sku.setText(model.getSku());
            holder.tv_amount.setText(Integer.toString(model.getDenomination()));
            holder.tv_description.setText(model.getDescription());
        }
    }

    public static class RewardOrderViewHolder extends RecyclerView.ViewHolder {
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

    public int getItemCount(){
        return alRewardCatalogModel.size();
    }

    public  RewardCatalogModel getRewardCatalogModel(int position) {
        return alRewardCatalogModel.get(position);
    }


}
