package edu.depaul.csc595.jarvis.rewards;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.rewards.Models.RewardOrderModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardOrderFragment extends Fragment {

    Rewards rewards;
    RewardOrderModel rewardOrderModel;

    public RewardOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reward_order, container, false);
    }

    private RewardOrderModel buildOrder() {

        rewardOrderModel.setCustomer("csc595g1_01");
        rewardOrderModel.setAccount_identifier("csc595g1_01");
        rewardOrderModel.setCampaign("HomeSafety");
        rewardOrderModel.setRecipient_name("Test Order");
        rewardOrderModel.setRecipient_email("csc595g1@gmail.com");
        rewardOrderModel.setSku("TNGO-E-V-STD");
        rewardOrderModel.setAmount(1000);
        rewardOrderModel.setReward_from("CSC595 Group1");
        rewardOrderModel.setReward_subject("Here is your reward!");
        rewardOrderModel.setReward_message("Way to go! Thanks!");
        rewardOrderModel.setSend_reward("true");
        rewardOrderModel.setExternal_id("123456-XYZ");


        return rewardOrderModel;
    }











//    {
//        "customer": "csc595g1_01",
//        "account_identifier": "csc595g1_01",
//        "campaign": "HomeSafety",
//        "recipient": {
//            "name": "Test Order",
//            "email": "csc595g1@gmail.com"
//        },
//        "sku": "TNGO-E-V-STD",
//            "amount": 1000,
//            "reward_from": "CSC595 Group1",
//            "reward_subject": "Here is your reward!",
//            "reward_message": "Way to go! Thanks!",
//            "send_reward": true,
//            "external_id": "123456-XYZ"
//    }


}
