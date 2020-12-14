package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mashreq.app.model.modeldb.Resource.ResourceDB;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;
import com.mashreq.app.repository.Repository;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowOrUnFollowViewModel extends ViewModel {

    private MutableLiveData<ResourceDB>mutableLiveData=new MutableLiveData<>();

    private Repository repository;
    @ViewModelInject
    public FollowOrUnFollowViewModel(Repository repository) {
        this.repository = repository;
    }



    public MutableLiveData<ResourceDB> getMutableLiveData() {
        return mutableLiveData;
    }

    public void FollowOrUnFollow(String token, String sourceId){
        JsonObject data=new JsonObject();
        data.addProperty("source_id",sourceId);
        repository.getResponse(data,token).subscribeOn(Schedulers.io())
        .subscribe(resourceDB -> mutableLiveData.postValue(resourceDB),error->Log.e("error",error.getMessage()));
    }

}
