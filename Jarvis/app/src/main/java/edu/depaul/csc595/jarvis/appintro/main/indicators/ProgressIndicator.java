package edu.depaul.csc595.jarvis.appintro.main.indicators;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.appintro.library.AppIntro;
import edu.depaul.csc595.jarvis.appintro.main.activities.AppIntroMainActivity;
import edu.depaul.csc595.jarvis.appintro.main.activities.SampleSlide;


public class ProgressIndicator extends AppIntro {
    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(SampleSlide.newInstance(R.layout.for_app_intro_intro));
        addSlide(SampleSlide.newInstance(R.layout.for_app_intro_intro2));
        addSlide(SampleSlide.newInstance(R.layout.for_app_intro_intro3));
        addSlide(SampleSlide.newInstance(R.layout.for_app_intro_intro4));

        setProgressIndicator();
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, AppIntroMainActivity.class);
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
