package com.mashreq.app.view.actvivties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mashreq.app.R;
import com.mashreq.app.databinding.ActivityAddNewsBinding;

public class AddNewsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAddNewsBinding activity_add_news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_add_news=ActivityAddNewsBinding.inflate(getLayoutInflater());
        setContentView(activity_add_news.getRoot());
        activity_add_news.cvUploadPictures.setOnClickListener(this);
        activity_add_news.cvUploadVideo.setOnClickListener(this);
        activity_add_news.backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cvUploadPictures:
                startActivity(new Intent(this,AddNewsPicture.class));
                break;
            case R.id.backButton:
                finish();
                break;
        }
    }
}
