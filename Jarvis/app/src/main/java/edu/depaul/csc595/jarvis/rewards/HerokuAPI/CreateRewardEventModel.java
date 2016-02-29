package edu.depaul.csc595.jarvis.rewards.HerokuAPI;

/**
 * Created by Ed on 2/25/2016.
 */
public class CreateRewardEventModel {

    private String userId;
    private String eventCategory;
    private int units;
    private String title;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CreateRewardEventModel(String userId, String eventCategory, int units, String title){
        this.userId = userId;
        this.eventCategory = eventCategory;
        this.units = units;
        this.title = title;
    }

    public CreateRewardEventModel(){}
}
