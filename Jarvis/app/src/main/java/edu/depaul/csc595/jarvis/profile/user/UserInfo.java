package edu.depaul.csc595.jarvis.profile.user;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;
import java.util.ArrayList;

/**
 * Created by Ed on 1/29/2016.
 */
public final class UserInfo {

    private boolean isLoggedIn = false;
    private String userName;
    private String pw;
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

    public void setLoggedIn(boolean status){
        isLoggedIn = status;
    }

    public boolean getIsLoggedIn(){return isLoggedIn;}

    public boolean logOutUser(Context context){
        boolean returnBool = false;
        UserLoginDataSource db = new UserLoginDataSource(context);
        return returnBool;
    }

    public boolean logInUser(String email, String password, Context context){
        boolean returnBool = false;
        UserLoginDataSource db = new UserLoginDataSource(context);


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
        //if logging into tango succesful, log session on db

        try {
            //something to do with tango log-in here

            db.open();
            db.resetAllLoggedInFlags();
            db.insertUserNameAndPassword(email, password);
            db.close();
            userName = email;
            pw = password;
            isLoggedIn = true;
            returnBool = true;
        }
        catch (SQLDataException e){
            returnBool = false;
            isLoggedIn = false;
            //do something to ensure that tango is not logged in
            userName = " ";
            pw = " ";
        }
        return returnBool;
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
