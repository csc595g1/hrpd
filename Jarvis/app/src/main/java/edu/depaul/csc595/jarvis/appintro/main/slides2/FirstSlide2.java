package edu.depaul.csc595.jarvis.appintro.main.slides2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.depaul.csc595.jarvis.R;

public class FirstSlide2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.for_app_intro_intro2, container, false);
        return v;
    }
}
