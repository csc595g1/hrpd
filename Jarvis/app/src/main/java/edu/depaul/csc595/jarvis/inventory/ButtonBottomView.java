package edu.depaul.csc595.jarvis.inventory;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by Advait on 15-02-2016.
 */
public class ButtonBottomView extends Button {
    private final String TAG_LOG = "ButtonBottomView";

    private boolean mIgnoreMotionEvents = true;

    public ButtonBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Enables or disables the button from responding to touch events.
     *
     * @param ignore If set to true, touch events will be ignored by the button.
     */
    public void setIgnoreMotionEvents(boolean ignore) {
        mIgnoreMotionEvents = ignore;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (mIgnoreMotionEvents) {
            super.onTouchEvent(event);
            return false;
        }

        return super.onTouchEvent(event);
    }
}

