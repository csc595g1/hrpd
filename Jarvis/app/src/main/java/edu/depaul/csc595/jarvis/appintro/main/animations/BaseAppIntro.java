package edu.depaul.csc595.jarvis.appintro.main.animations;

import android.view.Menu;
import android.view.MenuItem;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.appintro.library.AppIntro;


/**
 * Created by julio on 20/10/15.
 */
public abstract class BaseAppIntro extends AppIntro {
    private int mScrollDurationFactor = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.for_app_intro_menu_base_intro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_factor1:
                mScrollDurationFactor = 1;
                break;
            case R.id.action_factor2:
                mScrollDurationFactor = 2;
                break;
            case R.id.action_factor4:
                mScrollDurationFactor = 4;
                break;
            case R.id.action_factor6:
                mScrollDurationFactor = 6;
                break;

        }
        setScrollDurationFactor(mScrollDurationFactor);
        return super.onOptionsItemSelected(item);
    }
}
