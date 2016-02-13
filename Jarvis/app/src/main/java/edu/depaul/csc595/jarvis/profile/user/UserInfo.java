package edu.depaul.csc595.jarvis.profile.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.SQLDataException;
import java.util.ArrayList;

import edu.depaul.csc595.jarvis.profile.LogInActivity;

/**
 * Created by Ed on 1/29/2016.
 */
public final class UserInfo {

    private boolean isLoggedIn = false;
    private String userName;
    private String pw;
    private String email;
    private User user;
    private String authMessage;
    //Sess
    //private const
    private UserInfo(){}
    private static UserInfo instance = null;

    public static UserInfo getInstance(){


        if(instance == null){
            return instance = new UserInfo();
        }
        else return instance;
    }

    public void setCredentials(String email, String pw){
        this.email = email;
        this.pw = pw;
    }

    public void setAuthMessage(String message){
        authMessage = message;
    }

    public String getAuthMessage(){
        return authMessage;
    }

    public User getCredentials(){
        return new User(email,pw);
    }

    public void setLoggedIn(boolean status){
        isLoggedIn = status;
    }

    public boolean getIsLoggedIn(){return isLoggedIn;}

    public boolean logOutUser(Context context){
        isLoggedIn = false;
        UserLoginDataSource db = new UserLoginDataSource(context);
        try{
            db.open();
            db.updateFlagForUser(userName, 0);
            db.close();
        }
        catch(SQLDataException e){
            return false;
        }
        return isLoggedIn;
    }

    public boolean logInUser(String email, String password, Context context, LogInActivity logInActivity, ProgressDialog progressDialog){
        UserLoginDataSource db = new UserLoginDataSource(context);

        try{
            this.email = email;
            this.pw = password;
        }
        catch(Exception e){
            this.email = " ";
            this.pw = " ";
            this.isLoggedIn = false;
            return false;
        }

        if(userName == null){userName = " ";}

        if(!userName.equals(" ")){
            try{
                db.open();
                db.updateFlagForUser(userName, 0);
                db.close();
            }
            catch(SQLDataException e){
                return false;
            }
            userName = " ";
            pw = " ";
        }

            boolean auth = false;
            HerokuLogin login = new HerokuLogin();
            login.execute(logInActivity,progressDialog,context);

        return isLoggedIn;
    }

    protected void insertLocalInformationForLogin(Context context){
        UserLoginDataSource db = new UserLoginDataSource(context);
        Log.d("UserLogin", this.email + " " + this.pw + " " + this.isLoggedIn);
        try {
            db.open();
            db.resetAllLoggedInFlags();
            db.insertUserNameAndPassword(this.email, this.pw);
            if (!isLoggedIn) {
                db.updateFlagForUser(email, 0);
            }
            db.close();
            //userName = email;
            //pw = password;
        }
        catch (SQLDataException e){
            //eturnBool = false;
            isLoggedIn = false;
            //do something to ensure that tango is not logged in
            userName = " ";
            pw = " ";
        }
    }

    public static ArrayList<User> getUserList(Context context){
        ArrayList<User> users = new ArrayList<>();
        UserLoginDataSource db = new UserLoginDataSource(context);
        try{
            db.open();
            users = db.returnAllUsersForAutoComplettion();
            db.close();
        }
        catch(SQLDataException e){
            return new ArrayList<User>();
        }
        return users;
    }
}
