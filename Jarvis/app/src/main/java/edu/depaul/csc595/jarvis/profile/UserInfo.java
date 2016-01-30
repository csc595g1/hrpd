package edu.depaul.csc595.jarvis.profile;

/**
 * Created by Ed on 1/29/2016.
 */
public final class UserInfo {

    private boolean isLoggedIn = false;
    private String userName;

    //private const
    private UserInfo(){}
    private static UserInfo instance = null;

    public static UserInfo getInstance(){
        if(instance == null){
            return instance = new UserInfo();
        }
        else return instance;
    }

    public void setLoggedIn(boolean status){
        isLoggedIn = status;
    }

    public boolean getIsLoggedIn(){return isLoggedIn;}
}
