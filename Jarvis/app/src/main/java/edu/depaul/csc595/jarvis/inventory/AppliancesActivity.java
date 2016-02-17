package edu.depaul.csc595.jarvis.inventory;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import edu.depaul.csc595.jarvis.R;

public class AppliancesActivity extends AppCompatActivity {

    private final String TAG_LOG = "AppliancesActivity";
    private final String TAG_BOTTOM_VIEW = "BottomView";

    private ArrayList<Item> mPersons = new ArrayList<>();
    private ItemAdapter mAdapterPerson;
    private ItemListViewOrder mPersonsListView;
    private JBHorizontalSwipe mJBHorizontalSwipe;
    private Context mContext;
    private ViewGroup mSwipedViewGroup;

    private boolean mRemovePrevDeleted;
    private Item mPrevDeletedPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = this;

        // The main activity needs a JBHorizontalSwipe object to handle swiping listview items.
        mJBHorizontalSwipe = new JBHorizontalSwipe(ijbHorizontalSwipe);

        // Add some data to the listview.
        mPersons.add(new Item(getNewId(), "Test Smoke Alarm", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "Smoke Alarm Change Batteries", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "Test CO Detector", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "CO Detector Change Batteries", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "Check Fire Extinguisher", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "Check Sump Pump", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "Clean/Inspect Washing Machine", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "Clean/Inspect Dryer", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "Clean Dryer Vent", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "Test Home for Radon", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "Pool Pump Filter Maintenance", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "Maintain Underground Sprinkler System", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "Generator Maintenance", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "Change HVAC Filter", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "Drain Water Heater", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));
        mPersons.add(new Item(getNewId(), "Inspect Water Heater", BitmapFactory.decodeResource(getResources(), R.drawable.doc_lv_tp_2)));


        mPersonsListView = (ItemListViewOrder) findViewById(R.id.lvPersons);
        mPersonsListView.setPersonList(mPersons);
        mAdapterPerson = new ItemAdapter(this, R.layout.appliances_item, mPersons, mJBHorizontalSwipe, mPersonsListView, new IListItemControls() {
            @Override
            public void onUndoClicked(View v) {
                // When the Undo button on a list item is pressed, we need to reset the state of deletion.
                mRemovePrevDeleted = false;
                mPrevDeletedPerson = null;
            }
        });

        mPersonsListView.setAdapter(mAdapterPerson);

        mPersonsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // This is where you put your code to handle the user tapping on a list item, i.e.
                // when the item's top view is being displayed.
                Item person = (Item) view.getTag();
                Toast.makeText(mContext, person.name, Toast.LENGTH_SHORT).show();
            }
        });

        // Handles  deleted item if the user scrolls the listview.
        // NOTE: Don't use a ScrollListener on the listview as this will cause bad
        // side effects. Motion events for the listview must be handled by the
        // onTouchEvent method in PersonListViewOrder in order for this kind of listview
        // to function properly.
        mPersonsListView.setVerticalScrollCallback(new ItemListViewOrder.IVerticalScrollCallback() {
            @Override
            public void onVerticalScroll() {
                // This method gets called when the user scrolls the listview vertically.
                if (mPrevDeletedPerson != null) {
                    int pos = mAdapterPerson.getPosition(mPrevDeletedPerson);
                    View vPrevDeleted = mPersonsListView.getChildAt(pos - mPersonsListView.getFirstVisiblePosition());
                    mAdapterPerson.animateRemoval(vPrevDeleted);
                    mRemovePrevDeleted = false;
                    mPrevDeletedPerson = null;
                }
            }
        });

    }


    /**
     * Used to handle callbacks when the user swipes list items.
     */
    private JBHorizontalSwipe.IJBHorizontalSwipe ijbHorizontalSwipe = new JBHorizontalSwipe.IJBHorizontalSwipe() {
        @Override
        public void onReposition(float x, boolean scrollingRight, float scrollDelta) {
            // Currently not used. You can use this callback to do something while the user is swiping a list item.
        }

        @Override
        public void onTopViewVisibilityChange(View vTop, boolean visible) {
            // This callback gets called when the list item's top view changes from fully visible to
            // fully invisible.

            mSwipedViewGroup = (ViewGroup) vTop.getParent();
            final Item person = (Item) mSwipedViewGroup.getTag();
            person.deleted = !visible;
            mRemovePrevDeleted = false;

            // Using setPressed is necessary in various places throughout the app in order
            // to restore the background color of the top view. This is required because list
            // items don't receive the ACTION_UP event which would normally restore the background
            // color. The ACTION_UP is not received because code in PersonListViewOrder as well
            // JBHorizontalSwipe and CustomListItem intercept the motion events and take over
            // control when a ACTION_DOWN is received.

            vTop.setPressed(false);
            mPersonsListView.setPressed(false);

            if ((person == mPrevDeletedPerson) && !person.deleted)
                mPrevDeletedPerson = null;

            if ((person.deleted) && (mPrevDeletedPerson != null) && (person != mPrevDeletedPerson))
                mRemovePrevDeleted = true;

            View vBottom = mSwipedViewGroup.findViewWithTag(TAG_BOTTOM_VIEW);
            PropertyValuesHolder pvhAlphaCurrent;

            ButtonBottomView btnUndo = (ButtonBottomView) vBottom.findViewById(R.id.btnUndo);
            mAdapterPerson.onItemSwiped(person, btnUndo);

            // If the top view is swiped out of view, we want to animate the bottom view's
            // visibility to gradually show, which is done by changing its alpha.

            if (person.deleted)
                pvhAlphaCurrent = PropertyValuesHolder.ofFloat("alpha", 0, 1);
            else
                pvhAlphaCurrent = PropertyValuesHolder.ofFloat("alpha", 1, 0);

            ObjectAnimator animatorView = ObjectAnimator.ofPropertyValuesHolder(vBottom, pvhAlphaCurrent);
            animatorView.setInterpolator(new LinearInterpolator());
            animatorView.setDuration(300);

            animatorView.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    // If a previous item has been deleted but is still visible, we
                    // need to remove it from the list using some animation.

                    if (mRemovePrevDeleted) {
                        int pos = mAdapterPerson.getPosition(mPrevDeletedPerson);

                        if ((pos >= mPersonsListView.getFirstVisiblePosition()) && (pos <= mPersonsListView.getLastVisiblePosition())) {
                            View vPrevDeleted = mPersonsListView.getChildAt(pos - mPersonsListView.getFirstVisiblePosition());
                            mAdapterPerson.animateRemoval(vPrevDeleted);
                        } else {
                            mAdapterPerson.remove(mPrevDeletedPerson);
                        }
                    }

                    if (person.deleted)
                        mPrevDeletedPerson = person;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });

            animatorView.start();
        }
    };


    /**
     * Used to intercept touch events.
     */
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mJBHorizontalSwipe != null)
            mJBHorizontalSwipe.onRootDispatchTouchEventListener(ev);

        return super.dispatchTouchEvent(ev);
    }


    /**
     * Generates a unique ID.
     *
     * @return Returns a random number.
     */
    private long getNewId() {
        Random r = new Random();
        return r.nextLong();
    }


    interface IListItemControls {
        /**
         * A callback that gets called when the user taps on the Undo button.
         *
         * @param v The view that represents the Undo button.
         */
        void onUndoClicked(View v);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prevention, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
