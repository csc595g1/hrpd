package edu.depaul.csc595.jarvis.community.models;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ed on 3/8/2016.
 */
public class CommunityPostMainModel {
    private final static String TAG = "CommPostModel";
    public String name;
    public String content;
    public String email;
    public String post_id;
    public String dttm;
    public String repliesCount;

    public static List<CommunityPostMainModel> parseGetCommunityPostList(String json){
        List<CommunityPostMainModel> returnList = new ArrayList<>();

        JsonParser parser = new JsonParser();
        JsonArray jarray = parser.parse(json).getAsJsonObject().getAsJsonArray("posts");
        for(int i = 0; i < jarray.size();i++){
            JsonObject obj = jarray.get(i).getAsJsonObject();
            Log.d(TAG, "parseGetCommunityPostList " + obj);
            //post_id, email, name, content, dttm
            CommunityPostMainModel model = new CommunityPostMainModel();
            model.post_id = String.valueOf(obj.get("post_id").getAsInt());
            //add count of replies
            model.repliesCount = String.valueOf(obj.get("countOfReplies").getAsInt());
            model.email = obj.get("email").getAsString();
            model.name = obj.get("name").getAsString();
            model.content = obj.get("content").getAsString();
            model.dttm = obj.get("dttm").getAsString();
            returnList.add(model);
        }
        return returnList;
    }
}
