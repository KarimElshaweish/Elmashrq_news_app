package com.mashreq.app.view.actvivties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mashreq.app.R;
import com.mashreq.app.databinding.ActivityHomePageBinding;
import com.mashreq.app.databinding.ActivitySplashScreenBinding;
import com.mashreq.app.view.fragments.HomeFragemt;
import com.mashreq.app.view.fragments.MyAccount;
import com.mashreq.app.view.fragments.ReportsFragment;
import com.mashreq.app.view.fragments.VersionFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomePage extends AppCompatActivity {
    ActivityHomePageBinding activityHomePageBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomePageBinding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(activityHomePageBinding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragemt(0)).commit();
        activityHomePageBinding.newsTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.news_active,0,0);
        activityHomePageBinding.reportsTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.reports,0,0);
        activityHomePageBinding.accoutTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.account,0,0);
        activityHomePageBinding.soursTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.sourse,0,0);
        activityHomePageBinding.versinTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.versions,0,0);
        clickLis();
        changeDrawable();


        activityHomePageBinding.liveStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),LiveStreamActivity.class));
            }
        });
    }
    public void clickLis(){
        activityHomePageBinding.lastNews.setTextColor(getResources().getColor(R.color.colorAccent));
        activityHomePageBinding.agelNews.setTextColor(getResources().getColor(R.color.color_not_activ));
        activityHomePageBinding.moreNews.setTextColor(getResources().getColor(R.color.color_not_activ));
        activityHomePageBinding.lastNews.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.diver);
        activityHomePageBinding.agelNews.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        activityHomePageBinding.moreNews.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);

        activityHomePageBinding.lastNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityHomePageBinding.lastNews.setTextColor(getResources().getColor(R.color.colorAccent));
                activityHomePageBinding.agelNews.setTextColor(getResources().getColor(R.color.color_not_activ));
                activityHomePageBinding.moreNews.setTextColor(getResources().getColor(R.color.color_not_activ));
                activityHomePageBinding.lastNews.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.diver);
                activityHomePageBinding.agelNews.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                activityHomePageBinding.moreNews.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
             getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragemt(0)).commit();
            }
        });
        activityHomePageBinding.agelNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityHomePageBinding.agelNews.setTextColor(getResources().getColor(R.color.colorAccent));
                activityHomePageBinding.lastNews.setTextColor(getResources().getColor(R.color.color_not_activ));
                activityHomePageBinding.moreNews.setTextColor(getResources().getColor(R.color.color_not_activ));
                activityHomePageBinding.agelNews.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.diver);
                activityHomePageBinding.lastNews.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                activityHomePageBinding.moreNews.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
             getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragemt(1)).commit();
            }
        });
        activityHomePageBinding.moreNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityHomePageBinding.moreNews.setTextColor(getResources().getColor(R.color.colorAccent));
                activityHomePageBinding.agelNews.setTextColor(getResources().getColor(R.color.color_not_activ));
                activityHomePageBinding.lastNews.setTextColor(getResources().getColor(R.color.color_not_activ));
                activityHomePageBinding.moreNews.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.diver);
                activityHomePageBinding.agelNews.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                activityHomePageBinding.lastNews.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
           getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragemt(2)).commit();
            }
        });
    }
    public void changeDrawable(){
       activityHomePageBinding.newsTv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               activityHomePageBinding.rel.setVisibility(View.VISIBLE);
               getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragemt(2)).commit();
               activityHomePageBinding.newsTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.news_active,0,0);
               activityHomePageBinding.reportsTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.reports,0,0);
               activityHomePageBinding.accoutTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.account,0,0);
               activityHomePageBinding.soursTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.sourse,0,0);
               activityHomePageBinding.versinTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.versions,0,0);
           }
       });
        activityHomePageBinding.accoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new MyAccount()).commit();
                activityHomePageBinding.newsTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.news_not_active,0,0);
                activityHomePageBinding.reportsTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.reports,0,0);
                activityHomePageBinding.accoutTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.myaccount_active,0,0);
                activityHomePageBinding.soursTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.sourse,0,0);
                activityHomePageBinding.versinTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.versions,0,0);
            }
        });
        activityHomePageBinding.versinTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new VersionFragment()).commit();
                activityHomePageBinding.newsTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.news_not_active,0,0);
                activityHomePageBinding.reportsTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.reports,0,0);
                activityHomePageBinding.accoutTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.account,0,0);
                activityHomePageBinding.soursTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.sourse,0,0);
                activityHomePageBinding.versinTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.version_actvie,0,0);
            }
        });
        activityHomePageBinding.reportsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityHomePageBinding.rel.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new ReportsFragment()).commit();
                activityHomePageBinding.newsTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.news_not_active,0,0);
                activityHomePageBinding.reportsTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.reports_actvie,0,0);
                activityHomePageBinding.accoutTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.account,0,0);
                activityHomePageBinding.soursTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.sourse,0,0);
                activityHomePageBinding.versinTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.versions,0,0);
            }
        });
        activityHomePageBinding.soursTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),ReosurcesActivity.class));
                activityHomePageBinding.newsTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.news_not_active,0,0);
                activityHomePageBinding.reportsTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.reports,0,0);
                activityHomePageBinding.accoutTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.account,0,0);
                activityHomePageBinding.soursTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.sourse_actvie,0,0);
                activityHomePageBinding.versinTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.versions,0,0);
            }
        });
    }
}
