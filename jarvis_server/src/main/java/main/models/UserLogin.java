package main.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;
import java.util.TimeZone;

@XmlRootElement
public class UserLogin {

    private String user;
    private String pw;
    public String fn, ln;
    private final boolean loggedIn;
    
    public UserLogin(String fn, String ln, boolean isForName){
        this.fn = fn;
        this.ln = ln;
        loggedIn = false;
    }
    
    public UserLogin() {
        //this(TimeZone.getDefault());
        this.loggedIn = false;
    }

    public UserLogin(String user, String pw) {
        this.user = user;
        this.pw = pw;
        this.loggedIn = new UserLoginDB().authUser(user, pw).auth; //replace 'true' with method checking user/pw
    }

    public String getUser(){
        return user;
    }
    
    public boolean isLoggedIn(){
        return loggedIn;
    }
    
    private boolean getAuth(String user, String pw){
        return false;
    }
    
    public String getfn(){return fn;}
    public String getln(){return ln;}
}
