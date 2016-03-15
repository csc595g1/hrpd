package edu.depaul.csc595.jarvis.appliances.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderCODetector;
import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderDryer;
import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderFireExtinguisher;
import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderGenerator;
import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderSmokeAlarm;
import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderSumpPump;
import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderVent;
import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderWashingMachine;
import edu.depaul.csc595.jarvis.reminders.staticreminders.activities.ReminderWaterHeater;

public class AppliancesActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
        /*
                Intent go = new Intent(getApplicationContext(), DepthAnimation.class);
                startActivity(go);
            }
        });
        */

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CardView car1 = (CardView) findViewById(R.id.card_view1);
        final CardView car2 = (CardView) findViewById(R.id.card_view2);
        final CardView car3 = (CardView) findViewById(R.id.card_view3);
        final CardView car4 = (CardView) findViewById(R.id.card_view4);
        final CardView car5 = (CardView) findViewById(R.id.card_view5);
        final CardView car6 = (CardView) findViewById(R.id.card_view6);
        final CardView car7 = (CardView) findViewById(R.id.card_view7);
        final CardView car8 = (CardView) findViewById(R.id.card_view8);
        final CardView car9 = (CardView) findViewById(R.id.card_view9);

        Switch smoke_alarm = (Switch) findViewById(R.id.switch_smoke_alarm);
        Switch fire_exting = (Switch) findViewById(R.id.switch_fire_extinguisher);
        Switch sump_pump = (Switch) findViewById(R.id.switch_sump_pump);
        Switch washing_machine = (Switch) findViewById(R.id.switch_washing_machine);
        Switch water_heater = (Switch) findViewById(R.id.switch_water_heater);
        Switch co_detector = (Switch) findViewById(R.id.switch_co_detector);
        Switch dryer = (Switch) findViewById(R.id.switch_dryer);
        Switch dryer_vent = (Switch) findViewById(R.id.switch_dryer_vent);
        Switch generator = (Switch) findViewById(R.id.switch_generator);

        smoke_alarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((Switch) v).isChecked())
                {
                    Toast.makeText(getApplicationContext(),"ON",Toast.LENGTH_SHORT).show();
                    car1.setClickable(true);
                }
                else
                {
                    car1.setClickable(false);
                    car1.setBackgroundColor(Color.parseColor("#fafafa"));
                }
            }
        });

        fire_exting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((Switch) v).isChecked())
                {
                    Toast.makeText(getApplicationContext(),"ON",Toast.LENGTH_SHORT).show();
                    car2.setClickable(true);
                }
                else
                {
                    car2.setClickable(false);
                    car2.setBackgroundColor(Color.parseColor("#fafafa"));
                }
            }
        });

        sump_pump.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((Switch) v).isChecked())
                {
                    Toast.makeText(getApplicationContext(),"ON",Toast.LENGTH_SHORT).show();
                    car3.setClickable(true);
                }
                else
                {
                    car3.setClickable(false);
                    car3.setBackgroundColor(Color.parseColor("#fafafa"));
                }
            }
        });

        washing_machine.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((Switch) v).isChecked())
                {
                    Toast.makeText(getApplicationContext(),"ON",Toast.LENGTH_SHORT).show();
                    car4.setClickable(true);
                }
                else
                {
                    car4.setClickable(false);
                    car4.setBackgroundColor(Color.parseColor("#fafafa"));
                }
            }
        });

        water_heater.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((Switch) v).isChecked())
                {
                    Toast.makeText(getApplicationContext(),"ON", Toast.LENGTH_SHORT).show();
                    car5.setClickable(true);
                }
                else
                {
                    car5.setClickable(false);
                    car5.setBackgroundColor(Color.parseColor("#fafafa"));
                }
            }
        });

        co_detector.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((Switch) v).isChecked())
                {
                    Toast.makeText(getApplicationContext(),"ON", Toast.LENGTH_SHORT).show();
                    car7.setClickable(true);
                }
                else
                {
                    car7.setClickable(false);
                    car7.setBackgroundColor(Color.parseColor("#fafafa"));
                }
            }
        });

        dryer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((Switch) v).isChecked())
                {
                    Toast.makeText(getApplicationContext(),"ON", Toast.LENGTH_SHORT).show();
                    car6.setClickable(true);
                }
                else
                {
                    car6.setClickable(false);
                    car6.setBackgroundColor(Color.parseColor("#fafafa"));
                }
            }
        });

        dryer_vent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((Switch) v).isChecked())
                {
                    Toast.makeText(getApplicationContext(),"ON", Toast.LENGTH_SHORT).show();
                    car7.setClickable(true);
                }
                else
                {
                    car7.setClickable(false);
                    car7.setBackgroundColor(Color.parseColor("#fafafa"));
                }
            }
        });

        generator.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((Switch) v).isChecked())
                {
                    Toast.makeText(getApplicationContext(),"ON", Toast.LENGTH_SHORT).show();
                    car9.setClickable(true);
                }
                else
                {
                    car9.setClickable(false);
                    car9.setBackgroundColor(Color.parseColor("#fafafa"));
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

        car6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent go_to_notification = new Intent(getApplicationContext(), ReminderDryer.class);
                startActivity(go_to_notification);

            }
        });

        car7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent go_to_notification = new Intent(getApplicationContext(), ReminderCODetector.class);
                startActivity(go_to_notification);

            }
        });

        car8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent go_to_notification = new Intent(getApplicationContext(), ReminderVent.class);
                startActivity(go_to_notification);

            }
        });

        car9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent go_to_notification = new Intent(getApplicationContext(), ReminderGenerator.class);
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
