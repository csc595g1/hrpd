package edu.depaul.csc595.jarvis.rewards.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by markwilhelm on 3/1/16.
 */
public class RewardOrderModel {

    private String customer;
    private String account_identifier;
    private String campaign;
    private String recipient_name;
    private String recipient_email;
    private String sku;
    private Integer amount;
    private String reward_from;
    private String reward_subject;
    private String reward_message;
    private String send_reward = "true";
    private String external_id;

//    {
//        "customer": "csc595g1_01",
//            "account_identifier": "csc595g1_01",
//            "campaign": "HomeSafety",
//            "recipient": {
//        "name": "Test Order",
//                "email": "csc595g1@gmail.com"
//    },
//        "sku": "TNGO-E-V-STD",
//            "amount": 1000,
//            "reward_from": "CSC595 Group1",
//            "reward_subject": "Here is your reward!",
//            "reward_message": "Way to go! Thanks!",
//            "send_reward": true,
//            "external_id": "123456-XYZ"
//    }






    public JSONObject toJSON() {

        JSONObject jsonResponse = new JSONObject();
        JSONObject jsonRecipient = new JSONObject();
        JSONArray jsonRecipientInfo = new JSONArray();


        try {
            jsonResponse.put("customer", this.customer);
            jsonResponse.put("account_identifier", this.account_identifier);
            jsonResponse.put("campaign", this.campaign);

            jsonRecipient.put("name", this.recipient_name);
            jsonRecipient.put("email", this.recipient_email);
            jsonRecipientInfo.put(jsonRecipient);
            jsonResponse.put("recipient",jsonRecipientInfo);

            jsonResponse.put("sku", this.sku);

            jsonResponse.put("amount", Integer.toString(this.amount.intValue()));

            jsonResponse.put("reward_from", this.reward_from);
            jsonResponse.put("reward_subject", this.reward_subject);
            jsonResponse.put("reward_message", this.reward_message);
            jsonResponse.put("send_reward", this.send_reward);
            jsonResponse.put("external_id", this.external_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }


    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAccount_identifier() {
        return account_identifier;
    }

    public void setAccount_identifier(String account_identifier) {
        this.account_identifier = account_identifier;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public String getRecipient_email() {
        return recipient_email;
    }

    public void setRecipient_email(String recipient_email) {
        this.recipient_email = recipient_email;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getReward_from() {
        return reward_from;
    }

    public void setReward_from(String reward_from) {
        this.reward_from = reward_from;
    }

    public String getReward_subject() {
        return reward_subject;
    }

    public void setReward_subject(String reward_subject) {
        this.reward_subject = reward_subject;
    }

    public String getReward_message() {
        return reward_message;
    }

    public void setReward_message(String reward_message) {
        this.reward_message = reward_message;
    }

    public String getSend_reward() {
        return send_reward;
    }

    public void setSend_reward(String send_reward) {
        this.send_reward = send_reward;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }
}
