package edu.depaul.csc595.jarvis.inventory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Advait on 15-02-2016.
 */
public class ListViewItemBackground extends FrameLayout {
    private final String LOG_TAG = "ListViewItemBackground";

    private boolean mShowing = false;
    private int mOpenAreaTop, mOpenAreaHeight;
    private boolean mUpdateBounds = false;

    private Bitmap mBitmapRowSnapshot;
    private Drawable mDrawableBackground;

    public ListViewItemBackground(Context context) {
        super(context);
    }

    public ListViewItemBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewItemBackground(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * Causes a snapshot of the top view to be taken and drawn
     * onto a canvas.
     *
     * @param v
     */
    public void showBackground(View v) {
        setWillNotDraw(false);
        mOpenAreaTop = v.getTop();
        mOpenAreaHeight = v.getHeight();
        mShowing = true;
        mUpdateBounds = true;

        this.mBitmapRowSnapshot = loadBitmapFromView(v);
    }

    /**
     * Creates a bitmap from a view.
     *
     * @param v The view from which a bitmap will be created.
     * @return A bitmap of the view is returned.
     */
    public Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

    public void hideBackground() {
        setWillNotDraw(true);
        mShowing = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
    /*
     * The canvas takes up the entire area of the listview. The row to be deleted has a snapshot image of itself
     * drawn on top of this canvas at the top of the canvas. After drawing the snapsot, the canvas is moved down
     * to the position where the row's top position is located, giving the effect that the row is still visible
     * when in reality only a snapshot is being shown.
     */

        if (mShowing) {
            if (mUpdateBounds) {
                this.mDrawableBackground = new BitmapDrawable(getResources(), this.mBitmapRowSnapshot);
                this.mDrawableBackground.setBounds(0, 0, getWidth(), mOpenAreaHeight);
            }

            canvas.save();
            canvas.translate(0, mOpenAreaTop);
            this.mDrawableBackground.draw(canvas);
            canvas.restore();
        }
    }
}
