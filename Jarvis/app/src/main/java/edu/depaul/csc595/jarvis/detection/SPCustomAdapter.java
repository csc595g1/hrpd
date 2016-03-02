package edu.depaul.csc595.jarvis.detection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.detection.classes.SmartProductContent.SmartProduct;

/**
 * Created by uchennafokoye on 2/22/16.
 */
public class SPCustomAdapter extends BaseAdapter {

    Context context;
    List<SmartProduct> smart_products;

    private static LayoutInflater inflater = null;
    public SPCustomAdapter(Context context, List<SmartProduct> smart_products){
        this.smart_products = smart_products;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void clear() {
        smart_products = null;
    }

    public void addAll(List<SmartProduct> detections){
        this.smart_products = detections;
    }

    @Override
    public int getCount() {
        return smart_products.size();
    }

    @Override
    public Object getItem(int position) {
        return smart_products.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    public List<SmartProduct> getList(){
        return this.smart_products;
    }

    public class Holder {
        TextView tv_category;
        TextView tv_date;
        TextView tv_notification;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SmartProduct smart_product = smart_products.get(position);
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
        holder.tv_category.setText(smart_product.type_of_smart_product);
        holder.tv_notification.setText(smart_product.toString());
        holder.tv_date.setText(smart_product.appliance_name);
        return rowView;
    }
}
