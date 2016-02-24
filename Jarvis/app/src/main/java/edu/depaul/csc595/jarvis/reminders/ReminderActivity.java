package edu.depaul.csc595.jarvis.reminders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.depaul.csc595.jarvis.R;

/**
 * Created by Advait on 18-02-2016.
 */
public class ReminderActivity extends AppCompatActivity
{
    Button linkForCustom;

    String[] country = new String[] {
            "Advait",
            "A",
            "CSC",
            "595",
            "Android",
            "ALlState",
            "Jarivs",
            "Capstone",
            "IOT",
            "HRPD"

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
            false
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        linkForCustom = (Button) findViewById(R.id.go_to_custom_reminder);
        linkForCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go = new Intent(getApplicationContext(), CustomReminderActivity.class);
                startActivity(go);

            }
        });

        /*
        if(savedInstanceState!=null){
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
    */
    }
}