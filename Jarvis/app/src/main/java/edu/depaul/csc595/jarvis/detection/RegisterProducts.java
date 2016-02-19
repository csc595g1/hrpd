package edu.depaul.csc595.jarvis.detection;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.depaul.csc595.jarvis.R;

public class RegisterProducts extends Activity {

    private Button register;
    private EditText barcode;
    private RadioGroup rb_sensors;
    private RadioButton radioSensorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_products);
        barcode = (EditText)findViewById(R.id.edit_barcode_number);
        register = (Button)findViewById(R.id.button_enter);
        rb_sensors = (RadioGroup)findViewById(R.id.rb_sensors);
       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int selectedId = rb_sensors.getCheckedRadioButtonId();
               radioSensorButton = (RadioButton) findViewById(selectedId);
               CharSequence text = "Barcode " + barcode.getText() + "     Sensor " + radioSensorButton.getText();
               int duration = Toast.LENGTH_SHORT;

               Toast toast = Toast.makeText(getApplicationContext(), text, duration);
               toast.show();

           }
       });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_products, menu);
        return true;
    }
//    public void addProduct(View view) {
//        int selectedId = rb_sensors.getCheckedRadioButtonId();
//        radioSensorButton = (RadioButton) findViewById(selectedId);
//        CharSequence text = barcode.getText() +" "+radioSensorButton.getText();
//        int duration = Toast.LENGTH_SHORT;
//
//        Toast toast = Toast.makeText(this, text, duration);
//        toast.show();
//
//
//
//    }
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
