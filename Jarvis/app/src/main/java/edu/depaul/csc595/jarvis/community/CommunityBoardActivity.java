package edu.depaul.csc595.jarvis.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.appliances.main.AppliancesActivity;
import edu.depaul.csc595.jarvis.detection.DetectionBaseActivity;
import edu.depaul.csc595.jarvis.main.MainActivity;
import edu.depaul.csc595.jarvis.profile.LogInActivity;
import edu.depaul.csc595.jarvis.profile.ProfileActivity;
import edu.depaul.csc595.jarvis.profile.user.GoogleImage;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.reminders.main.ReminderActivity;
import edu.depaul.csc595.jarvis.rewards.RewardsActivity;
import edu.depaul.csc595.jarvis.settings.SettingsActivity;

/**
 * Created by Ed on 3/7/2016.
 */
public class CommunityBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,PostDialogFragment.OnPostDialogResultListener{

    private View headerLayout;
    private TextView tv_email;
    private TextView tv_name;
    private TextView tv_logout;
    private ImageView iv_image;
    private ImageView iv_image_profile;
    public static CommunityBoardActivity activity;

    protected void onCreate(Bundle savedInstance){
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Slide());
        getWindow().setEnterTransition(new Slide());
        super.onCreate(savedInstance);
        super.setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_community_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.community_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        drawer.setEnabled(true);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_detection);
        navigationView.setNavigationItemSelectedListener(this);

        activity = CommunityBoardActivity.this;
    }


    protected void onStart(){
        super.onStart();

        if(UserInfo.getInstance().getIsLoggedIn()){
            tv_email = (TextView)headerLayout.findViewById(R.id.nav_header_main_email);
            tv_name = (TextView)headerLayout.findViewById(R.id.nav_header_main_person_name);
            tv_name.setText(UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName());
            tv_email.setText(UserInfo.getInstance().getCredentials().getEmail());
        }
        else if(UserInfo.getInstance().isGoogleLoggedIn()){
            try {
                tv_email = (TextView)headerLayout.findViewById(R.id.nav_header_main_email);
                tv_name = (TextView)headerLayout.findViewById(R.id.nav_header_main_person_name);
                tv_email.setText(UserInfo.getInstance().getGoogleAccount().getEmail());
                tv_name.setText(UserInfo.getInstance().getGoogleAccount().getDisplayName());
                boolean photonotnull = false;
                try{
                    String test = UserInfo.getInstance().getGoogleAccount().getPhotoUrl().toString();
                    photonotnull = true;
                }
                catch (NullPointerException e){
                    Log.d("Profile Activity", "onStart photo is null");
                }
                if(photonotnull) {
                    iv_image = (ImageView)headerLayout.findViewById(R.id.imageView);
                    iv_image_profile = (ImageView)findViewById(R.id.imageView_profile);
                    GoogleImage img = new GoogleImage();
                    img.execute(iv_image, CommunityBoardActivity.this);
                    GoogleImage img2 = new GoogleImage();
                    img2.execute(iv_image_profile, CommunityBoardActivity.this);
                }
            }
            catch(NullPointerException e){}


        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent goToActivity = null;
        switch (id) {
            case R.id.nav_home:
                goToActivity = new Intent(getApplicationContext(), MainActivity.class);
                break;
            case R.id.nav_rewards:
                goToActivity = new Intent(getApplicationContext(), RewardsActivity.class);
                break;
            case R.id.nav_appliances:
                goToActivity = new Intent(getApplicationContext(), AppliancesActivity.class);
                break;
            case R.id.nav_profile:
                if(UserInfo.getInstance().getIsLoggedIn() || UserInfo.getInstance().isGoogleLoggedIn()) {
                    goToActivity = new Intent(getApplicationContext(), ProfileActivity.class);
                }
                else {
                    goToActivity = new Intent(getApplicationContext(), LogInActivity.class);
                }
                break;
            case R.id.nav_reminder:
                goToActivity = new Intent(getApplicationContext(), ReminderActivity.class);
                break;
            case R.id.nav_detection:
                goToActivity = new Intent(getApplicationContext(), DetectionBaseActivity.class);
                break;
            case R.id.nav_settings:
                goToActivity = new Intent(getApplicationContext(), SettingsActivity.class);
                break;
            case R.id.nav_logout:
                if(UserInfo.getInstance().getIsLoggedIn()) {
                    UserInfo.getInstance().logOutUser(CommunityBoardActivity.this);
                }
                else if(UserInfo.getInstance().isGoogleLoggedIn()){
                    UserInfo.getInstance().signOutWithGoogle();
                }
                break;
            default:
                break;
        }

        if (goToActivity != null){
            startActivity(goToActivity);
            overridePendingTransition(0, 0);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onPost(String result){
        Log.d("commboardact", "onPost in activity");
    }
}
