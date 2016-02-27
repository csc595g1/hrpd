package edu.depaul.csc595.jarvis.inventory.data;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.depaul.csc595.jarvis.R;

/**
 * Created by Advait on 27-02-2016.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView appliancesTitle,appliancesTimeInterval,appliancesNextReminder;
    public ImageView imageView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        appliancesTitle = (TextView) itemView.findViewById(R.id.appliances_card_title);
        appliancesTimeInterval = (TextView) itemView.findViewById(R.id.appliances_card_reminder_interval);
        appliancesNextReminder = (TextView) itemView.findViewById(R.id.appliances_card_next_reminder);
        imageView = (ImageView) itemView.findViewById(R.id.list_avatar);

    }

}
