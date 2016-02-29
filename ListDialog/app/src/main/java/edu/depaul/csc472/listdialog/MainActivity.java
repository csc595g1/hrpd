package edu.depaul.csc472.listdialog;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        public class NewsRowAdapter extends BaseAdapter  {

            private Context mContext;
            private Activity activity;
            private static LayoutInflater inflater=null;
            private ArrayList<HashMap<String, String>> data;
            int resource;
            //String response;
            //Context context;
            //Initialize adapter
            public NewsRowAdapter(Context ctx,Activity act, int resource,ArrayList<HashMap<String, String>> d, DialogCreatorInterface di) {
                super();
                this.resource=resource;
                this.data = d;
                this.activity = act;
                this.mContext = ctx;
                inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            }

            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {


                View vi = convertView;
                if(convertView==null)
                    vi = inflater.inflate(R.layout.row,null);

                TextView firstname = (TextView) vi.findViewById(R.id.fname);
                TextView lastname = (TextView) vi.findViewById(R.id.lname);
                TextView startTime = (TextView) vi.findViewById(R.id.stime);
                TextView endTime = (TextView) vi.findViewById(R.id.etime);
                TextView date = (TextView) vi.findViewById(R.id.blank);
                ImageView img = (ImageView) vi.findViewById(R.id.list_image);


                HashMap<String, String> song = new HashMap<String, String>();
                song =data.get(position);

                firstname.setText(song.get(MainActivity.TAG_PROP_FNAME));
                lastname.setText(song.get(MainActivity.TAG_PROP_LNAME));
                startTime.setText(song.get(MainActivity.TAG_STIME));
                endTime.setText(song.get(MainActivity.TAG_ETIME));
                date.setText(song.get(MainActivity.TAG_DATE));
                //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), img);

                Button accept = (Button) vi.findViewById(R.id.button1);
                accept.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        final int x = (int) getItemId(position);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                        // set title
                        alertDialogBuilder.setTitle("Your Title");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Click yes to exit!")
                                .setCancelable(false)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,int id) {
                                        Toast.makeText(mContext, "Yes clicked", Toast.LENGTH_LONG).show();


                                    })
                                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                        @SuppressLint("NewApi")
                                        public void onClick(DialogInterface dialog,int id) {

                                            dialog.cancel();

                                        }
                                    });

                                    // create alert dialog
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    // show it
                                    alertDialog.show();
                                }
                    });

                    return vi;
                }

                @Override
                public int getCount() {
                    // TODO Auto-generated method stub
                    return data.size();
                }



                @Override
                public Object getItem(int possision) {
                    // TODO Auto-generated method stub
                    return possision;
                }



                @Override
                public long getItemId(int possision) {
                    // TODO Auto-generated method stub
                    return possision;
                }
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
