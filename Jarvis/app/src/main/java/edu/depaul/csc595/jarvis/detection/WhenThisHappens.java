package edu.depaul.csc595.jarvis.detection;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_when_this_happens);
        button_done = (Button)findViewById(R.id.button_done);
        final ArrayList<String> list1 = new ArrayList<String>();
        chk_flame = (CheckBox)findViewById(R.id.checkbox_flame);
        chk_moisture = (CheckBox)findViewById(R.id.checkbox_moisture);
        chk_smoke = (CheckBox)findViewById(R.id.checkbox_smoke);
        chk_moisture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list1.add(chk_moisture.getText().toString());
            }
        });
        chk_smoke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list1.add(chk_smoke.getText().toString());
            }
        });
        chk_flame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list1.add(chk_flame.getText().toString());
            }
        });
        button_done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(WhenThisHappens.this,list1+"",Toast.LENGTH_SHORT).show();
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
