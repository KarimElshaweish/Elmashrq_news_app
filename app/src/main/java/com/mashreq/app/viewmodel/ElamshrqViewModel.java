package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mashreq.app.model.modeldb.ElmashrqManualData.ElmashrqNews;
import com.mashreq.app.model.modeldb.ElmashrqNews.News;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;
import com.mashreq.app.repository.Repository;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElamshrqViewModel extends ViewModel {
    MutableLiveData<ElmashrqNews>elmashrqList=new MutableLiveData<>();

    private Repository repository;

    @ViewModelInject
    public ElamshrqViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ElmashrqNews> getElmashrqList() {
        return elmashrqList;
    }

    public void getResponse(){
        repository.getRepsonseFromApi().subscribeOn(Schedulers.io())
        .subscribe(result->elmashrqList.postValue(result),error->Log.e("error",error.getMessage()));
    }

}
