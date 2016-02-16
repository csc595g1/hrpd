package main.models;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author Ed M
 */
public class UserLoginDB {

    private final String TABLE_NAME_USER = "user_login_tbl";
    private final String TABLE_NAME_USER_PERS_INFO = "user_personal_data_tbl";
    Connection connection;
    
    public UserLoginDB(){
        this.createUserTable();
        this.createUserPersonalInfoTable();
    }
    
    public boolean createAccount(String email,String pw, String firstName, String lastName){
        boolean returnBool = false;
        int count = 0;
        try{
            connection = getConnection();
            
            String sql = "select count(*) AS retcount from " + TABLE_NAME_USER + " where user_email = '" + email + "';";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                count = rs.getInt("retCount");
            }
            if(count == 0){
                sql = "insert into " + TABLE_NAME_USER + " values ('"+email+"','"+pw+"',CURRENT_TIMESTAMP);";
                statement = connection.createStatement();
                statement.execute(sql);
                
//                if(returnBool != true){
//                    System.out.println("Exiting createaccount method");
//                    return false;
//                }
                statement.clearBatch();
                statement.close();
                connection.close();
                
                connection = getConnection();
                System.out.println("Inserting into " + TABLE_NAME_USER_PERS_INFO + " values " + email+" " + firstName+" " + lastName);
                sql = "insert into " + TABLE_NAME_USER_PERS_INFO + " values ('"+email+"','"+firstName+"','"+lastName+"');";
                statement = connection.createStatement();
                statement.execute(sql);
                
                returnBool = true;
            }
            connection.close();
        }
        catch(URISyntaxException e){
            e.getMessage();
            e.printStackTrace();}
        catch(SQLException e){
            e.getMessage();
            e.printStackTrace();}
        
        System.out.println("ReturnBool for createAccount = " + returnBool);
        return returnBool;
    }
    
    public UserLogin getUserInformation(String email){
        UserLogin temp;
        String fn = "";
        String ln = "";
        String sql = "select user_first_name, user_last_name from " + TABLE_NAME_USER_PERS_INFO + " where user_email = '" + email + "';";
        try{
            connection = getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                fn = rs.getString(1);
                ln = rs.getString(2);
            }
            temp = new UserLogin(fn,ln,true);
            connection.close();
            return temp;
        }
        catch(URISyntaxException e){
            e.getMessage();
            e.printStackTrace();
            return null;
        }
        catch(SQLException e){
            e.getMessage();
            e.printStackTrace();
            return null;
        }
    }
    
    public AuthMessage authUser(String user, String pw){
        boolean auth = false;
        AuthMessage authm = new AuthMessage();
        //check if user_name exists
        String sql = "select count(*) AS retcount from " + TABLE_NAME_USER + " where user_email = '" + user + "';";
        int count = 0;
        try{
            connection = getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                count = rs.getInt("retCount");
            }
            connection.close();
            
            stmt.clearBatch();
            stmt.close();
            if(count > 0){
                String temppw = "";
                sql = "select user_pw from " + TABLE_NAME_USER + " where user_email = '" + user + "';";
                connection = getConnection();
                stmt = connection.createStatement();
                ResultSet r = stmt.executeQuery(sql);
                while(r.next()){
                    temppw = r.getString(1);
                }
                connection.close();
                if(pw.equals(temppw)){
                    return new AuthMessage(true,"successful login");
                }
                else{
                    return new AuthMessage(false,"incorrect password");
                }
            }
            else{
                connection.close();
                return new AuthMessage(false,"No User " + user + " exists");
            }
        }
        catch(URISyntaxException e){
            e.getMessage();
            e.printStackTrace();}
        catch(SQLException e){
            e.getMessage();
            e.printStackTrace();
        }
        
        return new AuthMessage(false,"Incorrect Password/User");
    }
    
    //public void insertUserAcctRow()
    
    //create if not exitsts
    private void createUserTable(){
        
        try{
        connection = getConnection();
        String sql = "create table if not exists " + TABLE_NAME_USER 
                + "(user_email VARCHAR(30) PRIMARY KEY NOT NULL,"
                + "user_pw VARCHAR(20) NOT NULL,"
                + "last_auth timestamp);";
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        connection.close();
        }
        catch(URISyntaxException e){
            e.getMessage();
            e.printStackTrace();
        }
        catch(SQLException e){
            e.getMessage();
            e.printStackTrace();
        }
    }
    
    private void createUserPersonalInfoTable(){
        try{
        connection = getConnection();
        String sql = "create table if not exists " + TABLE_NAME_USER_PERS_INFO 
                + "(user_email VARCHAR(30) PRIMARY KEY NOT NULL,"
                + "user_first_name VARCHAR(30) NOT NULL,"
                + "user_last_name VARCHAR(30) NOT NULL);";
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        connection.close();
        }
        catch(URISyntaxException e){
            e.getMessage();
            e.printStackTrace();}
        catch(SQLException e){
            e.getMessage();
            e.printStackTrace();}
    }
    
    private static Connection getConnection() throws URISyntaxException, SQLException {
        
        //production code
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
        
        //test - COMMENT OUT and use above code when deploying
        //URI dbUri = new URI("postgres://thuckapxnbbplj:y7Q-HMUC3AV3jN1k1cIDrWO6RW@ec2-54-225-215-233.compute-1.amazonaws.com:5432/ddu66lf1nisfq8");
        
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();
        
//        Properties props = new Properties();
//        props.setProperty("user",username);
//        props.setProperty("password", password);
//        props.setProperty("ssl", "true");
        
        return DriverManager.getConnection(dbUrl, username, password);
        //return DriverManager.getConnection(dbUrl,props);
       
    }
}
