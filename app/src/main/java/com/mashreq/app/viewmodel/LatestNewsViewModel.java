package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mashreq.app.model.modeldb.ElmashrqNews.News;
import com.mashreq.app.model.modeldb.News.NewsModel;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;
import com.mashreq.app.repository.Repository;

import java.io.IOException;

import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LatestNewsViewModel extends ViewModel {
  private MutableLiveData<News>mutableLiveData=new MutableLiveData<>() ;

  private Repository repository;

  @ViewModelInject
  public LatestNewsViewModel(Repository repository) {
    this.repository = repository;
  }

  public MutableLiveData<News> getMutableLiveData() {
    return mutableLiveData;
  }

  public void getResponse(int type, int catId) {
       repository.getListResult(type,catId).subscribeOn(Schedulers.io())
       .subscribe(result->mutableLiveData.postValue(result),error->Log.e("error",error.getMessage()));
  }
}
