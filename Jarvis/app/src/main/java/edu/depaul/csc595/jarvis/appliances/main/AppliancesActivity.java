package edu.depaul.csc595.jarvis.appliances.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderFireExtinguisher;
import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderSmokeAlarm;
import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderSumpPump;
import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderWashingMachine;
import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderWaterHeater;

public class AppliancesActivity extends AppCompatActivity
{

    RecyclerView recyclerView;

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

        final CardView car1 = (CardView) findViewById(R.id.card_view1);
        final CardView car2 = (CardView) findViewById(R.id.card_view2);
        final CardView car3 = (CardView) findViewById(R.id.card_view3);
        final CardView car4 = (CardView) findViewById(R.id.card_view4);
        final CardView car5 = (CardView) findViewById(R.id.card_view5);

        Switch switch_smoke_alarm = (Switch) findViewById(R.id.appliances_smoke_alarm_switch);
        Switch switch_fire_exting = (Switch) findViewById(R.id.appliances_fire_extinguisher_switch);
        Switch switch_sump_pump = (Switch) findViewById(R.id.appliances_sump_pump_switch);
        Switch switch_washing_machine = (Switch) findViewById(R.id.appliances_washing_machine_switch);
        Switch switch_water_heater = (Switch) findViewById(R.id.appliances_water_heater_switch);

        switch_smoke_alarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((Switch) v).isChecked())
                {
                    //Toast.makeText(getApplicationContext(),"ON",Toast.LENGTH_SHORT).show();
                    car1.setClickable(true);
                }
                else
                {
                    //car5.setVisibility(View.GONE);
                    car1.setClickable(false);
                    car5.setBackgroundColor(Color.parseColor("#fafafa"));
                }
            }
        });

        switch_fire_exting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((Switch) v).isChecked())
                {
                    //Toast.makeText(getApplicationContext(),"ON",Toast.LENGTH_SHORT).show();
                    car2.setClickable(true);
                }
                else
                {
                    //car5.setVisibility(View.GONE);
                    car2.setClickable(false);
                    car5.setBackgroundColor(Color.parseColor("#fafafa"));
                }
            }
        });

        switch_sump_pump.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((Switch) v).isChecked())
                {
                    //Toast.makeText(getApplicationContext(),"ON",Toast.LENGTH_SHORT).show();
                    car3.setClickable(true);
                }
                else
                {
                    //car5.setVisibility(View.GONE);
                    car3.setClickable(false);
                    car5.setBackgroundColor(Color.parseColor("#fafafa"));
                }
            }
        });

        switch_washing_machine.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((Switch) v).isChecked())
                {
                    //Toast.makeText(getApplicationContext(),"ON",Toast.LENGTH_SHORT).show();
                    car4.setClickable(true);
                }
                else
                {
                    //car5.setVisibility(View.GONE);
                    car4.setClickable(false);
                    car5.setBackgroundColor(Color.parseColor("#fafafa"));
                }
            }
        });

        switch_water_heater.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((Switch) v).isChecked())
                {
                    Toast.makeText(getApplicationContext(),"ON", Toast.LENGTH_SHORT).show();
                    //car5.setVisibility(View.VISIBLE);
                    car5.setClickable(true);
                }
                else
                {
                    //car5.setVisibility(View.GONE);
                    car5.setClickable(false);
                    car5.setBackgroundColor(Color.parseColor("#fafafa"));
                }
            }
        });

        car1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent go_to_notification = new Intent(getApplicationContext(), ReminderSmokeAlarm.class);
                startActivity(go_to_notification);

            }
        });

        car2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent go_to_notification = new Intent(getApplicationContext(), ReminderFireExtinguisher.class);
                startActivity(go_to_notification);

            }
        });

        car3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent go_to_notification = new Intent(getApplicationContext(), ReminderSumpPump.class);
                startActivity(go_to_notification);

            }
        });

        car4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent go_to_notification = new Intent(getApplicationContext(), ReminderWashingMachine.class);
                startActivity(go_to_notification);

            }
        });

        car5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent go_to_notification = new Intent(getApplicationContext(), ReminderWaterHeater.class);
                startActivity(go_to_notification);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_appliances, menu);
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
