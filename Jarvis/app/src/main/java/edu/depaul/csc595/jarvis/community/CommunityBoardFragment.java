package edu.depaul.csc595.jarvis.community;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.depaul.csc595.jarvis.R;
import edu.depaul.csc595.jarvis.community.adapters.MainCommunityPostAdapter;
import edu.depaul.csc595.jarvis.community.asynctasks.CommunityPostsAsync;
import edu.depaul.csc595.jarvis.community.models.CommunityPostMainModel;

/**
 * Created by Ed on 3/7/2016.
 */
public class CommunityBoardFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_community_board, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_community_recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        //List<CommunityPostMainModel> list = generateFakeList();

        List<CommunityPostMainModel> list = new ArrayList<>();

        MainCommunityPostAdapter adapter = new MainCommunityPostAdapter(list,getContext(),CommunityBoardActivity.activity);
        recyclerView.setAdapter(adapter);

        CommunityPostsAsync async = new CommunityPostsAsync();
        //needs: 1. Context, 2. CommunityBoardActivity, 3. recyclerview
        async.execute(this.getContext(),CommunityBoardActivity.activity,recyclerView);

        return rootView;
    }

    private List<CommunityPostMainModel> generateFakeList(){
        List<CommunityPostMainModel> modelList = new ArrayList<>();

        CommunityPostMainModel model;

        model = new CommunityPostMainModel();
        model.name = "Ed";
        model.content = "How do I fix my sump pump? It keeps flooding...";
        modelList.add(model);

        model = new CommunityPostMainModel();
        model.name = "Mark";
        model.content = "My house is on fire. Do I call 911?";
        modelList.add(model);

        model = new CommunityPostMainModel();
        model.name = "Advait";
        model.content = "Help. I've fallen and I can't get up.";
        modelList.add(model);

        model = new CommunityPostMainModel();
        model.name = "Dhaval";
        model.content = "Is it lefty loosey, righty tighty? Or the other way around?";
        modelList.add(model);

        model = new CommunityPostMainModel();
        model.name = "Clayton";
        model.content = "How many people does it take to fix a light bulb?";
        modelList.add(model);

        model = new CommunityPostMainModel();
        model.name = "Sarica";
        model.content = "How do you unclog a sink?";
        modelList.add(model);

        model = new CommunityPostMainModel();
        model.name = "Lavanya";
        model.content = "How do I register a smart device with Jarvis?";
        modelList.add(model);

        return modelList;
    }
}
