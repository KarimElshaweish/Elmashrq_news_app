package com.mashreq.app.view.actvivties;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mashreq.app.R;
import com.mashreq.app.adapter.LatesNewsAdapter;
import com.mashreq.app.databinding.ActivityCitesBinding;
import com.mashreq.app.model.modeldb.CitesNews.CitesNews;
import com.mashreq.app.viewmodel.CityNewsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class citesActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityCitesBinding activityCitesBinding;

    CityNewsViewModel citesViewModel;
    LatesNewsAdapter latesNewsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCitesBinding=ActivityCitesBinding.inflate(getLayoutInflater());
        setContentView(activityCitesBinding.getRoot());
        activityCitesBinding.backButton.setOnClickListener(this);
        activityCitesBinding.rv.setHasFixedSize(true);
        activityCitesBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        latesNewsAdapter=new LatesNewsAdapter(this);
        String cityName=getIntent().getStringExtra("city_name");
        int id=getIntent().getIntExtra("city_id",0);
        citesViewModel= new ViewModelProvider(this).get(CityNewsViewModel.class);
        activityCitesBinding.rv.setAdapter(latesNewsAdapter);
        activityCitesBinding.cityName.setText(cityName);
        fillData(String.valueOf(id));
    }

    private void fillData(String id){
        citesViewModel.getCityNews(id);
        citesViewModel.getMutableLiveData().observe(this, new Observer<CitesNews>() {
            @Override
            public void onChanged(CitesNews citesNews) {
                latesNewsAdapter.setnewsList(citesNews.getData());
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton:
                finish();
                break;
        }
    }
}
