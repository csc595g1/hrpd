package edu.depaul.csc595.jarvis.detection;

/**
 * Created by uchennafokoye on 2/23/16.
 */

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import edu.depaul.csc595.jarvis.detection.classes.DetectionContent.Detection;
import edu.depaul.csc595.jarvis.detection.classes.MobileDevice;
import edu.depaul.csc595.jarvis.detection.classes.SmartProductContent;
import edu.depaul.csc595.jarvis.detection.classes.SmartProductContent.SmartProductInfo;
import edu.depaul.csc595.jarvis.detection.classes.SmartProductContent.SmartProduct;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class DetectionService {

    public static String LOG_TAG = "DetectionService";

    public static final String API_URL = "http://detectionservices.herokuapp.com";
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public interface DetectionInterface {
        @GET("/users/{email_address}/detections")
        Call<List<Detection>> detections(
                @Path("email_address") String email_address);

        @GET("/users/{email_address}/smart_products")
        Call<List<SmartProduct>> smart_products(
                @Path("email_address") String email_address);

        @POST("/users/{email_address}/smart_products/new")
        Call<SmartProduct> createSmartProduct(
                @Path("email_address") String email_address, @Body SmartProduct smartProduct);

        @GET("/users/{email_address}/smart_products/count")
        Call<SmartProductInfo> get_total_registered_smart_products(
                @Path("email_address") String email_address);

        @POST("/users/{email_address}/gcm_tokens/{gcm_token}/new")
        Call<MobileDevice> registerGCMToken(
                @Path("email_address") String email_address, @Path("gcm_token") String gcm_token);

        @POST("/update_gcm_token/{old_token}/{new_token}")
        Call<MobileDevice> updateGCMToken(
                @Path("old_token") String old_token, @Path("new_token") String new_token);

        @DELETE("/users/{email_address}/smart_products/{serial_no}/delete")
        Call<ResponseBody> deleteSmartProduct(
                @Path("email_address") String email_address, @Path("serial_no") String serial_no);
    }

    public static void main(String... args) throws IOException {

        DetectionInterface detectionInterface = retrofit.create(DetectionInterface.class);
//
//        Call<List<Detection>> call = detectionInterface.detections("test1@test.com");
//        List<Detection> detections = call.execute().body();
//        for (Detection detection : detections){
//            System.out.print(detection.notification + " " + detection.category + " " + detection.date_occurred);
//            System.out.println("");
//        }
//
//        Call<List<SmartProduct>> call3 = detectionInterface.smart_products("test1@test.com");
//        List<SmartProduct> smart_products = call3.execute().body();
//        for (SmartProduct smart_product : smart_products ){
//            System.out.print(smart_product.serial_no + " " + smart_product.type_of_smart_product + " " + smart_product.appliance_name);
//            System.out.println("");
//        }
//
//        Call<MobileDevice> registerGCMTokenCall = detectionInterface.registerGCMToken("thuxtable@gmail.com", "fuymvlouXLE:APA91bGdJnJhf46aZi4pCQlTy1wFlpneMzsXO_vU9kFY94t8di8CYlLQAVEXqU7YLzWD_05UmXKIUsLla8tQPEwGJrI28uhOQKSCj-IrEAoL9emqA3fg_FIpl5SyT5DKs52OvSj9fx_S");
//        MobileDevice mobileDevice = registerGCMTokenCall.execute().body();
//        if (mobileDevice != null){
//            //System.out.print("GCM Token: " + mobileDevice.gcm_token);
//        }
//
//        Call<MobileDevice> registerGCMTokenCall2 = detectionInterface.registerGCMToken("test2@test.com", "my_test_token_from_retrofit");
//        MobileDevice mobileDevice2 = registerGCMTokenCall2.execute().body();
//        if (mobileDevice2 != null){
//            System.out.print("GCM Token: " + mobileDevice2.gcm_token);
//        }
//
//        Call<MobileDevice> updateGCMTokenCall = detectionInterface.updateGCMToken("my_test_token_from_retrofit", "my_updated_test_token_from_retrofit");
//        MobileDevice updatedMobileDevice = updateGCMTokenCall.execute().body();
//        if (updatedMobileDevice != null){
//            //System.out.print("GCM Token: " + updatedMobileDevice.gcm_token);
//        }
////
//
//        Call<SmartProduct> call2 = detectionInterface.createSmartProduct("test1@test.com", SmartProductContent.ITEMS.get(1));
//        SmartProduct smartProduct = call2.execute().body();
//        if (smartProduct != null){
//            System.out.println(smartProduct.serial_no + " " +  smartProduct.appliance_name + " " + smartProduct.type_of_smart_product);
//        }
//
//        Call<ResponseBody> call4 = detectionInterface.deleteSmartProduct("test1@test.com", SmartProductContent.ITEMS.get(1).serial_no);
//        Response response = call4.execute();
//        System.out.println("Response is success: " + Boolean.toString(response.isSuccess()));

        String email_address = "test1@test.com";
        Call<SmartProductInfo> call = detectionInterface.get_total_registered_smart_products(email_address);
        call.enqueue(new Callback<SmartProductInfo>() {
            @Override
            public void onResponse(Call<SmartProductInfo> call, Response<SmartProductInfo> response) {
                if (!response.isSuccess()) {
                    Log.d(LOG_TAG, "Unable to complete request to get smart product count");
                    System.out.println("Unable to complete request to get smart product");
                    return;
                }
                SmartProductInfo smart_products_info = response.body();
                System.out.println("From loop: " + smart_products_info.total_smart_products);
                // Do whatever you want in here
                // For example: tv_total.setText(smart_products_info.total_smart_products);
            }

            @Override
            public void onFailure(Call<SmartProductInfo> call, Throwable t) {
                Log.d(LOG_TAG, t.getMessage());
            }
        });

    }

}