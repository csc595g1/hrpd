package edu.depaul.csc595.jarvis.rewards;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.GetRewardEventsAsyncTask;
import edu.depaul.csc595.jarvis.rewards.Models.RewardBalanceModel;
import edu.depaul.csc595.jarvis.rewards.adapters.RewardBalanceAdapter;

/**
 * Created by Ed on 2/26/2016.
 */
public class RewardBalanceFragment extends Fragment {
    ProgressDialog dialog;
    List<RewardBalanceModel> modelList;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_rewards_balance, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_reward_balance_recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        modelList = new ArrayList<RewardBalanceModel>();

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.setTitle("Status");
        dialog.show();

        //need to add async task to get reward events for email and apply list to adapter.
        GetRewardEventsAsyncTask task = new GetRewardEventsAsyncTask();
        task.execute(modelList,dialog,recyclerView);

        RewardBalanceAdapter adapter = new RewardBalanceAdapter(modelList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
