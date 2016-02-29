package edu.depaul.csc595.jarvis.detection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public List<Detection> getList(){
        return detections;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        @Bind(R.id.tv_category)
        TextView tv_category;

        @Bind(R.id.tv_date)
        TextView tv_date;

        @Bind(R.id.tv_notification)
        TextView tv_notification;

        public Holder(View view){
            ButterKnife.bind(this, view);
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Detection detection = detections.get(position);
        Holder holder;
        View rowView;
        if (convertView == null){
            rowView = inflater.inflate(R.layout.detection_item_list, null);
            holder = new Holder(rowView);
            rowView.setTag(holder);
        } else {
            rowView = convertView;
            holder = (Holder) rowView.getTag();
        }

        holder.tv_category.setText(detection.category);
        holder.tv_notification.setText(detection.notification);
        holder.tv_date.setText(detection.date_occurred);
        return rowView;
    }
}
