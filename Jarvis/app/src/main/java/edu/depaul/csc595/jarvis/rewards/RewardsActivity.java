package edu.depaul.csc595.jarvis.rewards;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.CreateRewardEventAsyncTask;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.CreateRewardEventModel;

public class RewardsActivity extends AppCompatActivity {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private int numOfPages = 4;
//    private Rewards rewards;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        rewards = new Rewards();

//        rewards.sendGET();

        //test async
        CreateRewardEventModel event = new CreateRewardEventModel();
        event.setEventCategory("test");
        event.setTitle("testevent25");
        event.setUnits(6);
        if(UserInfo.getInstance().isGoogleLoggedIn()) {
            event.setUserId(UserInfo.getInstance().getGoogleAccount().getEmail());
            CreateRewardEventAsyncTask task = new CreateRewardEventAsyncTask();
            task.execute(this,event);
        }
        else if(UserInfo.getInstance().getIsLoggedIn()) {
            event.setUserId(UserInfo.getInstance().getCredentials().getEmail());
            CreateRewardEventAsyncTask task = new CreateRewardEventAsyncTask();
            task.execute(this, event);
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
                case 1:
                    try {
                        textView.setText(getString(R.string.rewards_section_text, getArguments().getInt(ARG_SECTION_NUMBER)));
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.toString());
                    }
                    break;
                case 2:
                    try {
                        textView.setText(getString(R.string.rewards_section_text, getArguments().getInt(ARG_SECTION_NUMBER)));
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.toString());
                    }
                    break;
                case 3:
                    try {
                        textView.setText(getString(R.string.rewards_section_text, getArguments().getInt(ARG_SECTION_NUMBER)));
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.toString());
                    }
                    break;
                case 4:
                    try {
                        textView.setText(getString(R.string.rewards_section_text, getArguments().getInt(ARG_SECTION_NUMBER)));
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.toString());
                    }
                    break;
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

    //////////////////////////////////
    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CreateRewardsFragment(), "CREATE");
        adapter.addFrag(new RewardEventsFragment(), "EVENTS");
        adapter.addFrag(new CatalogRewardsFragment(), "Catalog");
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
//                    fbLeft.show();
//                    fbCenter.show();
//                    fbRight.show();
                }
                else {
//                    fbLeft.hide();
//                    fbCenter.hide();
//                    fbRight.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                viewPager.getCurrentItem();

                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        if(viewPager.getCurrentItem() != 0) {
//                            fbLeft.hide();
//                            fbCenter.hide();
//                            fbRight.hide();
                        }
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if(viewPager.getCurrentItem() == 0) {
//                            fbLeft.show();
//                            fbCenter.show();
//                            fbRight.show();
                        }
                }
            }
        });
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) { return null; }
    }

    //region TAB_VIEW
    private void setupTabIcons() {
//        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_timer_profile_24dp);
//        mTabLayout.getTabAt(1).setIcon(R.mipmap.ic_steering_wheel);
//        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_directions_car);
//        mTabLayout.getTabAt(3).setIcon(R.drawable.ic_attach_money_24dp);
    }

    /////////////////////////////////

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

            return PlaceholderFragment.newInstance(position + 1);
        }


        @Override
        public int getCount() {
            return numOfPages;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return (CharSequence) getString(R.string.rewards_button_account_create);
                //return "SECTION 1";
                case 1:
                    return (CharSequence) getString(R.string.rewards_button_account_balance);
                //return "SECTION 2";
                case 2:
                    return (CharSequence) getString(R.string.rewards_button_account_update);
                //return "SECTION 3";
                case 3:
                    return (CharSequence) getString(R.string.rewards_button_redeem_points);
                //return "SECTION 4";
            }
            return null;
        }
    }


}
