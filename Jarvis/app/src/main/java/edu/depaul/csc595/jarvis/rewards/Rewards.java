package edu.depaul.csc595.jarvis.rewards;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by markwilhelm on 1/31/16.
 */
public class Rewards {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";

    private static final String GET_URL = "http://api.openweathermap.org/data/2.5/weather?q=Palatine&units=metric&APPID=e4551aeb5ae3ed6e932934042c36248d";

    private static final String POST_URL = "http://localhost:9090/SpringMVCExample/home";

    public String sendGET()  {
//        URL obj = new URL(GET_URL);
//        URL obj = new URL("http://www.android.com/");
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("GET");
//        con.setRequestProperty("Content-Type", CONTENT_TYPE);
//        int responseCode = con.getResponseCode();
//        System.out.println("GET Response Code :: " + responseCode);
//        if (responseCode == HttpURLConnection.HTTP_OK) { // success
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    con.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            // print result
//            System.out.println(response.toString());
//        } else {
//            System.out.println("GET request not worked");
//        }
//

        DownloadTask downloadTask = new DownloadTask();


        String result = null;
        downloadTask.execute(GET_URL, null, result);

        return result;

    }


    //connect to the Internet (URL), get back the InputStream from it.
    private InputStream openConnection(String urlStr) {
        InputStream is = null;

        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            if (con instanceof HttpURLConnection) {
                HttpURLConnection httpURLConnection = (HttpURLConnection)con;
                int response = -1;

                //connect
                httpURLConnection.connect();
                response = httpURLConnection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    is = httpURLConnection.getInputStream();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return is;
    }

    //put this long running task into the AsyncTask
    private class DownloadTask extends AsyncTask<String, Void, String> {

        //Convert the is to a string
        private String convertStreamToString(InputStream is) {
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            }
            catch (IOException e) { e.printStackTrace(); }
            catch(Exception e) { e.printStackTrace(); }

            return sb.toString();
        }

        @Override
        protected String doInBackground(String... urls) {
            String url = urls[0];
            int count = urls.length;
            long totalSize = 0;
            InputStream is = openConnection(url);

            return convertStreamToString(is);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //showDialog("Downloaded " + result + " bytes");
        }


    }

    //Get data and return JSONObject
    private void convertDataToJSON() {
        String response = "";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("response", response);
        }
        catch (Exception e) {e.printStackTrace(); }


    }


}
