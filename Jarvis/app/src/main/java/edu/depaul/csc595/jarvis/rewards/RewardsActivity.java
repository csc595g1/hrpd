package edu.depaul.csc595.jarvis.rewards;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.appliances.main.AppliancesActivity;
import edu.depaul.csc595.jarvis.detection.DetectionBaseActivity;
import edu.depaul.csc595.jarvis.main.MainActivity;
import edu.depaul.csc595.jarvis.profile.LogInActivity;
import edu.depaul.csc595.jarvis.profile.ProfileActivity;
import edu.depaul.csc595.jarvis.profile.user.GoogleImage;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.reminders.main.ReminderActivity;
import edu.depaul.csc595.jarvis.settings.SettingsActivity;

public class RewardsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private View headerLayout;
    private TextView tv_email;
    private TextView tv_name;
    private TextView tv_logout;
    private ImageView iv_image;
    private ImageView iv_image_profile;
    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private int numOfPages = 2;
//    private Rewards rewards;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        //if not logged in, send to login page.
        if(!UserInfo.getInstance().isGoogleLoggedIn() && !UserInfo.getInstance().getIsLoggedIn()){
            Intent intent = new Intent(RewardsActivity.this,LogInActivity.class);
            startActivity(intent);
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.rewards_tango_text, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        drawer.setEnabled(true);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_detection);
        navigationView.setNavigationItemSelectedListener(this);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        rewards = new Rewards();

//        rewards.sendGET();

        //test async
//        CreateRewardEventModel event = new CreateRewardEventModel();
//        event.setEventCategory("test");
//        event.setTitle("testevent25");
//        event.setUnits(6);
//        if(UserInfo.getInstance().isGoogleLoggedIn()) {
//            event.setUserId(UserInfo.getInstance().getGoogleAccount().getEmail());
//            CreateRewardEventAsyncTask task = new CreateRewardEventAsyncTask();
//            task.execute(this,event);
//        }
//        else if(UserInfo.getInstance().getIsLoggedIn()) {
//            event.setUserId(UserInfo.getInstance().getCredentials().getEmail());
//            CreateRewardEventAsyncTask task = new CreateRewardEventAsyncTask();
//            task.execute(this, event);
//        }
    }


    protected void onStart(){
        super.onStart();

        Log.d("Rewards", "Logged in :" + UserInfo.getInstance().getIsLoggedIn());
        //if user is logged in, set name, email and enable log out
        if(UserInfo.getInstance().getIsLoggedIn()){
            tv_email = (TextView)headerLayout.findViewById(R.id.nav_header_main_email);
            tv_name = (TextView)headerLayout.findViewById(R.id.nav_header_main_person_name);
            //tv_logout = (TextView)headerLayout.findViewById(R.id.nav_header_main_logout);
            // tv_logout.setText("Not " + UserInfo.getInstance().getFirstName() + "?");
            tv_name.setText(UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName());
            tv_email.setText(UserInfo.getInstance().getCredentials().getEmail());

        }
        else if(UserInfo.getInstance().isGoogleLoggedIn()){
            //if(null != UserInfo.getInstance().getGoogleAccount().getEmail()) {
            try {
                //iv_image = (ImageView)headerLayout.findViewById(R.id.imageView);
                // iv_image_profile = (ImageView)findViewById(R.id.imageView_profile);
                tv_email = (TextView)headerLayout.findViewById(R.id.nav_header_main_email);
                tv_name = (TextView)headerLayout.findViewById(R.id.nav_header_main_person_name);
                tv_email.setText(UserInfo.getInstance().getGoogleAccount().getEmail());
                tv_name.setText(UserInfo.getInstance().getGoogleAccount().getDisplayName());

                boolean photonotnull = false;
                try{
                    String test = UserInfo.getInstance().getGoogleAccount().getPhotoUrl().toString();
                    photonotnull = true;
                }
                catch (NullPointerException e){}
                if(photonotnull) {
                    //google image
                    iv_image = (ImageView)headerLayout.findViewById(R.id.imageView);
                    iv_image_profile = (ImageView)findViewById(R.id.imageView_profile);
                    GoogleImage img = new GoogleImage();
                    img.execute(iv_image, RewardsActivity.this);
                    GoogleImage img2 = new GoogleImage();
                    img2.execute(iv_image_profile, RewardsActivity.this);
                }

            }
            catch(NullPointerException e){
                //tv_logout.setText(" ");
            }
        }
        else{
            //tv_logout.setText(" ");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rewards, menu);
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
            return true;
        } else if (item.getItemId() == 1) {
            // ( 1 ) add a new item 
            // ( 2 ) change add to remove
        } else {
            // if a the new item is clicked show "Toast" message.
//            Context context = getApplicationContext();
//            CharSequence text = "Hello toast!";
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_rewards, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.rewards_section_text, getArguments().getInt(ARG_SECTION_NUMBER)));

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:
                    try {
                        textView.setText(getString(R.string.rewards_section_text, getArguments().getInt(ARG_SECTION_NUMBER)));
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.toString());
                    }
                    break;
//                case 2:
//                    try {
//                        textView.setText(getString(R.string.rewards_section_text, getArguments().getInt(ARG_SECTION_NUMBER)));
//                    } catch (Exception e) {
//                        System.out.println("ERROR: " + e.toString());
//                    }
//                    break;
                case 1:
                    try {
                        textView.setText(getString(R.string.rewards_section_text, getArguments().getInt(ARG_SECTION_NUMBER)));
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.toString());
                    }
                    break;
//                case 4:
//                    try {
//                        textView.setText(getString(R.string.rewards_section_text, getArguments().getInt(ARG_SECTION_NUMBER)));
//                    } catch (Exception e) {
//                        System.out.println("ERROR: " + e.toString());
//                    }
//                    break;
                default:
                    try {
                        textView.setText(getString(R.string.rewards_section_text, 3));
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.toString());
                    }
                    break;
            }


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position) {
                case 0:
                    return new RewardBalanceFragment();
                case 1:
                    return new RewardOrderFragment();
                default:
                    return PlaceholderFragment.newInstance(position + 1);
            }
        }


        @Override
        public int getCount() {
            return numOfPages;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                //case 0:
                //    return (CharSequence) getString(R.string.rewards_button_account_create);
                //return "SECTION 1";
                case 0:
                    return (CharSequence) getString(R.string.rewards_button_account_balance);
                //return "SECTION 2";
                //case 2:
                //    return (CharSequence) getString(R.string.rewards_button_account_update);
                //return "SECTION 3";
                case 1:
                    return (CharSequence) getString(R.string.rewards_button_redeem_points);
                //return "SECTION 4";
            }
            return null;
        }
    }

    //Get data and return JSONObject
    private void convertDataToJSON() {
        String response = "";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("response", response);
        }
        catch (Exception e) {e.printStackTrace(); }


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
                    UserInfo.getInstance().logOutUser(RewardsActivity.this);
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

}
