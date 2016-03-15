package edu.depaul.csc595.jarvis.appintro.main.animations;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.appintro.main.activities.SampleSlide;
import edu.depaul.csc595.jarvis.appliances.main.AppliancesActivity;

/**
 * Created by rohit on 22/7/15.
 */
public class DepthAnimation extends BaseAppIntro {

    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(SampleSlide.newInstance(R.layout.for_app_intro_intro));
        addSlide(SampleSlide.newInstance(R.layout.for_app_intro_intro2));
        addSlide(SampleSlide.newInstance(R.layout.for_app_intro_intro3));
        addSlide(SampleSlide.newInstance(R.layout.for_app_intro_intro4));

        setDepthAnimation();
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, AppliancesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
        Toast.makeText(getApplicationContext(), getString(R.string.skip), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {

    }

    public void getStarted(View v){
        loadMainActivity();
    }
}
