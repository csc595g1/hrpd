package edu.depaul.csc595.jarvis.detection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;


import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.depaul.csc595.jarvis.R;

public class RegisterProducts extends AppCompatActivity {

    @Bind(R.id.edit_barcode_number)
    EditText et_barcode;

    @Bind(R.id.rb_sensors)
    RadioGroup rb_sensors;

    @Bind(R.id.scan_button)
    Button scanBtn;

    @Bind(R.id.appliance_name)
    TextView tv_appliance_name;

    private static String LOG_TAG = "RegisterProducts";

    private TextView formatTxt, contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_products);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String email = intent.getStringExtra(DetectionBaseActivity.EMAIL_EXTRA);
        Log.d(LOG_TAG, "Email" + email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_products, menu);
        return true;
    }

    @OnClick(R.id.button_enter)
    public void addProducts(View view) {
        int selectedId = rb_sensors.getCheckedRadioButtonId();
        RadioButton radioSensorButton = (RadioButton) findViewById(selectedId);

        final String barcode = et_barcode.getText().toString();
        if (barcode.equals("")){
            et_barcode.setError("Barcode Required");
        }

        final String appliance_name = tv_appliance_name.getText().toString();
        if (appliance_name.equals(R.string.choose_appliance)) {
            tv_appliance_name.setError("Please select appliance");
        }

        CharSequence text = et_barcode.getText() + " " + radioSensorButton.getText() + " " + appliance_name;
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.appliance_name)
    public void openDialogAppliances(View v) {
        Log.d(LOG_TAG, "I got out of dialog");

//        CharSequence[] appliance_names = ApplianceContent.getCharSequence();
        final CharSequence appliance_names[] = new CharSequence[] {"Sump Pump", "Living Room", "Bedroom", "Fridge", "Heater"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick an appliance");
        builder.setItems(appliance_names, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(LOG_TAG, "I got in dialog");
                Log.d(LOG_TAG, "Which is: " + Integer.toString(which));
                tv_appliance_name.setText(appliance_names[which]);
            }
        });
        builder.show();
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
