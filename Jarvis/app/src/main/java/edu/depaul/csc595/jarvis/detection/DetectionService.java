package edu.depaul.csc595.jarvis.detection;

/**
 * Created by uchennafokoye on 2/23/16.
 */

import java.io.IOException;
import java.util.List;

import edu.depaul.csc595.jarvis.detection.classes.DetectionContent.Detection;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class DetectionService {

    public static final String API_URL = "http://detectionservices.herokuapp.com";

    public interface DetectionInterface {
        @GET("/users/{email_address}/detections")
        Call<List<Detection>> detections(
                @Path("email_address") String email_address);
    }

    public static void main(String... args) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DetectionInterface detectionInterface = retrofit.create(DetectionInterface.class);

        Call<List<Detection>> call = detectionInterface.detections("test1@test.com");
        List<Detection> detections = call.execute().body();
        for (Detection detection : detections){
            System.out.print(detection.notification + " " + detection.category + " " + detection.date_occurred);
            System.out.println("");
        }



    }

}
