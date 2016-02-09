package edu.depaul.csc595.jarvis.rewards;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import edu.depaul.csc595.jarvis.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateRewardsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateRewardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRewardsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateRewardsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateRewardsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateRewardsFragment newInstance(String param1, String param2) {
        CreateRewardsFragment fragment = new CreateRewardsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_rewards, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //connect to the Internet (URL), get back the InputStream from it.
    private InputStream openConnection(String urlStr) {
        InputStream is = null;

        try {
            URL url = new URL("https://sandbox.tangocard.com/raas/v1.1");
            URLConnection con = url.openConnection();
            if (con instanceof HttpURLConnection) {
                HttpURLConnection httpURLConnection = (HttpURLConnection)con;
                int response = -1;

                //set parameters
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
                httpURLConnection.setRequestProperty("User", "ConnectedHomeTest");
                httpURLConnection.setRequestProperty("Password", "9ZvkAtLt2BkzAKXtybuMlMXxB2wIZLZcVArHSGwq2WXEhfWfNCfsEEiyo");
                httpURLConnection.setRequestProperty("customer", "csc595g1_03");
                httpURLConnection.setRequestProperty("identifier", "csc595g1_03");
                httpURLConnection.setRequestProperty("email", "csc595g1_03@depaul.edu");

//Responses:
//When sending duplicate
//{
//    "success": false,
//        "error_message": "The account already exists for the platform."
//}
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



}
