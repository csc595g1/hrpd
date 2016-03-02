package edu.depaul.csc595.jarvis.rewards;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.RewardOrderAsyncTask;
import edu.depaul.csc595.jarvis.rewards.Models.RewardOrderModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardOrderFragment extends Fragment {

    Rewards rewards;
    //RewardOrderModel rewardOrderModel;

    EditText editText_customer;
    EditText editText_account_identifier;
    EditText editText_campaign;
    EditText editText_name;
    EditText editText_email;
    EditText editText_sku;
    EditText editText_amount;
    EditText editText_reward_from;
    EditText editText_reward_subject;
    EditText editText_reward_message;
    EditText editText_send_reward;
    EditText editText_external_id;

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

                //need to add async task to place an order.
                RewardOrderAsyncTask task = new RewardOrderAsyncTask();
                RewardOrderModel rewardOrderModel = buildOrder();
                task.execute(rewardOrderModel,null,null);

                String orderPlaced = "Order Placed...";
                Snackbar.make(v,orderPlaced, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

//        pointsTask = new GetTotalPointsAsyncTask();



        return rootView;

    }

    private void buildUI(View rootView) {

        editText_customer = (EditText) rootView.findViewById(R.id.editText_customer);
        editText_account_identifier = (EditText) rootView.findViewById(R.id.editText_account_identifier);
        editText_campaign = (EditText) rootView.findViewById(R.id.editText_campaign);
        editText_name = (EditText) rootView.findViewById(R.id.editText_name);
        editText_email = (EditText) rootView.findViewById(R.id.editText_email);
        editText_sku = (EditText) rootView.findViewById(R.id.editText_sku);
        editText_amount = (EditText) rootView.findViewById(R.id.editText_amount);
        editText_reward_from = (EditText) rootView.findViewById(R.id.editText_reward_from);
        editText_reward_subject = (EditText) rootView.findViewById(R.id.editText_reward_subject);
        editText_reward_message = (EditText) rootView.findViewById(R.id.editText_reward_message);
        editText_send_reward = (EditText) rootView.findViewById(R.id.editText_send_reward);
        editText_external_id = (EditText) rootView.findViewById(R.id.editText_external_id);

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

        RewardOrderModel rewardOrderModel = new RewardOrderModel();

        rewardOrderModel.setCustomer(editText_customer.getText().toString());
        rewardOrderModel.setAccount_identifier(editText_account_identifier.getText().toString());
        rewardOrderModel.setCampaign(editText_campaign.getText().toString());
        rewardOrderModel.setRecipient_name(editText_name.getText().toString());
        rewardOrderModel.setRecipient_email(editText_email.getText().toString());
        rewardOrderModel.setSku(editText_sku.getText().toString());

        String amount = editText_amount.getText().toString();
        rewardOrderModel.setAmount(Integer.valueOf(amount));

        rewardOrderModel.setReward_from(editText_reward_from.getText().toString());
        rewardOrderModel.setReward_subject(editText_reward_subject.getText().toString());
        rewardOrderModel.setReward_message(editText_reward_message.getText().toString());
        rewardOrderModel.setSend_reward(editText_send_reward.getText().toString());
        rewardOrderModel.setExternal_id(editText_external_id.getText().toString());


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
