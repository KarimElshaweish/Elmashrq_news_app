package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mashreq.app.model.modeldb.Report.ReportsModel;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;
import com.mashreq.app.repository.Repository;

import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportsViewModel extends ViewModel {
  private MutableLiveData<ReportsModel> mutableLiveData=new MutableLiveData<>();

  private Repository repository;

  @ViewModelInject
    public ReportsViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ReportsModel> getMutableLiveData() {
        return mutableLiveData;
    }

    public void getResponse(String catId) {

      repository.getImportantNews().subscribeOn(Schedulers.io())
      .subscribe(reportsModel -> mutableLiveData.postValue(reportsModel),error->Log.e("error",error.getMessage()));
  }
}
