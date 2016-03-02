package edu.depaul.csc595.jarvis.detection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import edu.depaul.csc595.jarvis.detection.SPCustomAdapter.*;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.detection.classes.SmartProductContent;
import edu.depaul.csc595.jarvis.detection.classes.SmartProductContent.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import edu.depaul.csc595.jarvis.detection.DetectionService.*;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 */
public class SmartProductListFragment extends ListFragment {

    /**
     * The fragment's ListView
     */
    private ArrayList mListView;
    private Context mContext;
    private String email;
    private String LOG_TAG = "SmartProductListFragment";

    public static String EMAIL_EXTRA = "Email Address";


    private ProgressDialog mProgressDialog;

    private SPCustomAdapter mAdapter;

    public SmartProductListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mAdapter = new SPCustomAdapter(getActivity(), SmartProductContent.ITEMS);
        setListAdapter(mAdapter);
        updateSmartProducts();
        mContext = this.getActivity();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DetectionBaseActivity activity = (DetectionBaseActivity) getActivity();
        email = activity.getEmail();

        return inflater.inflate(R.layout.fragment_detection_list, container, false);
    }



    public void updateSmartProducts() {

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle("Loading SmartProducts");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();


        Retrofit retrofit = DetectionService.retrofit;
        DetectionInterface detectionInterface = retrofit.create(DetectionInterface.class);

        Log.d(LOG_TAG, "email: " + email);
        String request_email = (email == null) ? "test1@test.com" : email;
        Toast.makeText(getActivity(), "email is: " + email, Toast.LENGTH_SHORT).show();

        Call<List<SmartProduct>> call = detectionInterface.smart_products(request_email);
        call.enqueue(new Callback<List<SmartProduct>>() {
            @Override
            public void onResponse(Call<List<SmartProduct>> call, Response<List<SmartProduct>> response) {
                Log.d(LOG_TAG, "Reached this place");
                if (!response.isSuccess()) {
                    Log.d(LOG_TAG, response.errorBody().toString());
                }
                List<SmartProduct> smart_products = response.body();
                if (smart_products.isEmpty()) {
                    setEmptyText("No Smart Products Found");
                }
                SmartProductContent.ITEMS = smart_products;
                mAdapter.clear();
                mAdapter.addAll(smart_products);

                mAdapter.notifyDataSetChanged();
                Log.d(LOG_TAG, "Response returned by website is : " + response.body());
                Log.d(LOG_TAG, "Response returned by website is : " + response.code());
                mProgressDialog.hide();
            }

            @Override
            public void onFailure(Call<List<SmartProduct>> call, Throwable t) {
                Log.d(LOG_TAG, t.getMessage());
                mProgressDialog.hide();
                Toast.makeText(getActivity(), "Failed !", Toast.LENGTH_LONG).show();
            }
        });


    }
    public void onListItemClick(ListView l, View v, final int position, long id) {

        final List<SmartProduct> list =  mAdapter.getList();
        String smart_product_details =  "Appliance Name:  " +list.get(position).appliance_name+"\n"+"Serial No:  "+ list.get(position).serial_no +"\n"+"ID:  "+list.get(position).id;
        new AlertDialog.Builder(this.getActivity())
                .setTitle("Smart Product Details")
                .setMessage(smart_product_details)
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                //  Toast.makeText(getActivity(), "Lavanya", Toast.LENGTH_SHORT).show();

                                new AlertDialog.Builder(mContext)
                                        .setTitle("")
                                        .setMessage("Are you sure, you want to delete it?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dlg, int sumthin) {
                                                        // do whatever you want to do
                                                        //    Toast.makeText(getActivity(), "Lavanya", Toast.LENGTH_SHORT).show();
                                                        SmartProduct smart_product = (SmartProduct) mAdapter.getItem(position);
                                                        deleteSmartProducts(smart_product);
                                                        list.remove(position);
                                                        mAdapter.notifyDataSetChanged();


                                                    }
                                                }
                                        )
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dlg, int sumthin) {
                                                        // do whatever you want to do
                                                        Toast.makeText(getActivity(), "Item not deleted", Toast.LENGTH_SHORT).show();

                                                    }
                                                }

                                        ).

                                        show();

                            }
                        }

                ).

                show();

    }
    public void deleteSmartProducts(SmartProduct smart_product) {

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle("Loading SmartProducts");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();


        Retrofit retrofit = DetectionService.retrofit;
        DetectionInterface detectionInterface = retrofit.create(DetectionInterface.class);
        final List<SmartProduct> list =  mAdapter.getList();
        Log.d(LOG_TAG, "email: " + email);
        String request_email = (email == null) ? "test1@test.com" : email;
        Toast.makeText(getActivity(), "email is: " + email, Toast.LENGTH_SHORT).show();
        String serial_no = smart_product.serial_no;

        Call<ResponseBody> call = detectionInterface.deleteSmartProduct(request_email, serial_no);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(LOG_TAG, "Reached this place");
                if (!response.isSuccess()) {
                    Log.d(LOG_TAG, response.errorBody().toString());
                    Toast.makeText(getContext(), "Server error: Smart Product could not be deleted", Toast.LENGTH_SHORT).show();
                }

                Log.d(LOG_TAG, "Response returned by website is : " + response.body());
                Log.d(LOG_TAG, "Response returned by website is : " + response.code());
                mProgressDialog.hide();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(LOG_TAG, "Failed to delete smart product");
                mProgressDialog.hide();
                Toast.makeText(getActivity(), "Failed to Delete!", Toast.LENGTH_LONG).show();
            }
        });


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