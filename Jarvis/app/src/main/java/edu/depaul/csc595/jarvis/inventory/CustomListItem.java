package edu.depaul.csc595.jarvis.inventory;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Advait on 15-02-2016.
 */
public class CustomListItem extends FrameLayout
{
    private JBHorizontalSwipe mJBHorizontalSwipe;

    public CustomListItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CustomListItem(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (this.mJBHorizontalSwipe != null)
            this.mJBHorizontalSwipe.onScrollerDispatchTouchEventListener(this, ev);

        return super.onTouchEvent(ev);
    }

    /**
     * Sets a reference to a JBHorizontalSwipe controller.
     * @param jbHorizontalSwipe The instance of a JBHorizontalSwipe controller.
     */
    public void setJBHeaderRef(JBHorizontalSwipe jbHorizontalSwipe)
    {
        this.mJBHorizontalSwipe = jbHorizontalSwipe;
    }
}
