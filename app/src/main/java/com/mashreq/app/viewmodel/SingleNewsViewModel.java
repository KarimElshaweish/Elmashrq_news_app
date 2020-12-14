package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mashreq.app.model.modeldb.SingleNew.SingleNewsModel;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;
import com.mashreq.app.repository.Repository;

import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.jvm.internal.markers.KMutableSet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleNewsViewModel extends ViewModel {
    private MutableLiveData<SingleNewsModel>mutableLiveData=new MutableLiveData<>();

    private Repository repository;

    @ViewModelInject
    public SingleNewsViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<SingleNewsModel> getMutableLiveData() {
        return mutableLiveData;
    }

    public void getSingleNews(int id){
        if(mutableLiveData==null)
            mutableLiveData=new MutableLiveData<>();

        repository.getSingleNews(id).subscribeOn(Schedulers.io())
        .subscribe(singleNewsModel -> {
            mutableLiveData.postValue(singleNewsModel);
        },error->{
            Log.e("error",error.getMessage());
        });
    }

}
