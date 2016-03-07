package edu.depaul.csc595.jarvis.rewards.Models;

import android.util.Log;

import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Ed on 2/26/2016.
 */
public class RewardBalanceModel {
    public String eventId;
    public String eventCatrgory;
    public String title;
    public int units;
    public String dttm;

    public RewardBalanceModel(){}

    public String getFormattedDttm(){
        String formattedDttm = "";
        String tempdttm = dttm.substring(0,10);
        String mn = tempdttm.substring(5,7);
        String yr = tempdttm.substring(0,4);
        String dy= tempdttm.substring(8, 10);
        String tm = dttm.substring(11).trim();
        String hr = tm.substring(0, 2);
        String min = tm.substring(3,5);
        if(Integer.parseInt(hr) > 12){
            hr = String.valueOf(Integer.parseInt(hr) / 2);
        }
        String day = "";
        LocalDate dt = new LocalDate(Integer.parseInt(yr),Integer.parseInt(mn),Integer.parseInt(dy));
        Log.d("RewardBalModel", "getFormattedDttm " + dt.getDayOfWeek());
        //int dayInt = dt.getDayOfWeek();
        switch (dt.getDayOfWeek()){
            case 1:
                day = "Monday";
                break;
            case 2:
                day = "Tuesday";
                break;
            case 3:
                day = "Wednesday";
                break;
            case 4:
                day = "Thursday";
                break;
            case 5:
                day = "Friday";
                break;
            case 6:
                day = "Saturday";
                break;
            case 7:
                day = "Sunday";
                break;
            default:
                day = " ";
        }

        if((LocalDate.now().getYear() - dt.getYear()) == 0){
            if((LocalDate.now().getMonthOfYear() - dt.getMonthOfYear() == 0)){
                if((LocalDate.now().getDayOfMonth() - dt.getDayOfMonth()) <= 0){
                    if(LocalDate.now().getDayOfMonth() == dt.getDayOfMonth()){
                        formattedDttm = "Today at " + hr + ":" + min;
                        return formattedDttm;
                    }
                    else {
                        formattedDttm = "Yesterday at " + hr + ":" + min;
                        return formattedDttm;
                    }
                }
                else{
                    formattedDttm = day;
                }
            }
            else{
                formattedDttm = day;
            }
        }
        else{
            formattedDttm = day;
        }

        formattedDttm += ", " + mn + "/" + dy + "/" + yr;
        return formattedDttm;
    }
}
