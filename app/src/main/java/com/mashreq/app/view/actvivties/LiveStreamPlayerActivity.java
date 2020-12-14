package com.mashreq.app.view.actvivties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.mashreq.app.R;
import com.mashreq.app.databinding.ActivityLiveStreamPlayerBinding;

public class LiveStreamPlayerActivity extends AppCompatActivity implements View.OnClickListener {


    ActivityLiveStreamPlayerBinding liveStreamPlayerBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        liveStreamPlayerBinding=ActivityLiveStreamPlayerBinding.inflate(getLayoutInflater());
        setContentView(liveStreamPlayerBinding.getRoot());
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        liveStreamPlayerBinding.webView.getSettings().setJavaScriptEnabled(true);
        liveStreamPlayerBinding.webView.setWebViewClient(new WebViewClient());
        liveStreamPlayerBinding.webView.loadUrl(url);

        liveStreamPlayerBinding.backButton.setOnClickListener(this);

        String imageUrl=intent.getStringExtra("logo");
        String channelName=intent.getStringExtra("name");

        liveStreamPlayerBinding.channelName.setText(channelName);
        Glide.with(this).load(imageUrl).into(liveStreamPlayerBinding.logo);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.backButton)
            finish();
    }
}
