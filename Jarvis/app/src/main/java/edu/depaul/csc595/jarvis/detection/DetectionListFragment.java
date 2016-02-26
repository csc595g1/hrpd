package edu.depaul.csc595.jarvis.detection;

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.depaul.csc595.jarvis.detection.DetectionService.*;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.detection.classes.DetectionContent;
import edu.depaul.csc595.jarvis.detection.classes.DetectionContent.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 */
public class DetectionListFragment extends ListFragment {

    /**
     * The fragment's ListView
     */
    private ListView mListView;
    private Context mContext;
    private String email;
    private String LOG_TAG = "DetectionListFragment";

    private DetectionCustomAdapter mAdapter;

    public DetectionListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mAdapter = new DetectionCustomAdapter(getActivity(), DetectionContent.ITEMS);
        setListAdapter(mAdapter);

        mListView = (ListView) getActivity().findViewById(android.R.id.list);


        Retrofit retrofit = DetectionService.retrofit;
        DetectionInterface detectionInterface = retrofit.create(DetectionInterface.class);

        Log.d(LOG_TAG, email);
        Call<List<Detection>> call = detectionInterface.detections(email);

        List<Detection> mydetections = new ArrayList<Detection>();
        call.enqueue(new Callback<List<Detection>>() {
            @Override
            public void onResponse(Call<List<Detection>> call, Response<List<Detection>> response) {
                Log.d(LOG_TAG, "Reached this place");
                if (!response.isSuccess()) {
                    Log.d(LOG_TAG, response.errorBody().toString());
                }
                List<Detection> detections = response.body();
                if (detections.isEmpty()){
                    setEmptyText("No Detections Found");
                }
                mAdapter.clear();
                mAdapter.addAll(detections);
                mAdapter.notifyDataSetChanged();
                Log.d(LOG_TAG, "Response returned by website is : " + response.body());
                Log.d(LOG_TAG, "Response returned by website is : " + response.code());

            }

            @Override
            public void onFailure(Call<List<Detection>> call, Throwable t) {
                Log.d(LOG_TAG, t.getMessage());

                Toast.makeText(getActivity(), "Failed !", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DetectionBaseActivity activity = (DetectionBaseActivity) getActivity();
        email = activity.getEmail();
        return inflater.inflate(R.layout.fragment_detection_list, container, false);
    }


    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = getListView().getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }

    }

}