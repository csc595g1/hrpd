package edu.depaul.csc595.jarvis.appintro.main.indicators;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.appintro.library.AppIntro2;
import edu.depaul.csc595.jarvis.appintro.main.activities.AppIntroMainActivity;
import edu.depaul.csc595.jarvis.appintro.main.activities.SampleSlide;


public class CustomColorIndicator  extends AppIntro2 {
    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(SampleSlide.newInstance(R.layout.for_app_intro_intro));
        addSlide(SampleSlide.newInstance(R.layout.for_app_intro_intro2));
        addSlide(SampleSlide.newInstance(R.layout.for_app_intro_intro3));
        addSlide(SampleSlide.newInstance(R.layout.for_app_intro_intro4));

        setIndicatorColor(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"));
    }

    private void loadMainActivity() {
        Intent intent = new Intent(this, AppIntroMainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {

    }

    public void getStarted(View v) {
        loadMainActivity();
    }
}