package edu.depaul.csc595.jarvis.inventory;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.depaul.csc595.jarvis.R;

public class AppliancesActivity extends AppCompatActivity
{

    String[] country = new String[] {
            "Test Smoke Alarms",
            "Change Smoke Alarm Batteries",
            "Test Carbon Monoxide Alarm",
            "Check Fire Extinguisher",
            "Inspect Sump Pump",
            "Clean/Inspect Washing Machine",
            "Clean Dryer Vent",
            "Test Home for Radon",
            "Pool Pump Filter Maintenance",
            "Maintain Underground Sprinkler System",
            "Generator Maintenance",
            "Check/Drain Water Heater",
            "HVAC Tune-up",
            "Clean Gutters",
            "Change Home Air Filter",
            "Inspect Roof",
            "Tree Trimming"

    };
    public boolean[] status = {
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState!=null)
        {
            status = savedInstanceState.getBooleanArray("status");
        }

        ListView lvCountries = (ListView) findViewById(R.id.lv_inventory);

        OnItemClickListener itemClickListener = new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> lv, View item, int position, long id) {
                // TODO Auto-generated method stub
                ListView lview = (ListView) lv;

                SimpleAdapter adapter = (SimpleAdapter) lview.getAdapter();

                HashMap<String,Object> hm = (HashMap) adapter.getItem(position);
                RelativeLayout rLayout = (RelativeLayout) item;
                ToggleButton tgl = (ToggleButton) rLayout.getChildAt(1);
                String strStatus = "";
                if(tgl.isChecked()){
                    tgl.setChecked(false);
                    strStatus = "Off";
                    status[position]=false;
                }else{
                    tgl.setChecked(true);
                    strStatus = "On";
                    status[position]=true;
                }
                Toast.makeText(getBaseContext(), (String) hm.get("txt") + " : " + strStatus, Toast.LENGTH_SHORT).show();
            }
        };

        lvCountries.setOnItemClickListener(itemClickListener);
        List<HashMap<String,Object>> aList = new ArrayList<HashMap<String,Object>>();
        for(int i=0;i<10;i++){
            HashMap<String, Object> hm = new HashMap<String,Object>();
            hm.put("txt", country[i]);
            hm.put("stat",status[i]);
            aList.add(hm);
        }
        String[] from = {"txt","stat" };
        int[] to = { R.id.tv_item, R.id.tgl_status};

        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.appliances_listview_format, from, to);

        lvCountries.setAdapter(adapter);

    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

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
