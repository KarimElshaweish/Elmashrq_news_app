package com.mashreq.app.view.actvivties;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mashreq.app.R;
import com.mashreq.app.Utils.FileUtils;
import com.mashreq.app.databinding.ActivityAddNewsPictureBinding;
import com.mashreq.app.model.modeldb.AddNews.AddNewsDB;
import com.mashreq.app.viewmodel.AddNewsViewModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class AddNewsPicture extends AppCompatActivity implements View.OnClickListener {

    int PICK_IMAGE=77;
    ActivityAddNewsPictureBinding activity_add_news_picture;
    String uriString;
    String token;
    Uri uri;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE){
             uri=data.getData();
            uriString=uri.toString();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            activity_add_news_picture.addImage.setImageURI(uri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_add_news_picture=ActivityAddNewsPictureBinding.inflate(getLayoutInflater());
        setContentView(activity_add_news_picture.getRoot());
        SharedPreferences sharedPref = getSharedPreferences("data",Context.MODE_PRIVATE);
        token=sharedPref.getString("token","");
        activity_add_news_picture.addComment.setOnClickListener(this);
        activity_add_news_picture.addImage.setOnClickListener(this);

    }
    Bitmap bitmap;
    private MultipartBody.Part convertToString()
    {

        File file=FileUtils.getFile(this,uri);
        RequestBody requestFile =
                RequestBody.create(
                        okhttp3.MediaType.parse("image/*"),
                        file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        RequestBody fullName =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), "Your Name");
        return body;
    }
    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result =checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }
    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    77);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    AddNewsViewModel addNewsViewModel;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton:
                finish();
                break;
            case R.id.addImage:
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"اختار صورة الخبر "),PICK_IMAGE);
                break;
            case R.id.addComment:
                if(!checkPermissionForReadExtertalStorage()) {
                    try {
                        requestPermissionForReadExtertalStorage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                addNewsViewModel= new ViewModelProvider(this).get(AddNewsViewModel.class);
                    addNewsViewModel.AddNews(token,"1","1",activity_add_news_picture.titleText.getText().toString(),
                        activity_add_news_picture.commentText.getText().toString(),
                        convertToString(),"", Calendar.getInstance().getTime().toString());

                    addNewsViewModel.getMutableLiveData().observe(this, new Observer<AddNewsDB>() {
                        @Override
                        public void onChanged(AddNewsDB addNewsDB) {
                            if(addNewsDB.getData()!=null) {
                                MDToast mdToast=MDToast.makeText(getBaseContext(),addNewsDB.getMessage(),MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS);
                                mdToast.show();
                                finish();
                            }
                            else {
                                Log.d("error", addNewsDB.getMessage());
                                MDToast mdToast=MDToast.makeText(getBaseContext(),addNewsDB.getMessage(),MDToast.LENGTH_SHORT,MDToast.TYPE_INFO);
                                mdToast.show();
                            }
                        }
                    });
                break;
        }
    }
}
