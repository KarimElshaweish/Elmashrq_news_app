package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mashreq.app.model.modeldb.CitesNews.CitesNews;
import com.mashreq.app.repository.Repository;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class CityNewsViewModel extends ViewModel {
    private Repository repository;

    MutableLiveData<CitesNews>mutableLiveData=new MutableLiveData<>();
    @ViewModelInject
    public CityNewsViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<CitesNews> getMutableLiveData() {
        return mutableLiveData;
    }

    public void getCityNews(String id){
        repository.getCitesNews(id).subscribeOn(Schedulers.io())
                .subscribe(result->mutableLiveData.postValue(result),error-> Log.e("error",error.getMessage()));
    }
}
