package edu.depaul.csc595.jarvis.profile.user;

/**
 * Created by Ed on 1/31/2016.
 * basic data structure to return user information when needed.
 */
public class User {

    private String email, pw;

    public User(String email, String pw){
        this.email = email;
        this.pw = pw;
    }

    //empty const.
    public User(){}

    public void setEmail(String e){email = e;}
    public void setPw(String p){pw = p;}

    public String getEmail(){return email;}
    public String getPw(){return pw;}
}
