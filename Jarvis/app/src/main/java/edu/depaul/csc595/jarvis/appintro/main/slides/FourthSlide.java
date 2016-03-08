package edu.depaul.csc595.jarvis.appintro.main.slides;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.depaul.csc595.jarvis.R;


public class FourthSlide extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.for_app_intro_intro4, container, false);
        return v;
    }
}
