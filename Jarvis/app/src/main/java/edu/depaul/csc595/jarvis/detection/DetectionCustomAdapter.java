package edu.depaul.csc595.jarvis.detection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.detection.classes.DetectionContent.Detection;

/**
 * Created by uchennafokoye on 2/22/16.
 */
public class DetectionCustomAdapter extends BaseAdapter {

    Context context;
    List<Detection> detections;

    private static LayoutInflater inflater = null;
    public DetectionCustomAdapter(Context context, List<Detection> detections){
        this.detections = detections;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void clear() {
        detections = null;
    }

    public void addAll(List<Detection> detections){
        this.detections = detections;
    }

    @Override
    public int getCount() {
        return detections.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView tv_category;
        TextView tv_date;
        TextView tv_notification;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Detection detection = detections.get(position);
        Holder holder = new Holder();
        View rowView;
        if (convertView == null){
            rowView = inflater.inflate(R.layout.detection_item_list, null);
        } else {
            rowView = convertView;
        }
        holder.tv_category = (TextView) rowView.findViewById(R.id.tv_category);
        holder.tv_date = (TextView) rowView.findViewById(R.id.tv_date);
        holder.tv_notification = (TextView) rowView.findViewById(R.id.tv_notification);
        holder.tv_category.setText(detection.category);
        holder.tv_notification.setText(detection.notification);
        holder.tv_date.setText(detection.date_occurred);
        return rowView;
    }
}
