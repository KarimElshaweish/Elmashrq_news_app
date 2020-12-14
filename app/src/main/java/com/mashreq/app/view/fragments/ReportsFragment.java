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
import android.widget.Toast;

import com.mashreq.app.LoadingDialog.LoadingDailog;
import com.mashreq.app.R;
import com.mashreq.app.adapter.LatesNewsAdapter;
import com.mashreq.app.adapter.ReportsAdapter;
import com.mashreq.app.databinding.FragmentHomeFragemtBinding;
import com.mashreq.app.databinding.FragmentReportsBinding;
import com.mashreq.app.model.modeldb.Report.ReportsModel;
import com.mashreq.app.viewmodel.LatestNewsViewModel;
import com.mashreq.app.viewmodel.ReportsViewModel;

import dagger.hilt.EntryPoint;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 */

@AndroidEntryPoint
public class ReportsFragment extends Fragment {
    ReportsViewModel reportsViewModel;
    FragmentReportsBinding fragmentReportsBinding;
    ReportsAdapter reportsAdapter;


    public ReportsFragment() {
        // Required empty public constructor
    }
    LoadingDailog loadingDailog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentReportsBinding = FragmentReportsBinding.inflate(inflater, container, false);
        loadingDailog=new LoadingDailog(getContext());
        fillDate();


        return fragmentReportsBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textViewHome = getActivity().findViewById(R.id.textHome);
        textViewHome.setText("أهم التقارير");
    }

    public void fillDate(){
        loadingDailog.showDialog();
        fragmentReportsBinding.newsRec.setHasFixedSize(true);
        fragmentReportsBinding.newsRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        reportsAdapter = new ReportsAdapter(getActivity());
        fragmentReportsBinding.newsRec.setAdapter(reportsAdapter);
        //   FragmentNearbyBinding.shimmerText.startShimmerAnimation();
        reportsViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        reportsViewModel.getResponse("3");
        reportsViewModel.getMutableLiveData().observe(getActivity(), new Observer<ReportsModel>() {
            @Override
            public void onChanged(ReportsModel reportsModel) {
                if(reportsModel.getData()!=null)
                    reportsAdapter.setnewsList(reportsModel.getData());
                else
                    Toast.makeText(getContext(),reportsModel.getMessage(),Toast.LENGTH_SHORT).show();

                loadingDailog.dismissDialog();
            }
        });
    }
}
