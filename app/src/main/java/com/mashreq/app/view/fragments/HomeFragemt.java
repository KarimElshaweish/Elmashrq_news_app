package com.mashreq.app.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mashreq.app.R;
import com.mashreq.app.RoomCaching.DAO.NewsDAO.LatestNewsDAO.ILatestNewsDao;
import com.mashreq.app.RoomCaching.Database.AppDatabase;
import com.mashreq.app.adapter.CitesAdapter;
import com.mashreq.app.adapter.LatesNewsAdapter;
import com.mashreq.app.databinding.FragmentHomeFragemtBinding;
import com.mashreq.app.model.modeldb.Cities.CitesModel;
import com.mashreq.app.model.modeldb.ElmashrqNews.LatestNews;
import com.mashreq.app.model.modeldb.ElmashrqNews.News;
import com.mashreq.app.view.actvivties.AddNewsActivity;
import com.mashreq.app.viewmodel.CitesViewModel;
import com.mashreq.app.viewmodel.LatestNewsViewModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.List;

import dagger.hilt.EntryPoint;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 */
@AndroidEntryPoint
public class HomeFragemt extends Fragment {
    LatestNewsViewModel latestNewsViewModel;
    FragmentHomeFragemtBinding fragmentHomeFragemtBinding;
    LatesNewsAdapter latesNewsAdapter;
    CitesViewModel citesViewModel;
    public HomeFragemt(int type) {
        this.type=type;
        // Required empty public constructor
    }


    private int type;
    AppDatabase appDatabase;
    ILatestNewsDao iLatestNewsDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeFragemtBinding = FragmentHomeFragemtBinding.inflate(inflater, container, false);
        fragmentHomeFragemtBinding.addNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddNewsActivity.class));
            }
        });
        fillDate();
        getCites();
        appDatabase=AppDatabase.getInstance(getActivity().getApplicationContext());
        iLatestNewsDao=appDatabase.iLatestNewsDao();
        return fragmentHomeFragemtBinding.getRoot();
    }
    List<LatestNews> list;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textViewHome = getActivity().findViewById(R.id.textHome);
        textViewHome.setText("اخر الاخبار");
    }
    public void fillDate(){

        fragmentHomeFragemtBinding.newsRec.setHasFixedSize(true);
        fragmentHomeFragemtBinding.newsRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        latesNewsAdapter = new LatesNewsAdapter(getActivity());
        fragmentHomeFragemtBinding.newsRec.setAdapter(latesNewsAdapter);
            //   FragmentNearbyBinding.shimmerText.startShimmerAnimation();
        latestNewsViewModel = new ViewModelProvider(this).get(LatestNewsViewModel.class);
        if(isNetworkAvailable()) {
            getLastNews();
        }else{
            MDToast mdToast = MDToast.makeText(getActivity(), getString(R.string.no_internet),MDToast.LENGTH_LONG,MDToast.TYPE_INFO);
            mdToast.show();
            AppDatabase appDatabase=AppDatabase.getInstance(getContext());
//            final ILatestNewsDao iLatestNewsDao=appDatabase.iLatestNewsDao();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    list=iLatestNewsDao.getAllNews();
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            latesNewsAdapter.setnewsList(list);
//                        }
//                    });
//                }
//            }).start();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void getLastNews(){
        latestNewsViewModel.getResponse(type,1);
        latestNewsViewModel.getMutableLiveData().observe(getActivity(), new Observer<News>() {
            @Override
            public void onChanged(final News newsModelArray) {
//                if(newsModelArray!=null){
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            iLatestNewsDao.addLatestNews(newsModelArray.getData().getLatestNews());
//                        }
//                    }).start();
                switch (type){
                    case 0:
                        latesNewsAdapter.setnewsList(newsModelArray.getData().getLatestNews());
                        break;
                    case 1:
                        latesNewsAdapter.setnewsList(newsModelArray.getData().getAgelNews());
                        break;
                    case 2:
                        latesNewsAdapter.setnewsList(newsModelArray.getData().getMostRead());
                        break;
                }
//                }else{
//                    MDToast mdToast = MDToast.makeText(getActivity(), "لا يوجد اخبار حاليا",MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS);
//                    mdToast.show();
//                }
            }
        });
    }

    private void getCites(){
        fragmentHomeFragemtBinding.citesRV.setHasFixedSize(true);
        fragmentHomeFragemtBinding.citesRV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        citesViewModel=new ViewModelProvider(getActivity()).get(CitesViewModel.class);
        final CitesAdapter citesAdapter=new CitesAdapter(getContext());
        fragmentHomeFragemtBinding.citesRV.setAdapter(citesAdapter);
        citesViewModel.getCites();
        citesViewModel.getCitesViewModelMutableLiveData().observe(getActivity(), new Observer<CitesModel>() {
            @Override
            public void onChanged(CitesModel citesModel) {
                if (citesModel!=null)
                    citesAdapter.setNewList(citesModel.getData());
            }
        });
    }

}
