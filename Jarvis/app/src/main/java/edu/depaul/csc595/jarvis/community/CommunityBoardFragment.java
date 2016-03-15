package edu.depaul.csc595.jarvis.community;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.community.adapters.MainCommunityPostAdapter;
import edu.depaul.csc595.jarvis.community.asynctasks.CommunityPostsAsync;
import edu.depaul.csc595.jarvis.community.asynctasks.SendPostAsync;
import edu.depaul.csc595.jarvis.community.models.CommunityPostMainModel;
import edu.depaul.csc595.jarvis.profile.user.UserInfo;

/**
 * Created by Ed on 3/7/2016.
 */
public class CommunityBoardFragment extends Fragment implements PostDialogFragment.OnPostDialogResultListener, CommunityPostsAsync.ListListener {

    private List<CommunityPostMainModel> list;
    private RecyclerView recyclerView;
    private MainCommunityPostAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_community_board, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_community_recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        //List<CommunityPostMainModel> list = generateFakeList();

        list = new ArrayList<>();

        adapter = new MainCommunityPostAdapter(list,getContext(),CommunityBoardActivity.activity);
        recyclerView.setAdapter(adapter);

        CommunityPostsAsync async = new CommunityPostsAsync();
        //needs: 1. Context, 2. CommunityBoardActivity, 3. recyclerview
        async.execute(this.getContext(),CommunityBoardActivity.activity,recyclerView,this);

        //fab stuff
        TextView fab = (TextView)rootView.findViewById(R.id.createPost);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle creating a new post...
                enterPost();
            }
        });

        return rootView;
    }

    public void enterPost(){
        DialogFragment frag = new PostDialogFragment().newInstance(123);
        frag.setTargetFragment(this, 1);
        //frag.show(getActivity().getSupportFragmentManager(),"post");
        frag.show(getFragmentManager().beginTransaction(),"post");
    }

    public void onPost(String result){
        CommunityPostMainModel model = new CommunityPostMainModel();
        Log.d("commboardfrag", "onPost in fragment for onpost");
        if(UserInfo.getInstance().getIsLoggedIn() || UserInfo.getInstance().isGoogleLoggedIn()){
            if(UserInfo.getInstance().isGoogleLoggedIn()){
                model.name = UserInfo.getInstance().getGoogleAccount().getDisplayName();
                model.email = UserInfo.getInstance().getGoogleAccount().getEmail();
            }
            else{
                model.email = UserInfo.getInstance().getCredentials().getEmail();
                model.name = UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName();
            }
            model.post_id = " ";
            model.content = result;
            model.repliesCount = "0";
            this.list.add(model);
            adapter = new MainCommunityPostAdapter(list,getContext(),CommunityBoardActivity.activity);
            recyclerView.setAdapter(adapter);
            //kick off async task to send to server...
            SendPostAsync async = new SendPostAsync();
            async.execute(model);
        }
        else{
            Toast.makeText(getContext(),"Please log in to post",Toast.LENGTH_LONG).show();
        }
    }

    public void getList(List<CommunityPostMainModel> list){
        this.list = list;
    }

//    private List<CommunityPostMainModel> generateFakeList(){
//        List<CommunityPostMainModel> modelList = new ArrayList<>();
//
//        CommunityPostMainModel model;
//
//        model = new CommunityPostMainModel();
//        model.name = "Ed";
//        model.content = "How do I fix my sump pump? It keeps flooding...";
//        modelList.add(model);
//
//        model = new CommunityPostMainModel();
//        model.name = "Mark";
//        model.content = "My house is on fire. Do I call 911?";
//        modelList.add(model);
//
//        model = new CommunityPostMainModel();
//        model.name = "Advait";
//        model.content = "Help. I've fallen and I can't get up.";
//        modelList.add(model);
//
//        model = new CommunityPostMainModel();
//        model.name = "Dhaval";
//        model.content = "Is it lefty loosey, righty tighty? Or the other way around?";
//        modelList.add(model);
//
//        model = new CommunityPostMainModel();
//        model.name = "Clayton";
//        model.content = "How many people does it take to fix a light bulb?";
//        modelList.add(model);
//
//        model = new CommunityPostMainModel();
//        model.name = "Sarica";
//        model.content = "How do you unclog a sink?";
//        modelList.add(model);
//
//        model = new CommunityPostMainModel();
//        model.name = "Lavanya";
//        model.content = "How do I register a smart device with Jarvis?";
//        modelList.add(model);
//
//        return modelList;
//    }
}
