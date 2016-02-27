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

    public TextView tv1,tv2;
    public ImageView imageView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        tv1 = (TextView) itemView.findViewById(R.id.list_title);
        tv2 = (TextView) itemView.findViewById(R.id.list_desc);
        imageView = (ImageView) itemView.findViewById(R.id.list_avatar);

    }

}
