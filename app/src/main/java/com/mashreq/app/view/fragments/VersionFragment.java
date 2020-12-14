package com.mashreq.app.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mashreq.app.R;
import com.mashreq.app.adapter.LatesNewsAdapter;
import com.mashreq.app.adapter.VersionAdapter;
import com.mashreq.app.databinding.FragmentHomeFragemtBinding;
import com.mashreq.app.databinding.FragmentVersionBinding;
import com.mashreq.app.model.modeldb.ElmashrqManualData.ElmashrqNews;
import com.mashreq.app.model.modeldb.ElmashrqNews.News;
import com.mashreq.app.model.modeldb.export.ExportModel;
import com.mashreq.app.viewmodel.AllExportsViewModel;
import com.mashreq.app.viewmodel.ElamshrqViewModel;
import com.mashreq.app.viewmodel.LatestNewsViewModel;
import com.mashreq.app.viewmodel.VersionViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VersionFragment extends Fragment {
    FragmentVersionBinding fragmentVersionBinding;
    VersionAdapter versionAdapter;
    VersionViewModel versionViewModel;

    AllExportsViewModel elamshrqViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentVersionBinding = FragmentVersionBinding.inflate(inflater, container, false);
        elamshrqViewModel=new ViewModelProvider(this).get(AllExportsViewModel.class);
        fillDate();
        return fragmentVersionBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textViewHome = getActivity().findViewById(R.id.textHome);
        textViewHome.setText("إصدارات المشرق");
    }
    public void fillDate(){
        fragmentVersionBinding.newsRec.setHasFixedSize(true);
        fragmentVersionBinding.newsRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        versionAdapter = new VersionAdapter(getActivity());
        fragmentVersionBinding.newsRec.setAdapter(versionAdapter);
        //   FragmentNearbyBinding.shimmerText.startShimmerAnimation();
       // versionViewModel = ViewModelProviders.of(this).get(VersionViewModel.class);

        elamshrqViewModel.getResponse();
        elamshrqViewModel.getExportModelMutableLiveData().observe(getActivity(), new Observer<ExportModel>() {
            @Override
            public void onChanged(ExportModel exportModel) {
                versionAdapter.setnewsList(exportModel.getData());
            }
        });
    }
}
