package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

;import com.google.gson.JsonObject;
import com.mashreq.app.model.modeldb.AddNews.AddNewsDB;
import com.mashreq.app.model.modeldb.AddNews.Data;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;
import com.mashreq.app.repository.Repository;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddNewsViewModel extends ViewModel {

    private Repository repository;

    @ViewModelInject
    public AddNewsViewModel(Repository repository) {
        this.repository = repository;
    }


    private MutableLiveData<AddNewsDB>mutableLiveData=new MutableLiveData<>();

    public MutableLiveData<AddNewsDB> getMutableLiveData() {
        return mutableLiveData;
    }

    public void AddNews(String token, String cityId, String catID, String  title, String content , MultipartBody.Part image,
                        String video, String date){

        addNewsResponse(token,cityId,catID,title,content,image,video,date);
    }
    private void addNewsResponse(String token,String cityId, String catID, String  title, String content , MultipartBody.Part image,
                                 String video, String date){
        repository.addNews(token,cityId,catID,title,content,image,video,date)
                .subscribeOn(Schedulers.io())
                .subscribe(result->mutableLiveData.postValue(result),error->Log.e("error", error.getMessage()));
    }
}
