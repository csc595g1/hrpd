package edu.depaul.csc595.jarvis.reminders.adapters;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.reminders.ui.activities.CreateEditActivity;
import edu.depaul.csc595.jarvis.reminders.models.Icon;

/**
 * Created by Advait on 18-02-2016.
 */
public class IconsAdapter extends RecyclerView.Adapter<IconsAdapter.ViewHolder>{

    private int mRowLayout;
    private Context mContext;
    private List<Icon> mIconList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon) ImageView mImageView;
        private View mView;

        public ViewHolder(final View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }

    public IconsAdapter(Context context, int rowLayout, List<Icon> iconList) {
        mContext = context;
        mRowLayout = rowLayout;
        mIconList = iconList;
    }

    @Override
    public int getItemCount() {
        return mIconList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(mRowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final String iconName = mIconList.get(position).getName();
        final int iconResId = mContext.getResources().getIdentifier(iconName, "drawable", mContext.getPackageName());
        viewHolder.mImageView.setImageResource(iconResId);
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CreateEditActivity) mContext).iconSelected(iconResId, mIconList.get(position));
            }
        });
    }
}
