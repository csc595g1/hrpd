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

public class TakeThisAction extends AppCompatActivity {
    private CheckBox chk_email, chk_text, chk_notify;
    private Button button_done;
    private boolean email = false;
    private boolean text = false;
    private boolean notify = false;
    ArrayList<String> list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_this_action);

        button_done = (Button)findViewById(R.id.button_done);
        Intent intent = getIntent();
        list1 = intent.getStringArrayListExtra("action");

        chk_email = (CheckBox)findViewById(R.id.checkbox_email);
        chk_text = (CheckBox)findViewById(R.id.checkbox_text);
        chk_notify = (CheckBox)findViewById(R.id.checkbox_notify);

        if (list1 != null) {

            if (list1.contains(chk_email.getText().toString())) {
                chk_email.setChecked(true);
                email = true;
            }

            if (list1.contains(chk_text.getText().toString())) {
                chk_text.setChecked(true);
                text = true;
            }
            if (list1.contains(chk_notify.getText().toString())) {
                chk_notify.setChecked(true);
                notify = true;
            }
        }

        chk_email.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    email = true;
                } else{
                    email = false;
                }

            }
        });
        chk_text.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    text = true;
                } else{
                    text = false;
                }


            }
        });
        chk_notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notify = true;
                } else{
                    notify = false;
                }


            }
        });
        button_done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (list1==null){
                    list1 = new ArrayList<String>();
                }

                if (email) {

                    if (!list1.contains(chk_email.getText().toString())) {
                        list1.add(chk_email.getText().toString());
                    }
                }
                else
                {
                    if (list1.contains(chk_email.getText().toString())) {
                        list1.remove(chk_email.getText().toString());
                    }
                }

                if (text){
                    if (!list1.contains(chk_text.getText().toString())) {
                        list1.add(chk_text.getText().toString());
                    }

                }
                else
                {
                    if (list1.contains(chk_text.getText().toString())) {
                        list1.remove(chk_text.getText().toString());
                    }
                }

                if (notify){
                    if (!list1.contains(chk_notify.getText().toString())) {
                        list1.add(chk_notify.getText().toString());
                    }
                }
                else
                {
                    if (list1.contains(chk_notify.getText().toString())) {
                        list1.remove(chk_notify.getText().toString());
                    }
                }

                Toast.makeText(TakeThisAction.this, list1 + "", Toast.LENGTH_SHORT).show();
                AddTriggers at = AddTriggers.getActivityInstance();
                at.getData1(list1);
                finish();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_take_this_action, menu);
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
