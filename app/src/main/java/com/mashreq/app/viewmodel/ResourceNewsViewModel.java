package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mashreq.app.model.modeldb.ElmashrqNews.News;
import com.mashreq.app.model.modeldb.ResourceNews.ResourceNews;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;
import com.mashreq.app.repository.Repository;

import java.util.logging.Logger;

import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResourceNewsViewModel extends ViewModel {
    MutableLiveData<ResourceNews>newsMutableLiveData=new MutableLiveData<>();

    private Repository repository;

    @ViewModelInject
    public ResourceNewsViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ResourceNews> getNewsMutableLiveData() {
        return newsMutableLiveData;
    }

    public void getNewsMutableLiveData(String id) {
        repository.getResourceNews(id).subscribeOn(Schedulers.io())
        .subscribe(resourceNews -> newsMutableLiveData.postValue(resourceNews),error->Log.e("error",error.getMessage()));
    }
}
