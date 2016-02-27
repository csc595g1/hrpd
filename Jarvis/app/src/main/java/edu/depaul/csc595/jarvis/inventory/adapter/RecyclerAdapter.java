package edu.depaul.csc595.jarvis.inventory.adapter;

/**
 * Created by Advait on 27-02-2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.inventory.data.RecyclerViewHolder;

public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerViewHolder> {

    String [] name = {
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

    String [] reminder_time = {
            "at least once a month",
            "at least twice a year",
            "at least once a month",
            "at least once a year",
            "at least once a month",
            "at least twice a month",
            "at least once a year",
            "every two years",
            "at least twice a month",
            "at least once a month",
            "at least once a year",
            "after every six months",
            "at least twice a year",
            "at least twice a year",
            "once every three months",
            "at least twice a year",
            "at least once a month"
    };

    Context context;
    LayoutInflater inflater;

    public RecyclerAdapter(Context context)
    {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = inflater.inflate(R.layout.activity_appliances_cardview_row, parent, false);

        RecyclerViewHolder viewHolder=new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.tv1.setText(name[position]);
        holder.tv2.setText(reminder_time[position]);
        holder.imageView.setOnClickListener(clickListener);
        holder.imageView.setTag(holder);

    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            RecyclerViewHolder vholder = (RecyclerViewHolder) v.getTag();
            int position = vholder.getPosition();

            Toast.makeText(context,"This is position "+position,Toast.LENGTH_LONG ).show();

        }
    };



    @Override
    public int getItemCount() {
        return name.length;
    }
}
