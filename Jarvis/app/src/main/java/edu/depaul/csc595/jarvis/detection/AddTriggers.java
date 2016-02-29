package edu.depaul.csc595.jarvis.detection;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.depaul.csc595.jarvis.R;
/**
 * Created by lavanyalatha on 2/23/16.
 */

public class AddTriggers extends AppCompatActivity {

    static AddTriggers INSTANCE;
    ArrayList<String> sensors_array, action_array;
    private TextView sensors, take_action;
    private LinearLayout linear_when, linear_take_action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_triggers);
        linear_when = (LinearLayout)findViewById(R.id.linear_when);
        linear_take_action = (LinearLayout)findViewById(R.id.linear_take_action);
        sensors = (TextView)findViewById(R.id.text_when);
        take_action = (TextView)findViewById(R.id.text_take_action);

        linear_when.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent when_intent = new Intent(AddTriggers.this,WhenThisHappens.class);
                when_intent.putExtra("sensors", sensors_array);
                startActivity(when_intent);
            }
        });
        linear_take_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent action_intent = new Intent(AddTriggers.this,TakeThisAction.class);
                action_intent.putExtra("action", action_array);
                startActivity(action_intent);
            }
        });
//        Toolbar mToolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        mToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        getSupportActionBar().setTitle("Event Details");
        INSTANCE = this;
    }

    public static AddTriggers getActivityInstance()
    {
        return INSTANCE;
    }

    public void getData(ArrayList<String> array){
        sensors_array = array;
        String display = "";
        for (int i = 0; i< array.size(); i++){
            if (i == array.size()-1) {
                display = display + array.get(i);
            }else {
                display = display + array.get(i) + "\n";
            }
        }

        if(display.length()<1){
            sensors.setText("When");
        }else {
            sensors.setText(display);
        }



    }
    public void getData1(ArrayList<String> array){
        action_array = array;
        String display = "";
        for (int i = 0; i< array.size(); i++){
            if (i == array.size()-1) {
                display = display + array.get(i);
            }else {
                display = display + array.get(i) + "\n";
            }
        }

        if(display.length()<1){
            take_action.setText("Take This Action");
        }else {
            take_action.setText(display);
        }



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
