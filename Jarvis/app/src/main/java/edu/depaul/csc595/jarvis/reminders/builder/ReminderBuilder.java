package edu.depaul.csc595.jarvis.reminders.builder;

import android.content.Context;

import java.util.Arrays;
import java.util.Calendar;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.reminders.database.DatabaseHelper;
import edu.depaul.csc595.jarvis.reminders.models.Reminder;
import edu.depaul.csc595.jarvis.reminders.utils.DateAndTimeUtil;

/**
 * Created by uchennafokoye on 2/28/16.
 */
public class ReminderBuilder {

    private static Calendar mCalendar = Calendar.getInstance();
    private static int mTimesShown;
    private static int mTimesToShow;
    private static int mRepeatType;
    private static boolean[] mDaysOfWeek;
    private static String mIcon;
    private static String mColour;
    private static boolean newNotification;


    public static Reminder buildBasicReminder(Context context) {
            //if (getSupportActionBar() != null)
            //getSupportActionBar().setTitle(getResources().getString(R.string.create_notification));
            DatabaseHelper database = DatabaseHelper.getInstance(context);
            int mId = database.getLastNotificationId() + 1;
            mCalendar.set(Calendar.SECOND, 0);
            newNotification = true;
            mRepeatType = 0;
            mTimesShown = 0;
            mTimesToShow = 1;
            mDaysOfWeek = new boolean[7];
            mIcon = "ic_notifications_white_24dp";
            mColour = "#9E9E9E";
        Arrays.fill(mDaysOfWeek, false);
            Reminder reminder = new Reminder()
                .setId(mId)
                .setTitle("")
                .setContent("")
                .setDateAndTime(DateAndTimeUtil.toStringDate(mCalendar) + DateAndTimeUtil.toStringTime(mCalendar))
                .setRepeatType(mRepeatType)
                .setForeverState(Boolean.toString(false))
                .setNumberToShow(1)
                .setNumberShown(mTimesShown)
                .setIcon(mIcon)
                .setColour(mColour);


            return reminder;
    }

}
