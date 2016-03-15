package edu.depaul.csc595.jarvis.profile.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.sql.SQLDataException;
import java.util.ArrayList;

import edu.depaul.csc595.jarvis.profile.LogInActivity;

/**
 * Created by Ed on 1/29/2016.
 */
public final class UserInfo {
    private final String TAG = "UserInfo";
    private boolean isLoggedIn = false;
    private String userName;
    private String pw;
    private String email;
    private User user;
    private String authMessage;
    private GoogleSignInAccount googleAccount;
    private boolean isGoogleLoggedIn = false;
    private ImageView googleImageView;
    private Bitmap googleProfileBitMap;
    private boolean hasCustomProfilePicture = false;
    private Bitmap customProfilePicture;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setHasCustomProfilePicture(boolean hasCustomProfilePicture){
        this.hasCustomProfilePicture = hasCustomProfilePicture;
    }

    public boolean isHasCustomProfilePicture(){return hasCustomProfilePicture;}

    public Bitmap getCustomProfilePicture() {
        return customProfilePicture;
    }

    public void setCustomProfilePicture(Bitmap customProfilePicture) {
        this.customProfilePicture = customProfilePicture;
    }

    private String firstName,lastName;
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

    public void setGoogleProfileBitMap(Bitmap bm){
        googleProfileBitMap = bm;
    }

    public Bitmap getGoogleProfileBitMap(){
        return googleProfileBitMap;
    }

    public void signInWithGoogle(GoogleSignInAccount googleAccount,Context context){
        //check if authed through Jarvis Auth
        if(this.isLoggedIn){
            this.logOutUser(context);
        }

        this.googleAccount = googleAccount;
        this.isGoogleLoggedIn = true;
    }

    public String getEmail(){
        if(this.isGoogleLoggedIn()){
            return this.getGoogleAccount().getEmail();
        }
        else if(this.isLoggedIn){
            return this.getCredentials().getEmail();
        }
        else{return null;}
    }

    public boolean checkTotalLoginIn(){
        if(this.isGoogleLoggedIn() || this.isLoggedIn){
            return true;
        }
        else return false;
    }

    public boolean isGoogleLoggedIn(){return isGoogleLoggedIn;}

    public void signOutWithGoogle(){
        this.googleAccount = null;
        this.googleImageView = null;
        this.isGoogleLoggedIn = false;
        this.googleProfileBitMap = null;
    }

    public void setGoogleProfileImage(ImageView image){
        googleImageView = image;
    }

    public ImageView getGoogleImageView(){
        return googleImageView;
    }

    public GoogleSignInAccount getGoogleAccount(){
        return googleAccount;
    }

    //will return null if no user logged in
    public User getUserForSplash(Context context){
        UserLoginDataSource db = new UserLoginDataSource(context);
        try{
            db.open();
            User user = db.getCurrentUserIfExists();
            db.close();
            if(!user.getEmail().equals(" ")) {
                return user;
            }
            else{return null;}
        }
        catch(SQLDataException | NullPointerException e){
            return null;
        }
    }



    public boolean getIsLoggedIn(){return isLoggedIn;}

    public boolean logOutUser(Context context){
        isLoggedIn = false;
        UserLoginDataSource db = new UserLoginDataSource(context);
        try{
            db.open();
            db.updateFlagForUser(email, 0);
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
            int count = db.getUserCount(this.email);
            Log.d(TAG, "insertLocalInformationForLogin count = " + count);
            if(count > 0){
                Log.d(TAG, "insertLocalInformationForLogin auth = " + this.isLoggedIn);
                if(this.isLoggedIn){
                    db.updateFlagForUser(this.email,1);
                    db.close();
                    return;
                }
                else{
                    db.updateFlagForUser(this.email,0);
                    db.close();
                    return;
                }
            }
            db.insertUserNameAndPassword(this.email, this.pw);
            if (!isLoggedIn) {
                db.updateFlagForUser(email, 0);
            }
            else{
                db.updateFlagForUser(email,1);
            }
            db.close();
            //userName = email;
            //pw = password;
        }
        catch (SQLDataException e){
            e.getMessage();
            e.printStackTrace();
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
