package edu.depaul.csc595.jarvis.detection;

/**
 * Created by uchennafokoye on 2/23/16.
 */

import java.io.IOException;
import java.util.List;

import edu.depaul.csc595.jarvis.detection.classes.DetectionContent;
import edu.depaul.csc595.jarvis.detection.classes.DetectionContent.Detection;
import edu.depaul.csc595.jarvis.detection.classes.SmartProductContent;
import edu.depaul.csc595.jarvis.detection.classes.SmartProductContent.SmartProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class DetectionService {

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
    }


    public static void main(String... args) throws IOException {

        DetectionInterface detectionInterface = retrofit.create(DetectionInterface.class);

        Call<List<Detection>> call = detectionInterface.detections("test1@test.com");
        List<Detection> detections = call.execute().body();
        for (Detection detection : detections){
            System.out.print(detection.notification + " " + detection.category + " " + detection.date_occurred);
            System.out.println("");
        }



        Call<SmartProduct> call2 = detectionInterface.createSmartProduct("test1@test.com", SmartProductContent.ITEMS.get(1));
        SmartProduct smartProduct = call2.execute().body();
        if (smartProduct != null){
            System.out.print(smartProduct.serial_no + " " +  smartProduct.appliance_name + " " + smartProduct.type_of_smart_product);
        }

        Call<List<SmartProduct>> call3 = detectionInterface.smart_products("test1@test.com");
        List<SmartProduct> smart_products = call3.execute().body();
        for (SmartProduct smart_product : smart_products ){
            System.out.print(smart_product.serial_no + " " + smart_product.type_of_smart_product + " " + smart_product.appliance_name);
            System.out.println("");
        }



    }

}