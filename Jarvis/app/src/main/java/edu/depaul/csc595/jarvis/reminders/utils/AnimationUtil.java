package edu.depaul.csc595.jarvis.reminders.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import edu.depaul.csc595.jarvis.R;

/**
 * Created by Advait on 22-02-2016.
 */
public class AnimationUtil
{
    public static void shakeView(View view, Context context) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake_view);
        view.startAnimation(shake);
    }
}
