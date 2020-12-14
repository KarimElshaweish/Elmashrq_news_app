package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mashreq.app.model.modeldb.Resource.ResourceDB;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;
import com.mashreq.app.repository.Repository;

import java.util.logging.Logger;

import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResourceViewModel extends ViewModel {
    MutableLiveData<ResourceDB> dbMutableLiveData=new MutableLiveData<>();

    private Repository repository;

    @ViewModelInject
    public ResourceViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ResourceDB> getDbMutableLiveData() {
        return dbMutableLiveData;
    }

    public void getResources(String token){
        repository.getResources(token).subscribeOn(Schedulers.io())
        .subscribe(resourceDB -> dbMutableLiveData.postValue(resourceDB),error->Log.e("error",error.getMessage()));
    }

}
