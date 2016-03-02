package edu.depaul.csc595.jarvis.detection;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.appliances.main.AppliancesActivity;
import edu.depaul.csc595.jarvis.main.MainActivity;
import edu.depaul.csc595.jarvis.profile.LogInActivity;
import edu.depaul.csc595.jarvis.profile.ProfileActivity;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.reminders.main.ReminderActivity;
import edu.depaul.csc595.jarvis.rewards.RewardsActivity;
import edu.depaul.csc595.jarvis.settings.SettingsActivity;


public class DetectionBaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private View headerLayout;
    private TextView tv_email;
    private TextView tv_name;
    private String email;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    public static String EMAIL_EXTRA = "Email Address";

    private String LOG_TAG = "DetectionBaseActivity";


    public String getEmail() {
        return email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_detection_base);
        ButterKnife.bind(this);

        //check login, send to login if not logged in
        if(!UserInfo.getInstance().isGoogleLoggedIn() && !UserInfo.getInstance().getIsLoggedIn()){
            Intent intent = new Intent(DetectionBaseActivity.this,LogInActivity.class);
            startActivity(intent);
            finish();
        }

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        drawer.setEnabled(true);
        toggle.syncState();

        headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_detection);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager.setAdapter(new DetectionFragmentPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d("Main", "Logged in :" + UserInfo.getInstance().getIsLoggedIn());
        //if user is logged in, set name, email and enable log out
        if(UserInfo.getInstance().getIsLoggedIn()){
            tv_email = (TextView)headerLayout.findViewById(R.id.nav_header_main_email);
            tv_name = (TextView)headerLayout.findViewById(R.id.nav_header_main_person_name);
            tv_name.setText(UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName());
            tv_email.setText(UserInfo.getInstance().getCredentials().getEmail());
            email = UserInfo.getInstance().getCredentials().getEmail();
        }
        else if(UserInfo.getInstance().isGoogleLoggedIn()){
            tv_email = (TextView)headerLayout.findViewById(R.id.nav_header_main_email);
            tv_name = (TextView)headerLayout.findViewById(R.id.nav_header_main_person_name);
            tv_email.setText(UserInfo.getInstance().getGoogleAccount().getEmail());
            tv_name.setText(UserInfo.getInstance().getGoogleAccount().getDisplayName());
            email = UserInfo.getInstance().getGoogleAccount().getEmail();
        } else {
            email = "test1@test.com";
        }

    }

    @Override
    protected void onRestart(){
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent settings = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settings);
            finish();

        }

        return super.onOptionsItemSelected(item);
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
            /*
            case R.id.nav_prevention:
                goToActivity = new Intent(getApplicationContext(), PreventionActivity.class);
                break;
                */
            case R.id.nav_logout:
                if(UserInfo.getInstance().getIsLoggedIn()) {
                    UserInfo.getInstance().logOutUser(DetectionBaseActivity.this);
                    this.recreate();
                }
                else if(UserInfo.getInstance().isGoogleLoggedIn()){
                    UserInfo.getInstance().signOutWithGoogle();
                    this.recreate();
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


}
