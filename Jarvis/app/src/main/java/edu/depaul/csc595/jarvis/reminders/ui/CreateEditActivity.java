package edu.depaul.csc595.jarvis.reminders.ui;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.depaul.csc595.jarvis.R;

/**
 * Created by Advait on 18-02-2016.
 */
public class CreateEditActivity extends AppCompatActivity
{

    @Bind(R.id.create_coordinator) CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.notification_title) EditText mTitleEditText;
    @Bind(R.id.notification_content) EditText mContentEditText;
    @Bind(R.id.time)
    TextView mTimeText;
    @Bind(R.id.date) TextView mDateText;
    @Bind(R.id.repeat_day) TextView mRepeatText;
    @Bind(R.id.switch_toggle)
    SwitchCompat mForeverSwitch;
    @Bind(R.id.show_times_number) EditText mTimesEditText;
    @Bind(R.id.forever_row)
    TableRow mForeverRow;
    @Bind(R.id.bottom_row) TableRow mBottomRow;
    @Bind(R.id.bottom_view)
    View mBottomView;
    @Bind(R.id.show) TextView mShowText;
    @Bind(R.id.times) TextView mTimesText;
    @Bind(R.id.select_icon_text) TextView mIconText;
    @Bind(R.id.select_colour_text) TextView mColourText;
    @Bind(R.id.colour_icon)
    ImageView mImageColourSelect;
    @Bind(R.id.selected_icon) ImageView mImageIconSelect;
    @Bind(R.id.error_time) ImageView mImageWarningTime;
    @Bind(R.id.error_date) ImageView mImageWarningDate;
    @Bind(R.id.error_show) ImageView mImageWarningShow;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private AlertDialog mIconSelectorDialog;
    private AlertDialog mColourSelectorDialog;
    private Calendar mCalendar;
    private int mTimesShown;
    private int mRepeatType;
    private boolean[] mDaysOfWeek;
    private String mIcon;
    private String mColour;
    private boolean newNotification;
    private int mId;
    private String[] mColourNames;
    private String[] mColoursArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_reminder_create);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);

        mColourNames = getResources().getStringArray(R.array.colour_names_array);
        mColoursArray = getResources().getStringArray(R.array.colours_array);

        mCalendar = Calendar.getInstance();
        mId = getIntent().getIntExtra("NOTIFICATION_ID", 0);



    }
}
