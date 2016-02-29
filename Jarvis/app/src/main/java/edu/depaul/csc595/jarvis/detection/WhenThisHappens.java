package edu.depaul.csc595.jarvis.detection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

import edu.depaul.csc595.jarvis.R;

public class WhenThisHappens extends AppCompatActivity {
    private CheckBox chk_flame, chk_smoke, chk_moisture;
    private Button button_done;
    private boolean moisture = false;
    private boolean smoke = false;
    private boolean flame = false;
    ArrayList<String> list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_when_this_happens);
        button_done = (Button)findViewById(R.id.button_done);
        Intent intent = getIntent();
        list1 = intent.getStringArrayListExtra("sensors");

        chk_flame = (CheckBox)findViewById(R.id.checkbox_flame);
        chk_moisture = (CheckBox)findViewById(R.id.checkbox_moisture);
        chk_smoke = (CheckBox)findViewById(R.id.checkbox_smoke);

        if (list1 != null) {

            if (list1.contains(chk_moisture.getText().toString())) {
                chk_moisture.setChecked(true);
                moisture = true;
            }

            if (list1.contains(chk_smoke.getText().toString())) {
                chk_smoke.setChecked(true);
                smoke = true;
            }
            if (list1.contains(chk_flame.getText().toString())) {
                chk_flame.setChecked(true);
                flame = true;
            }
        }

        chk_moisture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    moisture = true;
                } else{
                    moisture = false;
                }

            }
        });
        chk_smoke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    smoke = true;
                } else{
                    smoke = false;
                }


            }
        });
        chk_flame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flame = true;
                } else{
                    flame = false;
                }


            }
        });
        button_done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (list1==null){
                    list1 = new ArrayList<String>();
                }

                if (moisture) {

                    if (!list1.contains(chk_moisture.getText().toString())) {
                        list1.add(chk_moisture.getText().toString());
                    }
                }
                else
                {
                    if (list1.contains(chk_moisture.getText().toString())) {
                        list1.remove(chk_moisture.getText().toString());
                    }
                }

                if (flame){
                    if (!list1.contains(chk_flame.getText().toString())) {
                        list1.add(chk_flame.getText().toString());
                    }

                }
                else
                {
                    if (list1.contains(chk_flame.getText().toString())) {
                        list1.remove(chk_flame.getText().toString());
                    }
                }

                if (smoke){
                    if (!list1.contains(chk_smoke.getText().toString())) {
                        list1.add(chk_smoke.getText().toString());
                    }
                }
                else
                {
                    if (list1.contains(chk_smoke.getText().toString())) {
                        list1.remove(chk_smoke.getText().toString());
                    }
                }

                Toast.makeText(WhenThisHappens.this, list1+"",Toast.LENGTH_SHORT).show();
                AddTriggers at = AddTriggers.getActivityInstance();
                at.getData(list1);
                finish();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_when_this_happens, menu);
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
