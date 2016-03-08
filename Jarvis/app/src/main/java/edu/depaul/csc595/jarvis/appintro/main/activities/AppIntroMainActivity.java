package edu.depaul.csc595.jarvis.appintro.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.appintro.main.animations.DepthAnimation;

/**
 * Created by Advait on 07-03-2016.
 */

public class AppIntroMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.for_app_intro_activity_main);
    }

    public void startDepthAnimation(View v){
        Intent intent = new Intent(this, DepthAnimation.class);
        startActivity(intent);
    }

}