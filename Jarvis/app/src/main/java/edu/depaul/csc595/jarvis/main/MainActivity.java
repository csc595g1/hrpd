package edu.depaul.csc595.jarvis.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.detection.gcm.TokenIntentService;
import edu.depaul.csc595.jarvis.detection.gcm.TokenUpdateIntentService;
import edu.depaul.csc595.jarvis.inventory.AppliancesActivity;
import edu.depaul.csc595.jarvis.main.adapters.MainCardViewAdapter;
import edu.depaul.csc595.jarvis.main.card_view_model.CardViewModel;
import edu.depaul.csc595.jarvis.prevention.PreventionActivity;
import edu.depaul.csc595.jarvis.profile.LogInActivity;
import edu.depaul.csc595.jarvis.profile.ProfileActivity;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.reminders.ReminderActivity;
import edu.depaul.csc595.jarvis.rewards.RewardsActivity;
import edu.depaul.csc595.jarvis.settings.SettingsActivity;
import edu.depaul.csc595.jarvis.detection.DetectionBaseActivity;




public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private View headerLayout;
    private TextView tv_email;
    private TextView tv_name;
    private TextView tv_logout;
    private ImageView iv_image;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        //drawer.
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //going to inflate the header view at runtime instead of in the layout so that we
        //can modify it --ed
        headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        //ImageView img = (ImageView)navigationView.findViewById(R.id.imageView);
        navigationView.setNavigationItemSelectedListener(this);
        tv_logout = (TextView)headerLayout.findViewById(R.id.nav_header_main_logout);
        tv_email = (TextView)headerLayout.findViewById(R.id.nav_header_main_email);
        tv_name = (TextView)headerLayout.findViewById(R.id.nav_header_main_person_name);
        iv_image = (ImageView)headerLayout.findViewById(R.id.imageView);

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, TokenIntentService.class);
            startService(intent);
        }
        else {
            Toast.makeText(MainActivity.this, "No compatible GooglePlayServices APK found!", Toast.LENGTH_SHORT).show();
        }

        //cardview implementation
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.activity_main_recycler_view);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.
        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        MainCardViewAdapter adapter = new MainCardViewAdapter(createCardList());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d("Main", "Logged in :" + UserInfo.getInstance().getIsLoggedIn());
        //if user is logged in, set name, email and enable log out
        if(UserInfo.getInstance().getIsLoggedIn()){
//            tv_email = (TextView)headerLayout.findViewById(R.id.nav_header_main_email);
//            tv_name = (TextView)headerLayout.findViewById(R.id.nav_header_main_person_name);
            //tv_logout = (TextView)headerLayout.findViewById(R.id.nav_header_main_logout);
            tv_logout.setText("Not " + UserInfo.getInstance().getFirstName() + "?");
            tv_name.setText(UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName());
            tv_email.setText(UserInfo.getInstance().getCredentials().getEmail());
            iv_image.setImageBitmap(UserInfo.getInstance().getGoogleProfileBitMap());
            tv_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserInfo.getInstance().logOutUser(MainActivity.this);
                    MainActivity.this.recreate();
                }
            });
        }
        else if(UserInfo.getInstance().isGoogleLoggedIn()){
            //if(null != UserInfo.getInstance().getGoogleAccount().getEmail()) {
            try {
                tv_email.setText(UserInfo.getInstance().getGoogleAccount().getEmail());
                tv_name.setText(UserInfo.getInstance().getGoogleAccount().getDisplayName());
                tv_logout.setText("Not " + UserInfo.getInstance().getGoogleAccount().getDisplayName() + "?");
                tv_logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserInfo.getInstance().signOutWithGoogle();
                        MainActivity.this.recreate();
                    }
                });
            }
            catch(NullPointerException e){
                tv_logout.setText(" ");
            }
        }
        else{
            tv_logout.setText(" ");
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();

//        //if user is logged in, set name, email and enable log out
//        if(UserInfo.getInstance().getIsLoggedIn()){
//            tv_email = (TextView)headerLayout.findViewById(R.id.nav_header_main_email);
//            tv_name = (TextView)headerLayout.findViewById(R.id.nav_header_main_person_name);
//            tv_logout = (TextView)headerLayout.findViewById(R.id.nav_header_main_logout);
//            tv_logout.setText("Not " + UserInfo.getInstance().getFirstName() + "?");
//            tv_name.setText(UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName());
//            tv_email.setText(UserInfo.getInstance().getCredentials().getEmail());
//
//            tv_logout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    UserInfo.getInstance().logOutUser(MainActivity.this);
//                    MainActivity.this.recreate();
//                }
//            });
//        }
//        else{
//            tv_logout.setText(" ");
//        }
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
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private List<CardViewModel> createCardList(){
        List<CardViewModel> list = new ArrayList<CardViewModel>();
        CardViewModel cm = new CardViewModel();
        cm.title = "MyCommunity";
        cm.content = "Connect with others to solve common homeowner problems.";
        list.add(cm);

        cm = new CardViewModel();
        cm.title = "MyReward";
        cm.content = "Your balance is currently 0 \n";
        cm.content += "Check out what you can get!";
        list.add(cm);

        cm = new CardViewModel();
        cm.title = "MyConnectHome";
        cm.content = "Take a peek at your appliances to make sure they're working correctly.";
        list.add(cm);

        return list;
    }
}
