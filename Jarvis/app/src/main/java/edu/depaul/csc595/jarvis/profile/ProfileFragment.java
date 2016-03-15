package edu.depaul.csc595.jarvis.profile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Retrofit;
import java.io.IOException;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.detection.DetectionService;
import edu.depaul.csc595.jarvis.detection.RegisterDeviceToken;
import edu.depaul.csc595.jarvis.detection.classes.SmartProductContent;
import edu.depaul.csc595.jarvis.detection.gcm.TokenIntentService;
import edu.depaul.csc595.jarvis.profile.user.User;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;
import edu.depaul.csc595.jarvis.rewards.HerokuAPI.GetTotalPointsAsyncTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ed on 2/23/2016.
 */
public class ProfileFragment extends Fragment {

    public static final int GET_FROM_GALLERY = 3;

    private String email_address;
    private final String TAG = "ProfileFragment";
    private GetTotalPointsAsyncTask pointsTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView frag_prof_email = (TextView) rootView.findViewById(R.id.frag_prof_email);
        TextView frag_prof_name = (TextView) rootView.findViewById(R.id.frag_prof_name);
        TextView frag_prof_points = (TextView) rootView.findViewById(R.id.frag_prof_points);
        final TextView frag_prof_reg_dev = (TextView)rootView.findViewById(R.id.frag_prof_reg_devices);
        if(UserInfo.getInstance().getIsLoggedIn()||UserInfo.getInstance().isGoogleLoggedIn()) {
            if(UserInfo.getInstance().isGoogleLoggedIn()){email_address = UserInfo.getInstance().getGoogleAccount().getEmail();}
            else{email_address = UserInfo.getInstance().getCredentials().getEmail();}
            Retrofit retrofit = DetectionService.retrofit;
            DetectionService.DetectionInterface detectionInterface = retrofit.create(DetectionService.DetectionInterface.class);
            Call<SmartProductContent.SmartProductInfo> call = detectionInterface.get_total_registered_smart_products(email_address);
            call.enqueue(new Callback<SmartProductContent.SmartProductInfo>() {
                @Override
                public void onResponse(Call<SmartProductContent.SmartProductInfo> call, Response<SmartProductContent.SmartProductInfo> response) {
                    if (!response.isSuccess()) {
                        Log.d("getnumregdecives", "Unable to complete request to get smart product count");
                        return;
                    }
                    SmartProductContent.SmartProductInfo smart_products_info = response.body();
                    System.out.println("From loop: " + smart_products_info.total_smart_products);
                    // Do whatever you want in here
                    // For example: tv_total.setText(smart_products_info.total_smart_products);
                    frag_prof_reg_dev.setText(String.valueOf(smart_products_info.total_smart_products));
                }

                @Override
                public void onFailure(Call<SmartProductContent.SmartProductInfo> call, Throwable t) {
                    //Log.d("getnumregdecives", t.getMessage());
                }
            });
        }
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        Button registerDeviceBtn = (Button) rootView.findViewById(R.id.button_register_device);
        pointsTask = new GetTotalPointsAsyncTask();

        if(UserInfo.getInstance().getIsLoggedIn()){
            User user = UserInfo.getInstance().getCredentials();
            frag_prof_email.setText(user.getEmail());
            frag_prof_name.setText(UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName());
            email_address = user.getEmail();
            pointsTask.execute(ProfileFragment.this,frag_prof_points);
        }
        else if(UserInfo.getInstance().isGoogleLoggedIn()){
            frag_prof_email.setText(UserInfo.getInstance().getGoogleAccount().getEmail());
            frag_prof_name.setText(UserInfo.getInstance().getGoogleAccount().getDisplayName());
            email_address = UserInfo.getInstance().getGoogleAccount().getEmail();
            pointsTask.execute(ProfileFragment.this,frag_prof_points);
        }

//        Call<SmartProductContent.SmartProductInfo> call = detectionInterface.get_total_registered_smart_products(email_address);
//        call.enqueue(new Callback<SmartProductContent.SmartProductInfo>() {
//            @Override
//            public void onResponse(Call<SmartProductContent.SmartProductInfo> call, Response<SmartProductContent.SmartProductInfo> response) {
//                if (!response.isSuccess()) {
//                    Log.d(TAG, "Unable to complete request to get smart product count");
//                    return;
//                }
//                SmartProductContent.SmartProductInfo smart_products_info = response.body();
//                System.out.println("From loop: " + smart_products_info.total_smart_products);
//                // Do whatever you want in here
//                // For example: tv_total.setText(smart_products_info.total_smart_products);
//                if( smart_products_info.total_smart_products > 0){
//                    frag_prof_reg_dev.setText(String.valueOf(smart_products_info.total_smart_products));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SmartProductContent.SmartProductInfo> call, Throwable t) {
//                Log.d(TAG, t.getMessage());
//            }
//        });

        registerDeviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch the device token.
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String token = sharedPreferences.getString(TokenIntentService.GCM_TOKEN, "");

                // Set url for the web service
                String webServiceUrl = "https://detectionservices.herokuapp.com/register_gcm_token";

                // Register this device(token) on the server.
                new RegisterDeviceToken(getContext()).execute(email_address, token, webServiceUrl);
                Log.d(TAG, "Email_Address = " + email_address);
            }
        });

        TextView change_pic = (TextView) rootView.findViewById(R.id.prof_change_pic);
        change_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI),GET_FROM_GALLERY);
            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK){
            Uri imageSelected = data.getData();
            Bitmap bitmap = null;
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageSelected);
                de.hdodenhof.circleimageview.CircleImageView iv = (de.hdodenhof.circleimageview.CircleImageView) this.getView().findViewById(R.id.imageView_profile);
                iv.setImageBitmap(bitmap);
                UserInfo.getInstance().setCustomProfilePicture(bitmap);
                UserInfo.getInstance().setHasCustomProfilePicture(true);
                iv.refreshDrawableState();
            }
            catch(IOException e){}
        }
    }
}
