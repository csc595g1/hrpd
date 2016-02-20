package edu.depaul.csc595.jarvis.profile.user;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ed on 1/30/2016.
 */
public class UserLoginDataHelper extends SQLiteOpenHelper{

    protected static final String TABLE_NAME = "user_login_tbl";

    protected static final String user_email = "user_email";
    protected static final String user_pw = "user_pw";
    protected static final String last_login_dttm = "last_login_dttm";
    protected static String is_logged_on = "is_logged_on";

    protected static final String DB_NAME = "user_login_info.db";

    static final int DB_VERSION = 1;

    protected UserLoginDataHelper(Context context){super(context,DB_NAME,null,DB_VERSION);}

    private static final String CREATE_TABLE =
            "create table if not exists " + TABLE_NAME + " (" +
                    user_email + " TEXT NOT NULL, " +
                    user_pw + " TEXT NOT NULL, " +
                    last_login_dttm + " DATETIME, " +
                    is_logged_on + " INTEGER);";

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
}
