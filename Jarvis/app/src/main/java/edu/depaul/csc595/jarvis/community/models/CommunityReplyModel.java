package edu.depaul.csc595.jarvis.community.models;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ed on 3/12/2016.
 */
public class CommunityReplyModel {
    private final static String TAG = "CommPostModel";
    public String name;
    public String content;
    public String email;
    public String post_id;
    public String dttm;
    public String reply_id;

    public static List<CommunityReplyModel> parseGetCommunityReplyList(String json){
        List<CommunityReplyModel> returnList = new ArrayList<>();

        JsonParser parser = new JsonParser();
        JsonArray jarray = parser.parse(json).getAsJsonObject().getAsJsonArray("replies");
        for(int i = 0; i < jarray.size();i++){
            JsonObject obj = jarray.get(i).getAsJsonObject();
            Log.d(TAG, "parseGetCommunityPostList " + obj);
            //post_id, email, name, content, dttm
            CommunityReplyModel model = new CommunityReplyModel();
            model.reply_id = String.valueOf(obj.get("reply_id").getAsInt());
            model.post_id = String.valueOf(obj.get("post_id").getAsInt());
            model.email = obj.get("email").getAsString();
            model.name = obj.get("name").getAsString();
            model.content = obj.get("content").getAsString();
            model.dttm = obj.get("dttm").getAsString();
            returnList.add(model);
        }
        return returnList;
    }
}
