package edu.depaul.csc595.jarvis.detection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.detection.classes.SmartProductContent.SmartProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

    String email;


    private TextView formatTxt, contentTxt;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_products);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        email = intent.getStringExtra(DetectionBaseActivity.EMAIL_EXTRA);
        Log.d(LOG_TAG, "Email" + email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_products, menu);
        return true;
    }

    @OnClick(R.id.button_cancel)
    public void cancelForm(View view){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    @OnClick(R.id.button_enter)
    public void submitForm(View view) {
        int selectedId = rb_sensors.getCheckedRadioButtonId();
        RadioButton radioSensorButton = (RadioButton) findViewById(selectedId);

        final String serial_no = et_barcode.getText().toString();
        if (serial_no.equals("")){
            et_barcode.setError("Barcode Required");
        }

        final String appliance_name = tv_appliance_name.getText().toString();
        if (appliance_name.equals(R.string.choose_appliance)) {
            tv_appliance_name.setError("Please select appliance");
        }

        final String type_of_smart_product = radioSensorButton.getText().toString();
        if (serial_no.equals("") || appliance_name.equals(R.string.choose_appliance)){
            return;
        }

        SmartProduct smartProduct = new SmartProduct(serial_no, type_of_smart_product, appliance_name);
        if (email == null){
            email = "test1@test.com";
        }

        createSmartProduct(smartProduct, email);

        CharSequence text = serial_no + " " + radioSensorButton.getText() + " " + appliance_name + "" + email;
        Log.d(LOG_TAG, text.toString());

        Intent returnIntent = new Intent();
//        returnIntent.putExtra("result", result);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();


    }

    private void createSmartProduct(SmartProduct smartProduct, String email_address){
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Creating Smart Product");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();


        Retrofit retrofit = DetectionService.retrofit;
        DetectionService.DetectionInterface detectionInterface = retrofit.create(DetectionService.DetectionInterface.class);

        Call<SmartProduct> call = detectionInterface.createSmartProduct(email_address, smartProduct);

        call.enqueue(new Callback<SmartProduct>() {
            @Override
            public void onResponse(Call<SmartProduct> call, Response<SmartProduct> response) {
                Log.d(LOG_TAG, "Reached this place");
                if (!response.isSuccess()) {
                    Log.d(LOG_TAG, response.errorBody().toString());
                    return;
                }
                SmartProduct smartProduct = response.body();
                Log.d(LOG_TAG, "Response returned by website is : " + response.body());
                Log.d(LOG_TAG, "Response returned by website is : " + response.code());
                mProgressDialog.hide();

            }

            @Override
            public void onFailure(Call<SmartProduct> call, Throwable t) {
                Log.d(LOG_TAG, t.getMessage());
                mProgressDialog.hide();
                Toast.makeText(getApplicationContext(), "Failed to save smart product!", Toast.LENGTH_LONG).show();
            }
        });
    }


    @OnClick(R.id.appliance_name)
    public void openDialogAppliances(View v) {
        Log.d(LOG_TAG, "I am outside dialog");

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
