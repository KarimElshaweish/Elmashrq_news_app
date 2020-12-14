package com.mashreq.app.view.actvivties;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.mashreq.app.R;
import com.mashreq.app.databinding.ActivityProfileBinding;

import java.io.File;
import java.net.URL;

import retrofit2.http.Url;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    int SELECT_IMAGE_CODE=70;
    ActivityProfileBinding activityProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding=ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());
        activityProfileBinding.changeImageButton.setOnClickListener(this);
        activityProfileBinding.saveChange.setOnClickListener(this);
    }


    private void choiceImageFromGallery(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"من فضلك اختار صورة شخصية"),SELECT_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(requestCode==SELECT_IMAGE_CODE){
                Uri url=data.getData();
                Glide.with(this).load(url).into(activityProfileBinding.profileImage);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changeImageButton:
                choiceImageFromGallery();
                break;
                case R.id.saveChange:
                    break;
        }
    }
}
