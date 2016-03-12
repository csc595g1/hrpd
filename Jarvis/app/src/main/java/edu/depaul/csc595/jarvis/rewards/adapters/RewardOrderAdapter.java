package edu.depaul.csc595.jarvis.rewards.adapters;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.rewards.ItemClickSupport;
import edu.depaul.csc595.jarvis.rewards.Models.RewardCatalogModel;

/**
 * Created by markwilhelm on 3/6/16.
 */
public class RewardOrderAdapter extends RecyclerView.Adapter<RewardOrderAdapter.RewardOrderViewHolder> {
    final String TAG = "RewardOrderAdapter";

    private ArrayList<RewardCatalogModel> alRewardCatalogModel;
    private ImageView iv_catalog_icon;
    private Bitmap bm_catalog_icon;


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

    public void onBindViewHolder(RewardOrderAdapter.RewardOrderViewHolder viewHolder, int position) {
        RewardOrderViewHolder holder = (RewardOrderViewHolder) viewHolder;

        RewardCatalogModel model = (RewardCatalogModel) alRewardCatalogModel.get(position);
        if (model != null) {

            if (model.getCatalog_bitmap() == null) {
                Log.d(TAG, "onBindViewHolder->model.getCatalog_bitmap() " );

                RewardsUrlPngAsyncTask rewardsUrlPngAsyncTask = new RewardsUrlPngAsyncTask();
                rewardsUrlPngAsyncTask.execute(model.getImage_url(), bm_catalog_icon, position);
//                model.setCatalog_bitmap(bm_catalog_icon);
            }

            Log.d(TAG, model.getSku() + " -> " + model.getImage_url());

            holder.tv_sku.setText(model.getSku());
            holder.tv_amount.setText(Integer.toString(model.getDenomination()));
            holder.iv_catalog_icon.setImageBitmap(model.getCatalog_bitmap());
            holder.tv_description.setText(model.getDescription());
        }
    }

    public static class RewardOrderViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_sku;
        protected TextView tv_amount;
        protected TextView tv_description;
        protected ImageView iv_catalog_icon;

        public RewardOrderViewHolder(View v){
            super(v);
            tv_sku = (TextView) v.findViewById(R.id.tv_sku);
            tv_amount = (TextView) v.findViewById(R.id.tv_amount);
            iv_catalog_icon = (ImageView) v.findViewById(R.id.iv_catalog_icon);
            tv_description = (TextView) v.findViewById(R.id.tv_description);
        }
    }

    public int getItemCount(){
        return alRewardCatalogModel.size();
    }

    public  RewardCatalogModel getRewardCatalogModel(int position) {
        return alRewardCatalogModel.get(position);
    }


    class RewardsUrlPngAsyncTask extends AsyncTask<Object,Void,Bitmap> {
        private final String TAG = "RewardsUrlPngAsyncTask";
        private String catalogIconUrl = "";
        private ImageView catalogIconImageView;
        private Bitmap m_catalog_icon;
        private int mPosition;

        protected void onPreExecute(){super.onPreExecute();}

        @Override
        protected Bitmap doInBackground(Object... params) {
            Log.d(TAG, "doInBackground in catalog image");

            if(params.length > 0) {
                if (params[0] instanceof String) {
                    catalogIconUrl = (String) params[0];
                }
            }
            Log.d(TAG, "doInBackground: " + catalogIconUrl);

            if(params.length > 1) {
                if (params[1] instanceof Bitmap) {
                    m_catalog_icon = (Bitmap) params[1];
                }
            }

            if(params.length > 2) {
                mPosition = (int) params[2];
            }

            Bitmap catalogIconBitmap = null;
            try{
                InputStream in = new java.net.URL(catalogIconUrl).openStream();
                catalogIconBitmap = BitmapFactory.decodeStream(in);
                m_catalog_icon = catalogIconBitmap;
                if (catalogIconBitmap != null) {
                    Log.d(TAG, "catalogIconBitmap is not null");
                } else {
                    Log.d(TAG, "catalogIconBitmap is null");
                }

            }
            catch(Exception e){}
                return catalogIconBitmap;
            }


        protected void onPostExecute(Bitmap result){
//            @+id/card_view_rewards_content
            if (result != null) {
                Log.d(TAG, "result is not null");
//                bm_catalog_icon = result;
                RewardCatalogModel rewardCatalogModel = alRewardCatalogModel.get(mPosition);
                rewardCatalogModel.setCatalog_bitmap(result);
                alRewardCatalogModel.set(mPosition,rewardCatalogModel);

            } else {
                Log.d(TAG, "result is null");
            }
        }


    }

}
