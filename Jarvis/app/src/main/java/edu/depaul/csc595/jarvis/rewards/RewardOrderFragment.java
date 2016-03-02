package edu.depaul.csc595.jarvis.rewards;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.rewards.Models.RewardOrderModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardOrderFragment extends Fragment {

    Rewards rewards;
    RewardOrderModel rewardOrderModel;

    TextView editText_customer;
    TextView editText_account_identifier;
    TextView editText_campaign;
    TextView editText_name;
    TextView editText_email;
    TextView editText_sku;
    TextView editText_amount;
    TextView editText_reward_from;
    TextView editText_reward_subject;
    TextView editText_reward_message;
    TextView editText_send_reward;
    TextView editText_external_id;

    Button buttonOrder;

    public RewardOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_reward_order, container, false);

        buildUI(rootView);

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderPlaced = "Order Placed...";

                Snackbar.make(v,orderPlaced, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

//        pointsTask = new GetTotalPointsAsyncTask();



        return rootView;

    }

    private void buildUI(View rootView) {

        editText_customer = (TextView) rootView.findViewById(R.id.editText_customer);
        editText_account_identifier = (TextView) rootView.findViewById(R.id.editText_account_identifier);
        editText_campaign = (TextView) rootView.findViewById(R.id.editText_campaign);
        editText_name = (TextView) rootView.findViewById(R.id.editText_name);
        editText_email = (TextView) rootView.findViewById(R.id.editText_email);
        editText_sku = (TextView) rootView.findViewById(R.id.editText_sku);
        editText_amount = (TextView) rootView.findViewById(R.id.editText_amount);
        editText_reward_from = (TextView) rootView.findViewById(R.id.editText_reward_from);
        editText_reward_subject = (TextView) rootView.findViewById(R.id.editText_reward_subject);
        editText_reward_message = (TextView) rootView.findViewById(R.id.editText_reward_message);
        editText_send_reward = (TextView) rootView.findViewById(R.id.editText_send_reward);
        editText_external_id = (TextView) rootView.findViewById(R.id.editText_external_id);

        buttonOrder = (Button) rootView.findViewById(R.id.buttonOrder);

        boolean isAuthed = false;
        String email = "";
        String fullName = "";
        if (UserInfo.getInstance().isGoogleLoggedIn()) {
            isAuthed = true;
            email = UserInfo.getInstance().getGoogleAccount().getEmail();
        } else if (UserInfo.getInstance().getIsLoggedIn()) {
            isAuthed = true;
            email = UserInfo.getInstance().getCredentials().getEmail();
        }

        //check if logged in, if so, send reward event
        if (isAuthed) {
            editText_email.setText(email);
            editText_name.setText(UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName());
        }


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
