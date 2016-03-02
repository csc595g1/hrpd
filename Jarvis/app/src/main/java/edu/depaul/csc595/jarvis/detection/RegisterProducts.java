package edu.depaul.csc595.jarvis.detection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.Bind;


import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.depaul.csc595.jarvis.Manifest;
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

    @Bind(R.id.scanner)
    Button scanBtn;

    @Bind(R.id.appliance_name)
    TextView tv_appliance_name;

    private static String LOG_TAG = "RegisterProducts";

    String email;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;

    private TextView formatTxt, contentTxt;

    private ProgressDialog mProgressDialog;

    private Boolean isSuccess = false;
    void setSuccess(Boolean value){
        isSuccess = value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_products);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        email = intent.getStringExtra(DetectionBaseActivity.EMAIL_EXTRA);
        Log.d(LOG_TAG, "Email" + email);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != getPackageManager().PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            Log.d(LOG_TAG, "Permissions already granted");
        }
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
            Log.d(LOG_TAG, "Email is null");
            email = "test1@test.com";
        } else{
            Log.d(LOG_TAG, email);
        }

        CharSequence text = serial_no + " " + radioSensorButton.getText() + " " + appliance_name + "" + email;
        Log.d(LOG_TAG, text.toString());

        createSmartProduct(smartProduct, email);

    }

    private void completeAndReturn() {
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

        Log.d(LOG_TAG, "In Create Smart Product, email is: " + email_address);

        Retrofit retrofit = DetectionService.retrofit;
        DetectionService.DetectionInterface detectionInterface = retrofit.create(DetectionService.DetectionInterface.class);

        Call<SmartProduct> call = detectionInterface.createSmartProduct(email_address, smartProduct);



        call.enqueue(new Callback<SmartProduct>() {
            @Override
            public void onResponse(Call<SmartProduct> call, Response<SmartProduct> response) {
                Log.d(LOG_TAG, "Reached this place");
                if (!response.isSuccess()) {
                    Log.d(LOG_TAG, response.errorBody().toString());
                    setSuccess(false);
                    mProgressDialog.hide();
                    Toast.makeText(getApplicationContext(), "Failed to save smart product! Please make sure it is a valid serial no", Toast.LENGTH_LONG).show();
                    return;
                }
                SmartProduct smartProduct = response.body();
                Log.d(LOG_TAG, "Response returned by website is : " + response.body());
                Log.d(LOG_TAG, "Response returned by website is : " + response.code());
                mProgressDialog.hide();
                Toast.makeText(getApplicationContext(), "Successfully registered product", Toast.LENGTH_LONG).show();
                completeAndReturn();

            }

            @Override
            public void onFailure(Call<SmartProduct> call, Throwable t) {
                Log.d(LOG_TAG, t.getMessage());
                mProgressDialog.hide();
                Toast.makeText(getApplicationContext(), "Error occurred!", Toast.LENGTH_LONG).show();
                setSuccess(false);
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

    @OnClick(R.id.scanner)
    public void scanBar(View v){

        new IntentIntegrator(this).initiateScan();
//        try {
//            Intent intent = new Intent(ACTION_SCAN);
//            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
//            startActivityForResult(intent, 0);
//        } catch (ActivityNotFoundException e){
//            showDialog(RegisterProducts.this, "No Scanner Found",
//                    "Download a scanner code activity?", "Yes", "No").show();
//        }

    }

//    public void scanQR(View v){
//
//        try {
//            Intent intent = new Intent(ACTION_SCAN);
//            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
//            startActivityForResult(intent, 0);
//        } catch (ActivityNotFoundException e){
//            showDialog(RegisterProducts.this, "No Scanner Found",
//                    "Download a scanner code activity?", "Yes", "No").show();
//        }
//
//    }

    private static AlertDialog showDialog(final AppCompatActivity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo){

        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonYes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                                Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=" + "com.google.zxing.client.android");
//                                Uri uri = Uri.parse("market://details?id=" + "com.google.zxing.client.android");

                                Log.d(LOG_TAG, "URI: " + uri);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(uri);
                                try {
                                    Log.d(LOG_TAG, "About to start activity for market");
                                    act.startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                    Log.d(LOG_TAG, "Market: Zwing package not found");
                                    e.printStackTrace();
                                }
                            }
                        })
                .setNegativeButton(buttonNo,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }

                );

        return downloadDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d(LOG_TAG, "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d(LOG_TAG, "Scanned");
                et_barcode.setText(result.getContents());
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}