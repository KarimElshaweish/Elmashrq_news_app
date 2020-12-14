package com.mashreq.app.view.actvivties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mashreq.app.R;
import com.mashreq.app.adapter.ChannelAdapter;
import com.mashreq.app.databinding.ActivityLiveStreamBinding;
import com.mashreq.app.model.modeldb.LiveStreamDB.LiveSteamDB;
import com.mashreq.app.viewmodel.LiveStreamViewModel;

import dagger.hilt.EntryPoint;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LiveStreamActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLiveStreamBinding activity_live_stream;
    ChannelAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_live_stream=ActivityLiveStreamBinding.inflate(getLayoutInflater());
        setContentView(activity_live_stream.getRoot());
        activity_live_stream.all.setOnClickListener(this);
        activity_live_stream.sport.setOnClickListener(this);
        activity_live_stream.news.setOnClickListener(this);
        activity_live_stream.collection.setOnClickListener(this);

        adapter=new ChannelAdapter(this);
        activity_live_stream.rv.setHasFixedSize(true);
        activity_live_stream.rv.setLayoutManager(new GridLayoutManager(this,3));
        activity_live_stream.rv.setAdapter(adapter);


        activity_live_stream.backButton.setOnClickListener(this);

        getLiveChannels();
    }

    private void getLiveChannels() {
        LiveStreamViewModel liveStreamViewModel =new ViewModelProvider(this).get(LiveStreamViewModel.class);
        liveStreamViewModel.getLiveSteam();
        liveStreamViewModel.getLiveSteamDBMutableLiveData().observe(this, new Observer<LiveSteamDB>() {
            @Override
            public void onChanged(LiveSteamDB liveSteamDB) {
                if(liveSteamDB!=null)
                    adapter.setNewList(liveSteamDB.getData());
            }
        });
    }

    private void customaizeActiveTextView(TextView activeTextView){
        activeTextView.setTextColor(getResources().getColor(R.color.colorAccent));
        activeTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.diver);
    }

    private void customaziceInActiveTextView(TextView inActiveTextView){
        inActiveTextView.setTextColor(getResources().getColor(R.color.color_not_activ));
        inActiveTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sport:
                customaizeActiveTextView(activity_live_stream.sport);
                customaziceInActiveTextView(activity_live_stream.all);
                customaziceInActiveTextView(activity_live_stream.collection);
                customaziceInActiveTextView(activity_live_stream.news);
                break;
            case R.id.all:
                customaizeActiveTextView(activity_live_stream.all);
                customaziceInActiveTextView(activity_live_stream.sport);
                customaziceInActiveTextView(activity_live_stream.collection);
                customaziceInActiveTextView(activity_live_stream.news);
                break;
            case R.id.news:
                customaizeActiveTextView(activity_live_stream.news);
                customaziceInActiveTextView(activity_live_stream.all);
                customaziceInActiveTextView(activity_live_stream.collection);
                customaziceInActiveTextView(activity_live_stream.sport);
                break;
            case R.id.collection:
                customaizeActiveTextView(activity_live_stream.collection);
                customaziceInActiveTextView(activity_live_stream.all);
                customaziceInActiveTextView(activity_live_stream.news);
                customaziceInActiveTextView(activity_live_stream.sport);
                break;
            case R.id.backButton:
                finish();
                break;
        }
    }
}
